package com.mingtai.mt.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.ForgetPsdContract;
import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.presenter.ForgetPsdPresenter;
import com.mingtai.mt.ui.SmsDialog;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPsdActivity extends BaseActivity implements ForgetPsdContract {

    @BindView(R.id.et_servicer_id)
    EditText et_servicer_id;
    @BindView(R.id.et_id_card)
    EditText et_id_card;
    @BindView(R.id.ev_forget_mobile)
    EditText ev_forget_mobile;
    @BindView(R.id.ev_new_psd)
    EditText ev_new_psd;
    @BindView(R.id.ev_sure_new_psd)
    EditText ev_sure_new_psd;
    @BindView(R.id.et_sms)
    EditText et_sms;
    @BindView(R.id.tv_send)
    TextView tv_send;

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);
    private ForgetPsdPresenter forgetPsdPresenter = new ForgetPsdPresenter();


    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_psd;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));

        forgetPsdPresenter.onCreate(this,this);
    }

    @OnClick({R.id.tv_send,R.id.btn_sure,R.id.ll_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_send:
                if (!MingtaiUtil.editIsNotNull(ev_forget_mobile)){
                    toast(R.string.hint_input_mobile);
                }else if (!MingtaiUtil.editIsNotNull(et_servicer_id)){
                    onToast(R.string.input_service_id);
                }else {
                    forgetPsdPresenter.SendSms(et_servicer_id.getText().toString(),ev_forget_mobile.getText().toString(),ProApplication.SESSIONID(this));
                    myCountDownTimer.start();
                }
                break;

            case R.id.btn_sure:

                if (!MingtaiUtil.editIsNotNull(et_servicer_id)){
                    onToast(R.string.input_service_id);
                }else if (!MingtaiUtil.editIsNotNull(et_id_card)){
                    onToast(R.string.input_bank_card);
                }else if (!MingtaiUtil.editIsNotNull(ev_forget_mobile)){
                    onToast(R.string.hint_input_mobile);
                }else if (!MingtaiUtil.editIsNotNull(ev_new_psd)){
                    onToast(R.string.input_new_psd);
                }else if (!MingtaiUtil.editIsNotNull(ev_sure_new_psd)){
                    onToast(R.string.psd_sure);
                }else if (!MingtaiUtil.editIsNotNull(et_sms)){
                    onToast(R.string.register_input_vcode);
                }

                forgetPsdPresenter.ForgetPsd(et_servicer_id.getText().toString(),et_sms.getText().toString(),ev_new_psd.getText().toString(),
                        ev_sure_new_psd.getText().toString(),et_id_card.getText().toString(),ev_forget_mobile.getText().toString(), ProApplication.SESSIONID(this));

                break;

            case R.id.ll_back:

                finish();

                break;

        }
    }

    @Override
    public void onSendVcodeSuccess(String msg) {
        toast("已发送手机验证码");
    }

    @Override
    public void onSendVcodeFail(String msg) {
        toast(msg);
    }

    @Override
    public void setDataSuccess(AccountBean msg) {
        ProApplication.mAccountBean = msg;

        UiHelper.launcher(this,MainActivity.class);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setDataFail(String msg) {
        toast(msg);
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
            //防止计时过程中重复点击
            tv_send.setClickable(false);
            tv_send.setText(l / 1000 + "s");
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tv_send.setText(R.string.register_send_vcoed);
            //设置可点击
            tv_send.setClickable(true);

            tv_send.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void onToast(int msgInt){
        toast(msgInt);
        return;
    }

}
