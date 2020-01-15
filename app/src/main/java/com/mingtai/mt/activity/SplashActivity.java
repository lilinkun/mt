package com.mingtai.mt.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.gson.Gson;
import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.LoginContract;
import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.entity.CheckBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.presenter.LoginPresenter;
import com.mingtai.mt.ui.DownloadingDialog;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UToast;
import com.mingtai.mt.util.UpdateManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.update.BGADownloadProgressEvent;
import cn.bingoogolapple.update.BGAUpgradeUtil;
import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Administrator on 2018/12/31.
 */

public class SplashActivity extends BaseActivity implements LoginContract {

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(1500, 1000);

    /*
     * 下载文件权限请求码
     */
    private static final int RC_PERMISSION_DOWNLOAD = 1;
    /**
     * 删除文件权限请求码
     */
    private static final int RC_PERMISSION_DELETE = 2;
    private String mApkUrl = "";

    private DownloadingDialog mDownloadingDialog;
    private String mNewVersion = "2";
    private LoginPresenter loginPresenter = new LoginPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

        loginPresenter.onCreate(this,this);

        loginPresenter.getSettingParameter(ProApplication.SESSIONID(this));

        // 监听下载进度
        BGAUpgradeUtil.getDownloadProgressEventObservable()
                .compose(this.<BGADownloadProgressEvent>bindToLifecycle())
                .subscribe(new Action1<BGADownloadProgressEvent>() {
                    @Override
                    public void call(BGADownloadProgressEvent downloadProgressEvent) {
                        if (mDownloadingDialog != null && mDownloadingDialog.isShowing() && downloadProgressEvent.isNotDownloadFinished()) {
                            mDownloadingDialog.setProgress(downloadProgressEvent.getProgress(), downloadProgressEvent.getTotal());
                        }
                    }
                });

//        myCountDownTimer.start();

    }

    @Override
    public void setDataSuccess(AccountBean msg) {

    }

    @Override
    public void setDataFail(String msg) {
        toast(msg);
    }

    @Override
    public void getDataSuccess(HomeBean msg) {
        update(msg);
        MingtaiUtil.APP_ID = msg.getAppid();
        ProApplication.mHomeBean = msg;
        ProApplication.BANNERIMG = msg.getImgUrl() + "imgdb/";
        turnHome();
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(this,msg);
    }

    /**
     * 获取验证码倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            turnHome();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void turnHome(){

        Intent intent = null;
        SharedPreferences sharedPreferences = getSharedPreferences(MingtaiUtil.LOGIN,MODE_PRIVATE);
        if (sharedPreferences.getBoolean(MingtaiUtil.LOGIN,false)){
            intent = new Intent(getBaseContext(), MainActivity.class);
            try {
                AccountBean accountBean = MingtaiUtil.deSerialization(sharedPreferences.getString("AccountBean",""));
                ProApplication.mAccountBean = accountBean;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            intent = new Intent(getBaseContext(), LoginActivity.class);
        }
        //启动MainActivity
        startActivity(intent);
        finish();
    }

    private CheckBean bean;

    private void update(HomeBean urlBean) {
        String url = urlBean.getUpgradeUrl();
        OkHttpUtils.get()
                .url(url)
                .addParams("api_token", urlBean.getUpgradeToken())
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Log.d("err===========", e + "");
                    }


                    @Override
                    public void onResponse(String response, int id) {

                        Log.d("ok===========", response);

                        Gson gson = new Gson();
                        bean = gson.fromJson(response, CheckBean.class);

                        if (bean.getVersionShort() > UpdateManager.getInstance().getVersionName(SplashActivity.this)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    mApkUrl = bean.getInstall_url();
                                    deleteApkFile();
                                    downloadApkFile();
                                    dialog.dismiss();
                                }
                            });
                            builder.create().setCanceledOnTouchOutside(true);
                            //  builder.setCancelable(false);
                            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    finish();
                                }
                            });
                            builder.show();

                        }
                    }
                });

    }

    /**
     * 删除之前升级时下载的老的 apk 文件
     */
    @AfterPermissionGranted(RC_PERMISSION_DELETE)
    public void deleteApkFile() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 删除之前升级时下载的老的 apk 文件
            BGAUpgradeUtil.deleteOldApk();
        } else {
            EasyPermissions.requestPermissions(this, "使用 BGAUpdateDemo 需要授权读写外部存储权限!", RC_PERMISSION_DELETE, perms);
        }
    }

    /**
     * 下载新版 apk 文件
     */
    @AfterPermissionGranted(RC_PERMISSION_DOWNLOAD)
    public void downloadApkFile() {

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            downNewApk();
        } else {
            EasyPermissions.requestPermissions(this, "使用 BGAUpdateDemo 需要授权读写外部存储权限!", RC_PERMISSION_DOWNLOAD, perms);
        }
    }

    private void downNewApk() {
        // 如果新版 apk 文件已经下载过了，直接 return，此时不需要开发者调用安装 apk 文件的方法，在 isApkFileDownloaded 里已经调用了安装」
        if (BGAUpgradeUtil.isApkFileDownloaded(mNewVersion)) {
            return;
        }

        // 下载新版 apk 文件
        BGAUpgradeUtil.downloadApkFile(mApkUrl, mNewVersion)
                .subscribe(new Subscriber<File>() {
                    @Override
                    public void onStart() {
                        showDownloadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        dismissDownloadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDownloadingDialog();
                    }

                    @Override
                    public void onNext(File apkFile) {
                        if (apkFile != null) {
                            BGAUpgradeUtil.installApk(apkFile);
                        }
                    }
                });
    }

    /**
     * 显示下载对话框
     */
    private void showDownloadingDialog() {
        if (mDownloadingDialog == null) {
            mDownloadingDialog = new DownloadingDialog(this);
        }
        mDownloadingDialog.setUpdateMessage(bean.getChangelog() + "");
        mDownloadingDialog.show();
    }

    /**
     * 隐藏下载对话框
     */
    private void dismissDownloadingDialog() {
        if (mDownloadingDialog != null) {
            mDownloadingDialog.dismiss();
      /*Dialog dialog = new Dialog(this);
      TextView textView = new TextView(this);
      ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
      layoutParams.height = 500;
      layoutParams.width = 500;
      textView.setLayoutParams(layoutParams);
      textView.setText("请升级安装最新版本");
      dialog.setContentView(textView);
      dialog.setCanceledOnTouchOutside(false);
      dialog.setCancelable(false);
      dialog.show();*/
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RC_PERMISSION_DOWNLOAD:
                downNewApk();

                break;
            case RC_PERMISSION_DELETE:
                // 删除之前升级时下载的老的 apk 文件
                BGAUpgradeUtil.deleteOldApk();
                downloadApkFile();
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

