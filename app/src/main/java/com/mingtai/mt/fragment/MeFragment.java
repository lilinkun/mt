package com.mingtai.mt.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mingtai.mt.R;
import com.mingtai.mt.activity.ChooseAddressActivity;
import com.mingtai.mt.activity.IntegralActivity;
import com.mingtai.mt.activity.OrderListActivity;
import com.mingtai.mt.activity.PersonalInfoActivity;
import com.mingtai.mt.base.BaseFragment;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.IntegralContract;
import com.mingtai.mt.contract.MeContract;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.presenter.IntegralPresenter;
import com.mingtai.mt.presenter.MePresenter;
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
    @BindView(R.id.tv_me_vip)
    TextView tv_me_vip;
    @BindView(R.id.tv_username)
    TextView tv_username;

    private MePresenter mePresenter = new MePresenter();
    private BalanceBean balanceBean;
    private int result_person = 0x2212;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initEventAndData() {
        tv_wlm_account.setText(ProApplication.mAccountBean.getSurName());
        tv_me_vip.setText(ProApplication.mAccountBean.getUserLevelName());
        tv_username.setText(ProApplication.mAccountBean.getUserName());

        mePresenter.onCreate(getActivity(),this);
        mePresenter.getBalance(ProApplication.SESSIONID(getActivity()));
    }

    @OnClick({R.id.rl_my_all_order,R.id.ll_wait_pay,R.id.ll_wait_receiver,R.id.ll_wait_deliver,R.id.ll_customer_service, R.id.ll_integral,R.id.ll_coin,R.id.ll_me_setting,R.id.ll_address})
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


                Bundle bundle4 = new Bundle();
                bundle4.putInt("style", 0);
                bundle4.putSerializable(MingtaiUtil.BALANCEBEAN, balanceBean);
                UiHelper.launcherForResultBundle(getActivity(), IntegralActivity.class, 0x1987, bundle4);

                break;

            case R.id.ll_coin:

                Bundle bundle5 = new Bundle();
                bundle5.putInt("style", 1);
                bundle5.putSerializable(MingtaiUtil.BALANCEBEAN, balanceBean);
                UiHelper.launcherForResultBundle(getActivity(), IntegralActivity.class, 0x1987, bundle5);

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

    }

    @Override
    public void getSendVcodeFail(String msg) {

    }

    @Override
    public void verificationPsdSuccess(String msg) {

    }

    @Override
    public void verificationPsdFail(String msg) {

    }
}
