package com.mingtai.mt.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


import com.alibaba.fastjson.JSONObject;
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
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UToast;
import com.mingtai.mt.util.UiHelper;
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
 * Created by LG on 2019/12/12.
 */
public class LoginActivity extends BaseActivity implements LoginContract {

  @BindView(R.id.et_login_input_account)
  EditText et_login_input_account;
  @BindView(R.id.et_login_input_psd)
  EditText et_login_input_psd;
  @BindView(R.id.cb_remember_psd)
  CheckBox cb_remember_psd;

  LoginPresenter loginPresenter = new LoginPresenter();
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

  @Override
  public int getLayoutId() {
    return R.layout.activity_login;
  }

  @Override
  public void initEventAndData() {
    Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

    loginPresenter.onCreate(this,this);
    loginPresenter.getSettingParameter(ProApplication.SESSIONID(this));

    if (getIntent() != null && getIntent().getStringExtra("loginerror") != null && getIntent().getStringExtra("loginerror").trim().length() > 0){
      toast("登录失效，请重新登录");
    }


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

    SharedPreferences sharedPreferences = getSharedPreferences(MingtaiUtil.LOGIN,MODE_PRIVATE);
    if (sharedPreferences != null && sharedPreferences.getBoolean(MingtaiUtil.LOGIN,false)){
      et_login_input_account.setText(sharedPreferences.getString("account",""));
      et_login_input_psd.setText(sharedPreferences.getString("psd",""));
      cb_remember_psd.setChecked(true);
    }else if (sharedPreferences != null && !sharedPreferences.getBoolean(MingtaiUtil.LOGIN,false)){
      et_login_input_account.setText(sharedPreferences.getString("account",""));
      et_login_input_psd.setText("");
      cb_remember_psd.setChecked(false);
    }else {
      et_login_input_account.setText("");
      et_login_input_psd.setText("");
      cb_remember_psd.setChecked(true);
    }


  }

  @OnClick({R.id.btn_login,R.id.ll_login_send_psw})
  public void onClick(View view){
    switch (view.getId()){
      case R.id.btn_login:

        SharedPreferences sharedPreferences = getSharedPreferences(MingtaiUtil.LOGIN,MODE_PRIVATE);
        if (cb_remember_psd.isChecked()){

          sharedPreferences.edit().putString("account",et_login_input_account.getText().toString())
                  .putString("psd",et_login_input_psd.getText().toString()).putBoolean("isLogin",true).commit();
        }else {

          sharedPreferences.edit().putString("account",et_login_input_account.getText().toString())
                  .putString("psd","").putBoolean("isLogin",false).commit();
        }

        if (et_login_input_account.getText().toString().trim().length() > 0 && et_login_input_psd.getText().toString().trim().length() > 0){

          loginPresenter.setLogin(et_login_input_account.getText().toString(),et_login_input_psd.getText().toString(),ProApplication.SESSIONID(this));

        }
//                UiHelper.launcher(this,MainActivity.class);

        break;

      case R.id.ll_login_send_psw:
           UiHelper.launcherForResult(this,ForgetPsdActivity.class,0x1213);
        break;
    }
  }


  @Override
  public void setDataSuccess(AccountBean accountBean) {

      ProApplication.mAccountBean = accountBean;

      if (accountBean.getUserLogins() == 0){

        Bundle bundle = new Bundle();
        bundle.putString("CategoryName", "用户协议");
        bundle.putString("URL", ProApplication.mHomeBean.getRegisterRequirements());
        bundle.putString("type","register");
        UiHelper.launcherForResultBundle(this, WebviewActivity.class,0x1212, bundle);

      }else {

        SharedPreferences sharedPreferences = getSharedPreferences(MingtaiUtil.LOGIN,MODE_PRIVATE);
        if (cb_remember_psd.isChecked()){
          String msg = "";
          try {
            msg = MingtaiUtil.serialize(ProApplication.mAccountBean);
          } catch (IOException e) {
            e.printStackTrace();
          }
          sharedPreferences.edit().putBoolean(MingtaiUtil.LOGIN,true).putString("AccountBean",msg).commit();
        }else {
          sharedPreferences.edit().putBoolean(MingtaiUtil.LOGIN,false).putString("psd","").commit();
        }


        UiHelper.launcher(this,MainActivity.class);
        finish();
      }


  }


  @Override
  public void getDataSuccess(HomeBean msg) {
    update(msg);
    ProApplication.mHomeBean = msg;
    MingtaiUtil.APP_ID = msg.getAppid();
    ProApplication.BANNERIMG = msg.getImgUrl() + "imgdb/";
  }

  @Override
  public void getDataFail(String msg) {
    UToast.show(this,msg);
  }


  @Override
  public void setDataFail(String msg) {
    toast(msg);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && requestCode == 0x1213){
      finish();
    }else if (resultCode == RESULT_OK && requestCode == 0x1212){

      if (cb_remember_psd.isChecked()){
        String msg = "";
        SharedPreferences sharedPreferences = getSharedPreferences(MingtaiUtil.LOGIN,MODE_PRIVATE);
        try {
          msg = MingtaiUtil.serialize(ProApplication.mAccountBean);
        } catch (IOException e) {
          e.printStackTrace();
        }
        sharedPreferences.edit().putBoolean(MingtaiUtil.LOGIN,true).putString("account",et_login_input_account.getText().toString())
                .putString("psd",et_login_input_psd.getText().toString()).putString("AccountBean",msg).commit();
      }

      UiHelper.launcher(this,MainActivity.class);
      finish();
    }
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

                if (bean.getVersionShort() > UpdateManager.getInstance().getVersionName(LoginActivity.this)) {
                  AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this).setMessage("请升级更新app").setPositiveButton("确定", new DialogInterface.OnClickListener() {
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