package com.mingtai.mt.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.PersonalInfoContract;
import com.mingtai.mt.entity.CheckBean;
import com.mingtai.mt.entity.PersonalInfoBean;
import com.mingtai.mt.presenter.PersonalInfoPresenter;
import com.mingtai.mt.ui.DownloadingDialog;
import com.mingtai.mt.ui.SmsDialog;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.DataCleanManager;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;
import com.mingtai.mt.util.UpdateManager;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.update.BGADownloadProgressEvent;
import cn.bingoogolapple.update.BGAUpgradeUtil;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscriber;
import rx.functions.Action1;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

/**
 * Created by LG on 2020/1/9.
 */
public class PersonalInfoActivity extends BaseActivity implements  View.OnClickListener, PersonalInfoContract {

    @BindView(R.id.tv_nickname)
    TextView nickName;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_clear)
    TextView tv_clear;
    @BindView(R.id.rl_change_psd)
    RelativeLayout rl_change_psd;
    @BindView(R.id.tv_new_edition)
    TextView tv_new_edition;

    private PopupWindow popupWindow = null;
    private static final int IMAGEBUNDLE = 0x221;
    private static final int REQUEST_CAMERA = 0x222;
    private static final int RESULT_MYNICK = 0x112;
    private boolean isChangeSuccess = false;
    private String mFilePath;
    private Uri cropImageUri;
    private PersonalInfoPresenter personalInfoPresenter = new PersonalInfoPresenter();

    /**
     * 下载文件权限请求码
     */
    private static final int RC_PERMISSION_DOWNLOAD = 1;
    /**
     * 删除文件权限请求码
     */
    private static final int RC_PERMISSION_DELETE = 2;

    private DownloadingDialog mDownloadingDialog;
    private String mNewVersion = "2";
    private String mApkUrl = "";
    //    private DownloadBean downloadBean;
    private CheckBean bean;
    private double code = 0;
    private int type = 0;
    private SmsDialog smsDialog;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 123) {
                String str = msg.getData().getString("str");
                if (str != null && str.length() > 0) {
                    Gson gson = new Gson();
//                    ImageUploadResultBean imageUploadResultBean = gson.fromJson(str, ImageUploadResultBean.class);
//
//                    personalInfoPresenter.uploadImage(imageUploadResultBean.getUrl().get(0), ProApplication.SESSIONID(PersonalInfoActivity.this));
                }
            }else if (msg.what == 0x1234){
                personalInfoPresenter.SendSms(ProApplication.SESSIONID(PersonalInfoActivity.this));
                smsDialog.setStart();
            }else if(msg.what == 0x112){
                String vcode = msg.getData().getString("VCode");
                personalInfoPresenter.getSafetyVerificationCode(vcode,ProApplication.SESSIONID(PersonalInfoActivity.this));
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        ActivityUtil.addAllActivity(this);
        code = UpdateManager.getInstance().getVersionName(this);

        mFilePath = Environment.getExternalStorageDirectory() + "/test/" + "temp.jpg";// 获取SD卡路径
//        mFilePath = mFilePath + "/test/" + "temp.jpg";// 指定路径

        personalInfoPresenter.onCreate(this, this);
//        personalInfoPresenter.getInfo(ProApplication.SESSIONID(this));

        try {
            String cacheSize = DataCleanManager.getTotalCacheSize(this);
            if (!cacheSize.equals("0K")) {
                tv_clear.setText(cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        nickName.setText(ProApplication.mAccountBean.getSurName());
        tv_phone.setText(ProApplication.mAccountBean.getMobile());
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

        tv_new_edition.setText("V" + code);
    }

    @OnClick({ R.id.rl_nickname_info,  R.id.rl_clear, R.id.tv_loginout, R.id.rl_change_psd, R.id.rl_update,R.id.ll_back,R.id.rl_change_login_psd})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rl_nickname_info:

                break;

            case R.id.rl_clear:

                DataCleanManager.clearAllCache(this);
                if (tv_clear.getText().toString().length() > 0) {
                    toast("清除缓存成功");
                }
                tv_clear.setText("");

                break;

            case R.id.tv_loginout:

                personalInfoPresenter.LoginOut(ProApplication.SESSIONID(this));

                break;

            case R.id.rl_change_psd:

                type = 1;

                personalInfoPresenter.SendSms("2",ProApplication.SESSIONID(this));

                break;

            case R.id.ll_back:

                finish();

                break;

            case R.id.rl_update:
                myPermission();

                /*String url = ProApplication.UPGRADEURL;
                OkHttpUtils.get()
                        .url(url)
                        .addParams("api_token", ProApplication.UPGRADETOKEN)
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

                                if (bean.getVersionShort() > code) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfoActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            mApkUrl = bean.getInstall_url();
                                            deleteApkFile();
                                            downloadApkFile();
                                        }
                                    });
                                    builder.create().setCanceledOnTouchOutside(false);
                                    //  builder.setCancelable(false);
                                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialog) {
                                            finish();
                                        }
                                    });
                                    builder.show();

                                } else {
                                    toast("已经是最新版本");
                                }
                            }
                        });*/


                break;

            case R.id.rl_change_login_psd:

                type = 2;
                personalInfoPresenter.SendSms("2",ProApplication.SESSIONID(this));

                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isChangeSuccess) {
                setResult(RESULT_OK);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == RESULT_MYNICK && resultCode == Activity.RESULT_OK) {
            String account = data.getStringExtra("account");
            nickName.setText(account);
        }
    }

    //加载图片
    private void showImage(String imagePath) {
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
//        roundImageView.setImageBitmap(bm);
    }

    public void startPhotoZoom(Uri paramUri) {
        File headFile = new File(getExternalCacheDir(), "crop.jpg");
        try {
            if ((headFile).exists()) {
                (headFile).delete();
            }
            (headFile).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.cropImageUri = Uri.fromFile(headFile);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(paramUri, "image/*");
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra("output", this.cropImageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 4);
    }

    @Override
    public void modifySuccess() {

    }

    @Override
    public void modifyFail(String msg) {

    }

    @Override
    public void getInfoSuccess(PersonalInfoBean loginBean) {
        nickName.setText(ProApplication.mAccountBean.getNickName());
        tv_phone.setText(ProApplication.mAccountBean.getMobile());
    }

    @Override
    public void LoginOutSuccess(String msg) {
        SharedPreferences sharedPreferences = getSharedPreferences(MingtaiUtil.LOGIN, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(MingtaiUtil.LOGIN,false).commit();

        UiHelper.launcher(this, LoginActivity.class);

        Intent intent = new Intent();
        intent.putExtra("loginout", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void LoginOutFail(String msg) {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    @Override
    public void getSendVcodeSuccess(String s) {
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        UiHelper.launcherBundle(this, ModifyPayActivity.class,bundle);
    }

    @Override
    public void getSendVcodeFail(String msg) {

        smsDialog = new SmsDialog(this, MingtaiUtil.phoneAddress(ProApplication.mAccountBean.getMobile()).toString(),handler,3);
        smsDialog.show();
    }

    @Override
    public void onSendVcodeSuccess(String msg) {

    }

    @Override
    public void onSendVcodeFail(String msg) {

    }

    @Override
    public void safetyVerificationCodeSuccess(String msg) {
        UiHelper.launcher(this, ModifyPayActivity.class);
    }

    @Override
    public void safetyVerificationCodeFail(String msg) {
        toast(msg);
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
        } else {
            EasyPermissions.requestPermissions(this, "使用 BGAUpdateDemo 需要授权读写外部存储权限!", RC_PERMISSION_DOWNLOAD, perms);
        }
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
        }
    }
}
