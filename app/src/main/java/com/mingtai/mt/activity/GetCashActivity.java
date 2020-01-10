package com.mingtai.mt.activity;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.GetCashContract;
import com.mingtai.mt.entity.UserBankBean;
import com.mingtai.mt.presenter.GetCashPresenter;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/10.
 */
public class GetCashActivity extends BaseActivity implements GetCashContract {

    @BindView(R.id.tv_wlm_coin)
    TextView tv_wlm_coin;
    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.et_wlm_coin)
    EditText et_wlm_coin;

    GetCashPresenter getCashPresenter = new GetCashPresenter();
    private UserBankBean userBankBean;
    private PopupWindow popupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_getcash;
    }

    @Override
    public void initEventAndData() {
        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        getCashPresenter.onCreate(this, this);

        userBankBean = (UserBankBean) getIntent().getBundleExtra(MingtaiUtil.TYPEID).getSerializable(MingtaiUtil.USERBANKBEAN);
        String Coin = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString(MingtaiUtil.COIN);
        tv_wlm_coin.setText(Coin);
        String bankStr = userBankBean.getBankNo().substring(userBankBean.getBankNo().length() - 4, userBankBean.getBankNo().length());
        tv_bank_name.setText(userBankBean.getBankNameDesc() + "(" + bankStr + ")");
    }

    @OnClick({R.id.tv_all_getcash, R.id.tv_getcash, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_all_getcash:

                et_wlm_coin.setText(tv_wlm_coin.getText().toString());

                break;


            case R.id.tv_getcash:

                if (Double.valueOf(et_wlm_coin.getText().toString()) > Double.valueOf(tv_wlm_coin.getText().toString())) {
                    toast("提现金额大于余额，不能提现");
                    return;
                }

                final int coinAmount = Integer.valueOf(et_wlm_coin.getText().toString());

                View view1 = LayoutInflater.from(this).inflate(R.layout.layout_popup_psd, null);
                final Dialog payDialog =new Dialog(GetCashActivity.this);
                payDialog.setContentView(view1);
                payDialog.show();
                TextView tv_forgetPwd = view1.findViewById(R.id.tv_forgetPwd);
                ImageView tvCancel = view1.findViewById(R.id.tvCancel);
                final EditText et_psd = view1.findViewById(R.id.et_psd);
                TextView tv_pay = view1.findViewById(R.id.tv_pay);

                et_psd.setFocusable(true);
                et_psd.setFocusableInTouchMode(true);
                et_psd.requestFocus();

                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        payDialog.dismiss();
                    }
                });

                tv_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MingtaiUtil.editIsNotNull(et_psd)) {
                            getCashPresenter.getCash(coinAmount + "", Double.valueOf(0) + "", et_psd.getText().toString() + "",5+"", ProApplication.SESSIONID(GetCashActivity.this));
                        }else {
                            toast("密码不能为空");
                        }
                    }
                });


                tv_forgetPwd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UiHelper.launcher(GetCashActivity.this, ModifyPayActivity.class);
                    }
                });

                break;

            case R.id.ll_back:

                finish();

                break;
        }
    }

    @Override
    public void getCashSuccess() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void getCashFail(String msg) {
        toast(msg);
    }
}
