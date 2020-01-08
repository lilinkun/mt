package com.mingtai.mt.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.PayContract;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.entity.TiaoboBean;
import com.mingtai.mt.entity.WxInfo;
import com.mingtai.mt.interf.IWxResultListener;
import com.mingtai.mt.presenter.PayPresenter;
import com.mingtai.mt.ui.PasswordView;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2019/9/10.
 */
public class PayActivity extends BaseActivity implements PayContract, IWxResultListener {


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
    @BindView(R.id.ll_table)
    LinearLayout ll_table;
    @BindView(R.id.ll_pay)
    LinearLayout ll_pay;
    @BindView(R.id.tv_add_tiaobo_item)
    TextView tv_add_tiaobo_item;
    @BindView(R.id.tv_right_now_pay)
    TextView tv_right_now_pay;
    @BindView(R.id.et_discount_pay)
    EditText et_discount_pay;
    @BindView(R.id.et_netcoin_pay)
    EditText et_netcoin_pay;

    IWXAPI iwxapi = null;
    private PayPresenter payPresenter = new PayPresenter();

    private String orderid;
    private String totalPrice;
    private String where;
    private Dialog payDialog;
    private PopupWindow popupWindow;
    private String point;
    PasswordView passwordView;

    private OrderDetailBean orderDetailBeans;
    private BalanceBean balanceBean;

    HashMap<String,View> stringViewHashMap = new HashMap<>();
    HashMap<String,TextView> stringTextViewHashMap = new HashMap<>();

    ArrayList<TiaoboBean> tiaoboBeans = new ArrayList<>();
    ArrayList<Integer> strings = new ArrayList<>();

    int addInt = 0;
    int goodsType = 0;
    double isRemainderPrice = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initEventAndData() {

//        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        payPresenter.onCreate(this, this);
        ActivityUtil.addActivity(this);

//        iwxapi = WXAPIFactory.createWXAPI(this, WlmUtil.APP_ID, true);
//        iwxapi.registerApp(WlmUtil.APP_ID);

//        WXEntryActivity.wxType(WlmUtil.WXTYPE_LOGIN);
//        WXPayEntryActivity.setPayListener(this);

        Bundle bundle = getIntent().getBundleExtra(MingtaiUtil.TYPEID);

        if (bundle != null) {
            orderid = bundle.getString(MingtaiUtil.ORDERSN);
            goodsType = bundle.getInt(MingtaiUtil.GOODSTYPE);
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

        if (goodsType == MingtaiUtil.TIAOBOINT){
            ll_tiaobo.setVisibility(View.VISIBLE);
            ll_pay.setVisibility(View.GONE);
            tv_right_now_pay.setText("下一步");
        }else {
            ll_tiaobo.setVisibility(View.GONE);
            ll_pay.setVisibility(View.VISIBLE);
        }

        et_discount_pay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (et_discount_pay.getText().toString().trim().length() > 0){
                        if (Double.valueOf(et_discount_pay.getText().toString()) > isRemainderPrice ||
                                Double.valueOf(et_discount_pay.getText().toString()) > balanceBean.getMoney5Balance()){
                            et_discount_pay.setText("0");
                            toast("输入的金额有误，请重新输入");
                        }else {
                            isRemainderPrice = isRemainderPrice - Double.valueOf(et_discount_pay.getText().toString());
                        }
                    }
                }
            }
        });


        et_netcoin_pay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if (et_netcoin_pay.getText().toString().trim().length() > 0){
                        if (Double.valueOf(et_netcoin_pay.getText().toString()) > isRemainderPrice ||
                                Double.valueOf(et_netcoin_pay.getText().toString()) > balanceBean.getMoney1Balance()){
                            toast("输入的金额有误，请重新输入");
                            et_netcoin_pay.setText("0");
                        }else {
                            isRemainderPrice = isRemainderPrice - Double.valueOf(et_netcoin_pay.getText().toString());
                        }
                    }
                }
            }
        });



    }

    @SuppressLint("ResourceType")
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

                if (tv_right_now_pay.getText().toString().equals("下一步")){
                    if (tv_surplus_point.getText().toString().equals("0")) {
                        String dateStr = "";
                        String pointStr = "";
                        for (int i = 0;i <tiaoboBeans.size();i++) {
                            if (i == 0){
                                dateStr =  tiaoboBeans.get(i).getTime();
                                pointStr = tiaoboBeans.get(i).getValue()+"";
                            }else {
                                dateStr = dateStr + "," +tiaoboBeans.get(i).getTime();
                                pointStr = pointStr + "," +tiaoboBeans.get(i).getValue();
                            }
                        }
                        payPresenter.unloadPoint(orderid, dateStr,pointStr,ProApplication.SESSIONID(this));
                    }else {
                        toast("你输入的调拨值不正确");
                    }
                }else {

                    if (tv_balance_not_enough != null && !tv_balance_not_enough.isShown()) {

                        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_popup_psd, null);

                        popupWindow = new PopupWindow(this);

                        popupWindow.setContentView(view1);
                        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                        popupWindow.setOutsideTouchable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setFocusable(true);
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
                                popupWindow.dismiss();
                            }
                        });

                        tv_pay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (MingtaiUtil.editIsNotNull(et_psd)) {
                                    payPresenter.getPayOrderInfo(orderid, orderDetailBeans.getOrderAmount() + "", "0", orderDetailBeans.getIntegral() + "",
                                            et_psd.getText().toString(), et_netcoin_pay.getText().toString(), "", et_discount_pay.getText().toString(),
                                            ProApplication.SESSIONID(PayActivity.this));
                                }else {
                                    toast("密码不能为空");
                                }
                            }
                        });


                        tv_forgetPwd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UiHelper.launcher(PayActivity.this, ModifyPayActivity.class);
                            }
                        });

                        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {

                            }
                        });

                        if (et_discount_pay.getText().toString().trim().length() == 0){
                            et_discount_pay.setText("0");
                        }
                        if (et_netcoin_pay.getText().toString().trim().length() == 0){
                            et_netcoin_pay.setText("0");
                        }

                        if (Double.valueOf(et_discount_pay.getText().toString()) + Double.valueOf(et_netcoin_pay.getText().toString()) > orderDetailBeans.getOrderAmount()){
                            toast("输入的金额有误，请重新输入");
                            et_discount_pay.setText("0");
                            et_netcoin_pay.setText("0");
                            return;
                        }

                        double isRemainPrice = orderDetailBeans.getOrderAmount() - (Double.valueOf(et_discount_pay.getText().toString()) + Double.valueOf(et_netcoin_pay.getText().toString()));

                        if (check_wx.isChecked() || check_self.isChecked()) {

                            if (isRemainPrice == 0 && check_self.isChecked()) {
                                popupWindow.showAsDropDown(ll_back);
                            } else if (isRemainPrice > 0 && check_self.isChecked() && check_wx.isChecked()) {
                                popupWindow.showAsDropDown(ll_back);
                            } else if (isRemainPrice > 0 && check_wx.isChecked()) {
                                toast("微信支付待开发");
                            } else  if (isRemainPrice > 0 && !check_wx.isChecked()){
                                toast("您输入的余额不足以支付，请一起选择微信支付");
                            }
                        }else {
                            toast("请选择支付方式");
                        }
                    }

                }
                break;

            case R.id.ll_back:

                Bundle bundle = new Bundle();
                bundle.putInt("status", 0);
                bundle.putString("order_sn", orderid);
                UiHelper.launcherForResultBundle(this, OrderDetailActivity.class, 0x0987, bundle);

                setResult(RESULT_OK);
                finish();

                break;

            case R.id.tv_add_tiaobo_item:

                Calendar calendar = Calendar.getInstance();

                new DatePickerDialog(this,2, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view,final int year,final int month, int dayOfMonth) {
                        View view1 = LayoutInflater.from(PayActivity.this).inflate(R.layout.dialog_date,null);
//                        TextView tv_title = view1.findViewById(R.id.tv_title);
//                        tv_title.setText(year+"年" + (month+1) + "月" + dayOfMonth + "日调拨分值");
                        final EditText editText = view1.findViewById(R.id.et_choose_point);
                        final TextView tv_exit = view1.findViewById(R.id.tv_exit);
                        final TextView tv_sure = view1.findViewById(R.id.tv_sure);

                        final Dialog dialog = new Dialog(PayActivity.this);

                        dialog.setContentView(view1);
                        tv_exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });


                        tv_sure.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (editText.getText().toString() != null && !editText.getText().toString().equals("") && editText.getText().toString().trim().length() > 0){
                                    String editStr = editText.getText().toString();
                                    int editInt = Integer.valueOf(editStr);
                                    int surplus = Integer.valueOf(tv_surplus_point.getText().toString());


                                    if (editInt <= surplus){

                                        addInt++;

                                        strings.add(addInt);

                                        tv_surplus_point.setText(surplus-editInt +"");

                                        if (surplus == editInt){
                                            tv_add_tiaobo_item.setVisibility(View.GONE);
                                        }

                                        View view1 = LayoutInflater.from(PayActivity.this).inflate(R.layout.tiaobo_item,null);

                                        EditText tv_date = view1.findViewById(R.id.tv_date);
                                        EditText et_point = view1.findViewById(R.id.et_point);
                                        TextView tv_reduce = view1.findViewById(R.id.tv_reduce);


                                        TiaoboBean tiaoboBean = new TiaoboBean();
                                        if (month < 9){
                                            tiaoboBean.setTime(year + "-0"+(month+1));
                                            tv_date.setText(year+"/0"+(month+1));
                                        }else {
                                            tiaoboBean.setTime(year + "-"+(month+1));
                                            tv_date.setText(year+"/"+(month+1));
                                        }
                                            tiaoboBean.setValue(editInt);

                                        tiaoboBeans.add(tiaoboBean);

                                        et_point.setText(editStr);

                                        final int temp = addInt;

                                        stringViewHashMap.put("" + addInt,view1);
                                        stringTextViewHashMap.put(""+addInt,editText);

                                        ll_table.addView(view1);

                                        tv_reduce.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                int s = Integer.valueOf(stringTextViewHashMap.get(temp+"").getText().toString());
                                                tv_surplus_point.setText(Integer.valueOf(tv_surplus_point.getText().toString()) + s + "");
                                                ll_table.removeView(stringViewHashMap.get(temp+""));
                                                tv_add_tiaobo_item.setVisibility(View.VISIBLE);
                                                for (int i = 0; i < strings.size();i++){
                                                    if (strings.get(i) == temp){
                                                        tiaoboBeans.remove(i);
                                                    }
                                                }
                                            }
                                        });

                                        dialog.dismiss();
                                    }else {
                                        toast("您的分值不够");
                                    }
                                }
                            }
                        });

                        dialog.show();
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

        Bundle bundle = new Bundle();
        bundle.putString(MingtaiUtil.PRICE, totalPrice);
        bundle.putString(MingtaiUtil.ORDERSN, orderid);
        bundle.putString(MingtaiUtil.GOODSTYPE, orderDetailBeans.getOrderType() + "");
        UiHelper.launcherForResultBundle(this, PayResultActivity.class, 0x0987, bundle);

        finish();

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

        this.balanceBean = balanceBean;

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

        isRemainderPrice = orderDetailBeans.getOrderAmount();
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
    public void getPointSuccess(String msg) {
        ll_tiaobo.setVisibility(View.GONE);
        ll_pay.setVisibility(View.VISIBLE);
        tv_right_now_pay.setText(getString(R.string.right_now_pay));
    }

    @Override
    public void getPointFail(String msg) {
        toast(msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Bundle bundle = new Bundle();
            bundle.putInt("status", 0);
            bundle.putString("order_sn", orderid);
            UiHelper.launcherForResultBundle(this, OrderDetailActivity.class, 0x0987, bundle);
            setResult(RESULT_OK);
            finish();
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
