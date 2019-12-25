package com.mingtai.mt.activity;

import android.view.View;


import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.util.UiHelper;

import butterknife.OnClick;

/**
 * Created by LG on 2019/12/12.
 */
public class LoginActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initEventAndData() {

    }

    @OnClick({R.id.btn_login,R.id.tv_login_send_psw})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:

                UiHelper.launcher(this,MainActivity.class);

                break;

            case R.id.tv_login_send_psw:
                toast("sasfd");
//                UiHelper.launcher(this,MainActivity.class);
                break;
        }
    }


}
