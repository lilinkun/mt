package com.mingtai.mt.activity;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.LoginContract;
import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.presenter.LoginPresenter;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import butterknife.BindView;
import butterknife.OnClick;

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

  @Override
  public int getLayoutId() {
    return R.layout.activity_login;
  }

  @Override
  public void initEventAndData() {

    loginPresenter.onCreate(this,this);

    SharedPreferences sharedPreferences = getSharedPreferences(MingtaiUtil.LOGIN,MODE_PRIVATE);
    if (sharedPreferences != null && sharedPreferences.getBoolean("isLogin",false)){
      et_login_input_account.setText(sharedPreferences.getString("account",""));
      et_login_input_psd.setText(sharedPreferences.getString("psd",""));
      cb_remember_psd.setChecked(true);
    }

  }

  @OnClick({R.id.btn_login,R.id.tv_login_send_psw})
  public void onClick(View view){
    switch (view.getId()){
      case R.id.btn_login:

        SharedPreferences sharedPreferences = getSharedPreferences(MingtaiUtil.LOGIN,MODE_PRIVATE);
        if (cb_remember_psd.isChecked()){

          sharedPreferences.edit().putString("account",et_login_input_account.getText().toString())
                  .putString("psd",et_login_input_psd.getText().toString()).putBoolean("isLogin",true).commit();
        }else {

          sharedPreferences.edit().putString("account",et_login_input_account.getText().toString())
                  .putString("psd",et_login_input_psd.getText().toString()).putBoolean("isLogin",false).commit();
        }

        if (et_login_input_account.getText().toString().trim().length() > 0 && et_login_input_psd.getText().toString().trim().length() > 0){

          loginPresenter.setLogin(et_login_input_account.getText().toString(),et_login_input_psd.getText().toString(),ProApplication.SESSIONID(this));

        }
//                UiHelper.launcher(this,MainActivity.class);

        break;

      case R.id.tv_login_send_psw:
                UiHelper.launcher(this,ForgetPsdActivity.class);
        break;
    }
  }


  @Override
  public void setDataSuccess(AccountBean msg) {

    ProApplication.mAccountBean = msg;

    UiHelper.launcher(this,MainActivity.class);
    finish();

  }

  @Override
  public void setDataFail(String msg) {
    toast(msg);
  }
}