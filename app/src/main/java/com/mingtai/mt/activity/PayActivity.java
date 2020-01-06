package com.mingtai.mt.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.PayContract;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.entity.WxInfo;
import com.mingtai.mt.interf.IWxResultListener;
import com.mingtai.mt.interf.OnPasswordInputFinish;
import com.mingtai.mt.presenter.PayPresenter;
import com.mingtai.mt.ui.PasswordView;
import com.mingtai.mt.util.MingtaiUtil;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.text.NumberFormat;
import java.util.Calendar;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/10.
 */
public class PayActivity extends BaseActivity implements PayContract, IWxResultListener {

    private PayPresenter payPresenter = new PayPresenter();

    private String orderid;
    private String totalPrice;
    private String where;
    private Dialog payDialog;
    private PopupWindow popupWindow;
    private String point;
    PasswordView passwordView;

    private OrderDetailBean orderDetailBeans;

    @BindView(R.id.check_wx)
    CheckBox check_wx;
    @BindView(R.id.check_self)
    CheckBox check_self;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_balance_not_enough)
    TextView tv_balance_not_enough;
    @BindView(R.id.ll_pay_order)
    LinearLayout ll_pay_order;
    @BindView(R.id.tv_point)
    TextView tv_point;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.ll_self)
    LinearLayout ll_self;
    @BindView(R.id.tv_netcoin_balance)
    TextView tv_netcoin_balance;
    @BindView(R.id.tv_discount_balance)
    TextView tv_discount_balance;
    @BindView(R.id.tv_have_point)
    TextView tv_have_point;
    @BindView(R.id.tv_surplus_point)
    TextView tv_surplus_point;
    @BindView(R.id.ll_tiaobo)
    LinearLayout ll_tiaobo;

    IWXAPI iwxapi = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initEventAndData() {

//        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        payPresenter.onCreate(this, this);

//        iwxapi = WXAPIFactory.createWXAPI(this, WlmUtil.APP_ID, true);
//        iwxapi.registerApp(WlmUtil.APP_ID);

//        WXEntryActivity.wxType(WlmUtil.WXTYPE_LOGIN);
//        WXPayEntryActivity.setPayListener(this);

        Bundle bundle = getIntent().getBundleExtra(MingtaiUtil.TYPEID);

        if (bundle != null) {
            orderid = bundle.getString(MingtaiUtil.ORDERSN);
        }
//        tv_amount.setText(totalPrice + "");
        payPresenter.getBalance(ProApplication.SESSIONID(this));

        payPresenter.orderDetail(orderid, ProApplication.SESSIONID(this));

        check_self.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    ll_self.setVisibility(View.VISIBLE);
                }else {
                    ll_self.setVisibility(View.GONE);
                }
            }
        });

    }

    @OnClick({R.id.rl_wx, R.id.rl_self, R.id.tv_right_now_pay, R.id.ll_back,R.id.tv_add_tiaobo_item})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_wx:

                if (check_wx.isChecked()) {
                    check_wx.setChecked(false);
                }else {
                    check_wx.setChecked(true);
                }
                break;

            case R.id.rl_self:

                if (check_self.isChecked()) {
                    check_self.setChecked(false);
                }else {
                    check_self.setChecked(true);
                }

                break;

            case R.id.tv_right_now_pay:

                final SharedPreferences sharedPreferences = getSharedPreferences(MingtaiUtil.LOGIN, MODE_PRIVATE);

                if (tv_balance_not_enough != null && !tv_balance_not_enough.isShown()) {

                    View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_pay, null);

                    popupWindow = new PopupWindow(this);

                    popupWindow.setContentView(view1);
                    popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    popupWindow.showAsDropDown(ll_back);

                    passwordView = view1.findViewById(R.id.pwd_view);

                    passwordView.setOnFinishInput(new OnPasswordInputFinish() {
                        @Override
                        public void inputFinish() {
                            payPresenter.getPayOrderInfo(orderid, totalPrice + "", "", "0", point, passwordView.getStrPassword(),"","","", ProApplication.SESSIONID(PayActivity.this));
                        }

                        @Override
                        public void outfo() {
                            popupWindow.dismiss();
                        }

                        @Override
                        public void forgetPwd() {
//                            UiHelper.launcher(PayActivity.this, ModifyPayActivity.class);
                        }
                    });

                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {

                        }
                    });
                }

                break;

            case R.id.ll_back:

                /*if (where.equals(WlmUtil.GOODS)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("status", 0);
                    bundle.putString("order_sn", orderid);
                    UiHelper.launcherForResultBundle(this, AllOrderActivity.class, 0x0987, bundle);
                }*/
                setResult(RESULT_OK);
                finish();

                break;

            case R.id.tv_add_tiaobo_item:

//                PopupWindow popupWindow = new PopupWindow(this);
//                View rootView = LayoutInflater.from(this).inflate(R.layout.,null);
//                popupWindow.setContentView(rootView);
//                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                popupWindow.setFocusable(true);
//                popupWindow.setOutsideTouchable(true);

                Calendar calendar = Calendar.getInstance();

                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        View view1 = LayoutInflater.from(PayActivity.this).inflate(R.layout.dialog_date,null);
                        TextView tv_title = view1.findViewById(R.id.tv_title);
                        tv_title.setText(year+"年" + (month+1) + "月" + dayOfMonth + "日调拨分值");
                        final EditText editText = view1.findViewById(R.id.et_choose_point);
                        new AlertDialog.Builder(PayActivity.this).setView(view1).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.getText().toString().trim().length() > 0){

                                    if (Integer.valueOf(editText.getText().toString()) <= Integer.valueOf(tv_surplus_point.getText().toString())){
                                        dialog.dismiss();
                                    }else {
                                        toast("您的分值不够");
                                    }
                                }
                            }
                        }).show();
                    }
                }, calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();

                break;

        }
    }

    @Override
    public void sureWxOrderSuccess(WxInfo wxInfo) {
//        WlmUtil.wxPay(wxInfo.getAppId(), wxInfo.getPartnerid(), wxInfo.getPrepayid(), wxInfo.getNonceStr(), wxInfo.getTimeStamp(), wxInfo.getPaySign(), this);
//        SharedPreferences sharedPreferences = getSharedPreferences(WlmUtil.LOGIN, MODE_PRIVATE);
//        grouponOrderPresenter.getGoodsOrderInfo(ordersn,sharedPreferences.getString(WlmUtil.OPENID,""),totalPrice+"","11",ProApplication.SESSIONID(this));
    }

    @Override
    public void sureWxOrderFail(String msg) {
        toast(msg);
    }


    @Override
    public void sureOrderSuccess(String wxInfo) {

//        payDialog.dismiss();

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

//        Bundle bundle = new Bundle();
//        bundle.putString(WlmUtil.PRICE, totalPrice);
//        bundle.putString(WlmUtil.ORDERID, orderid);
//        bundle.putString(WlmUtil.GOODSTYPE, orderDetailBeans.getOrderType() + "");
//        UiHelper.launcherForResultBundle(this, PayResultActivity.class, 0x0987, bundle);

    }

    @Override
    public void sureOrderFail(String msg) {
        toast(msg);

        /*if (passwordView != null){
            passwordView.
        }*/
    }

    @Override
    public void getBalanceSuccess(BalanceBean balanceBean) {
        double totalBalance = balanceBean.getMoney5Balance() + balanceBean.getMoney1Balance();

        tv_balance.setText("余额支付（剩余" + MingtaiUtil.isCoin(totalBalance) + "）");

        tv_discount_balance.setText("(剩余" + balanceBean.getMoney5Balance() + ")");
        tv_netcoin_balance.setText("(剩余" + balanceBean.getMoney1Balance() + ")");
    }

    @Override
    public void getBalanceFail(String msg) {
        toast(msg);
    }

    @Override
    public void setDataSuccess(OrderDetailBean orderDetailBeans) {
        this.orderDetailBeans = orderDetailBeans;
        this.point = orderDetailBeans.getIntegral() + "";
        this.totalPrice = orderDetailBeans.getOrderAmount()+"";
        tv_amount.setText(totalPrice + "");
        /*if (orderDetailBeans.getIntegral() == 0) {
            tv_point.setVisibility(View.GONE);
        } else {
            tv_point.setText("+" + point + "积分");
        }*/

        if (orderDetailBeans.getOrderType() == MingtaiUtil.TIAOBOINT) {
            tv_have_point.setText(point);
            tv_surplus_point.setText(point);
            ll_tiaobo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setDataFail(String msg) {
        toast(msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            /*Bundle bundle = new Bundle();
            bundle.putInt("status", 0);
            bundle.putString("order_sn", orderid);
            UiHelper.launcherForResultBundle(this, AllOrderActivity.class, 0x0987, bundle);
            setResult(RESULT_OK);
            finish();*/
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setWxSuccess() {
//        Bundle bundle = new Bundle();
//        bundle.putString(MingtaiUtil.ORDERSN, orderid);
//        UiHelper.launcherForResultBundle(this, PayResultActivity.class, 0x0987, bundle);
    }

    @Override
    public void setWxFail() {
        toast("支付失败");
        /*Bundle bundle = new Bundle();
        bundle.putInt("status", 0);
        bundle.putString("order_sn", orderid);
        UiHelper.launcherForResultBundle(this, AllOrderActivity.class, 0x0987, bundle);
        setResult(RESULT_OK);
        finish();*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 0x0987) {
            setResult(RESULT_OK);
            finish();
        }
    }

}
