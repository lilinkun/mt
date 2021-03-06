package com.mingtai.mt.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.activity.BindCardActivity;
import com.mingtai.mt.activity.ChooseAddressActivity;
import com.mingtai.mt.activity.IntegralActivity;
import com.mingtai.mt.activity.OrderListActivity;
import com.mingtai.mt.activity.PersonalInfoActivity;
import com.mingtai.mt.activity.WebviewActivity;
import com.mingtai.mt.base.BaseFragment;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.MeContract;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.presenter.MePresenter;
import com.mingtai.mt.ui.SmsDialog;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UToast;
import com.mingtai.mt.util.UiHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/12/25.
 */
public class MeFragment extends BaseFragment implements MeContract {

    @BindView(R.id.tv_wlm_account)
    TextView tv_wlm_account;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.tv_me_vip)
    TextView tv_me_vip;
    @BindView(R.id.tv_username)
    TextView tv_username;

    private MePresenter mePresenter = new MePresenter();
    private BalanceBean balanceBean;
    private int result_person = 0x2212;
    private SmsDialog smsDialog;
    private int type = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x222){
                String vcode = msg.getData().getString("VCode");
                mePresenter.verificationPsd(vcode,ProApplication.SESSIONID(getActivity()));
            }else if (msg.what == 0x1234){
                mePresenter.SendSms(ProApplication.SESSIONID(getActivity()));
                smsDialog.setStart();
            }else if(msg.what == 0x112){
                String vcode = msg.getData().getString("VCode");
                mePresenter.getSafetyVerificationCode(vcode,ProApplication.SESSIONID(getActivity()));
            }
        }
    };


    @Override
    public int getlayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initEventAndData() {
        tv_wlm_account.setText(ProApplication.mAccountBean.getSurName());

        tv_account.setText(ProApplication.mAccountBean.getUserStatusTwoName());
        if (ProApplication.mAccountBean.getUserStatusTwo() == 10){
            tv_account.setTextColor(Color.parseColor("#17AE1A"));
        }else {
            tv_account.setTextColor(Color.parseColor("#FFFF54"));
        }

        tv_me_vip.setText(ProApplication.mAccountBean.getUserLevelName());
        if(ProApplication.mAccountBean.getUserName().trim().length() > 6) {
            tv_username.setText("**" + ProApplication.mAccountBean.getUserName().substring(ProApplication.mAccountBean.getUserName().length()-6,ProApplication.mAccountBean.getUserName().length()));
        }

        mePresenter.onCreate(getActivity(),this);
//        mePresenter.getBalance(ProApplication.SESSIONID(getActivity()));
    }

    @OnClick({R.id.rl_my_all_order,R.id.ll_wait_pay,R.id.ll_agreement,R.id.ll_bind_card,R.id.ll_wait_receiver,R.id.ll_wait_deliver,R.id.ll_customer_service, R.id.ll_integral,R.id.ll_coin,R.id.ll_me_setting,R.id.ll_address})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_my_all_order:

                setDumpOrderList(0);

                break;

            case R.id.ll_wait_pay:

                setDumpOrderList(1);
                break;

            case R.id.ll_wait_deliver:

                setDumpOrderList(2);

                break;
            case R.id.ll_wait_receiver:

                setDumpOrderList(3);

                break;

            case R.id.ll_integral:

                type = 1;

                mePresenter.SendSms("1",ProApplication.SESSIONID(getActivity()));

                /*Bundle bundle4 = new Bundle();
                bundle4.putInt("style", 0);
                bundle4.putSerializable(MingtaiUtil.BALANCEBEAN, balanceBean);
                UiHelper.launcherForResultBundle(getActivity(), IntegralActivity.class, 0x1987, bundle4);*/

                break;

            case R.id.ll_coin:

                type = 2;

                mePresenter.SendSms("1",ProApplication.SESSIONID(getActivity()));

                /*Bundle bundle5 = new Bundle();
                bundle5.putInt("style", 1);
                bundle5.putSerializable(MingtaiUtil.BALANCEBEAN, balanceBean);
                UiHelper.launcherForResultBundle(getActivity(), IntegralActivity.class, 0x1987, bundle5);*/

                break;

            case R.id.ll_customer_service:

            case R.id.ll_me_setting:

                UiHelper.launcherForResult(this, PersonalInfoActivity.class, result_person);

                break;

            case R.id.ll_address:

                Bundle bundle7 = new Bundle();
                bundle7.putString("type", "me");
                UiHelper.launcherBundle(getActivity(), ChooseAddressActivity.class, bundle7);

                break;

            case R.id.ll_bind_card:

                type = 3;
                mePresenter.SendSms("2",ProApplication.SESSIONID(getActivity()));

                break;

            case R.id.ll_agreement:

                Bundle bundle = new Bundle();
                bundle.putString("CategoryName", "购货申请");
                bundle.putString("URL", ProApplication.mHomeBean.getShoppingApplication());
                bundle.putString("type","agreement");
                UiHelper.launcherForResultBundle(this, WebviewActivity.class,0x1212, bundle);

                break;

        }
    }


    public void setDumpOrderList(int positon){

        Bundle bundle = new Bundle();
        bundle.putInt("position", positon);
        UiHelper.launcherBundle(getActivity(), OrderListActivity.class, bundle);
    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        this.balanceBean = balanceBean;
    }

    @Override
    public void getBalanceFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void getSendVcodeSuccess(String s) {
        dump();
    }

    @Override
    public void getSendVcodeFail(String msg) {

        smsDialog = new SmsDialog(getActivity(),MingtaiUtil.phoneAddress(ProApplication.mAccountBean.getMobile()).toString(),handler,type);
        smsDialog.show();

    }

    @Override
    public void verificationPsdSuccess(String msg) {
        if (smsDialog != null){
            smsDialog.dismiss();
        }

        dump();
    }

    @Override
    public void verificationPsdFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void onSendVcodeSuccess(String msg) {

    }

    @Override
    public void onSendVcodeFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void safetyVerificationCodeSuccess(String msg) {
        UiHelper.launcher(getActivity(), BindCardActivity.class);
        smsDialog.dismiss();
    }

    @Override
    public void safetyVerificationCodeFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    public void dump(){

        if (type == 3){

            UiHelper.launcher(getActivity(), BindCardActivity.class);
        }else {
            Bundle bundle5 = new Bundle();
            bundle5.putInt("style", type);
//            bundle5.putSerializable(MingtaiUtil.BALANCEBEAN, balanceBean);
            UiHelper.launcherForResultBundle(getActivity(), IntegralActivity.class, 0x1987, bundle5);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == result_person && resultCode == Activity.RESULT_OK){
            if (data.getBooleanExtra("loginout",false)){
                getActivity().finish();
            }
        }

    }
}
