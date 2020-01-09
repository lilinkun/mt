package com.mingtai.mt.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.ModifyPayPsdContract;
import com.mingtai.mt.presenter.ModifyPayPsdPresenter;
import com.mingtai.mt.util.Eyes;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/8.
 */
public class ModifyPayActivity extends BaseActivity implements ModifyPayPsdContract {

    @BindView(R.id.tv_phone)
    TextView textView;
    @BindView(R.id.ev_sure_psd)
    EditText ev_sure_psd;
    @BindView(R.id.ev_new_psd)
    EditText ev_new_psd;
    @BindView(R.id.ev_vcode)
    EditText ev_vcode;
    @BindView(R.id.tv_detail)
    TextView tv_detail;

    ModifyPayPsdPresenter modifyPayPsdPresenter = new ModifyPayPsdPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_pay_psd;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        modifyPayPsdPresenter.onCreate(this, this);


        tv_detail.setText("修改支付密码");

        textView.setText(ProApplication.mAccountBean.getMobile());

    }

    @OnClick({ R.id.ev_new_psd, R.id.tv_modify_commit, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_modify_commit:
                if (ev_vcode.getText().toString().isEmpty()) {
                    toast("原密码不能为空");
                } else if (ev_new_psd.getText().toString().isEmpty()) {
                    toast("新密码不能为空");
                } else if (ev_new_psd.getText().toString().equals(ev_vcode.getText().toString())) {
                    toast("新老密码不能相同");
                } else if (ev_sure_psd.getText().toString().isEmpty()) {
                    toast("确认密码不能为空");
                } else if (!ev_new_psd.getText().toString().equals(ev_sure_psd.getText().toString())) {
                    toast("两次密码不同，请确认");
                } else {

                    modifyPayPsdPresenter.modifyPsd(ev_vcode.getText().toString(), ev_new_psd.getText().toString(), ev_sure_psd.getText().toString(), ProApplication.SESSIONID(this));

                }

                break;

            case R.id.ll_back:

                finish();

                break;
        }
    }
    @Override
    public void modifySuccess() {
        toast("修改成功");
        finish();
    }

    @Override
    public void modifyFail(String str) {
        toast(str);
    }

    @Override
    public void modifyPsdSuccess() {
        toast("修改成功");
        finish();
    }

    @Override
    public void modifyPsdFail(String msg) {
        toast(msg);
    }


}
