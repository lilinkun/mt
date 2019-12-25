package com.mingtai.mt.activity;


import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.contract.RegisterContract;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.mvp.IView;
import com.mingtai.mt.presenter.RegisterPresenter;

/**
 * Created by LG on 2019/12/12.
 */
public class RegisterActivity extends BaseActivity implements RegisterContract {

    RegisterPresenter registerPresenter = new RegisterPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initEventAndData() {

        registerPresenter.onCreate(this,this);
        registerPresenter.setData("1","20","12");
    }

    @Override
    public void showPromptMessage(int resId) {

    }

    @Override
    public void showPromptMessage(String message) {

    }

    @Override
    public void setDataSuccess(String msg, PageBean pageBean) {

    }

    @Override
    public void setDataFail(String msg) {

    }
}
