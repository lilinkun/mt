package com.mingtai.mt.activity;

import android.view.View;
import android.widget.EditText;


import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.contract.LoginContract;
import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.presenter.LoginPresenter;
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

    LoginPresenter loginPresenter = new LoginPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initEventAndData() {

        loginPresenter.onCreate(this,this);


    }

    @OnClick({R.id.btn_login,R.id.tv_login_send_psw})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:

                if (et_login_input_account.getText().toString().trim().length() > 0 && et_login_input_psd.getText().toString().trim().length() > 0){

                    loginPresenter.setLogin(et_login_input_account.getText().toString(),et_login_input_psd.getText().toString());

                }

                break;

            case R.id.tv_login_send_psw:
                toast("sasfd");
//                UiHelper.launcher(this,MainActivity.class);
                break;
        }
    }


    @Override
    public void setDataSuccess(AccountBean msg) {

        UiHelper.launcher(this,MainActivity.class);

    }

    @Override
    public void setDataFail(String msg) {

    }
}
