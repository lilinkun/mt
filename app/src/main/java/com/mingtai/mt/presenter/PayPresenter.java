package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.PayContract;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.entity.OrderDetailInfo;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.entity.WxInfo;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/9/11.
 */
public class PayPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private PayContract payContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.manager = new DataManager(context);
        this.mContext = context;
        this.mCompositeSubscription = new CompositeSubscription();
        payContract = (PayContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    /**
     *
     * @param OrderSn  订单号
     * @param OrderAmount  付款金额
     * @param Logo_ID    在线支付方式
     * @param Integral    积分
     * @param PassWordTwo  交易密码
     * @param Money1   付款金额
     * @param Money2   付款金额
     * @param Money5    付款金额
     * @param SessionId
     */
    public void getPayOrderInfo(String OrderSn, String OrderAmount, String Logo_ID, String Integral, String PassWordTwo, String Money1, final String Money2, String Money5, String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "支付中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoPay");
        params.put("OrderSn", OrderSn);
        params.put("OrderAmount", OrderAmount);
        params.put("LogoID", Logo_ID);
        params.put("Integral", Integral);
        params.put("PassWordTwo", PassWordTwo);
        params.put("Money1", Money1);
        params.put("Money2", Money2);
        params.put("Money5", Money5);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.sureGoodsOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<WxInfo, Object>() {
                    @Override
                    public void onResponse(WxInfo s, String status, ResultBean<WxInfo, Object> page) {

                        payContract.sureOrderSuccess(s);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        payContract.sureOrderFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

    /**
     *
     * @param OrderSn  订单号
     * @param OrderAmount  付款金额
     * @param Logo_ID    在线支付方式
     * @param Integral    积分
     * @param PassWordTwo  交易密码
     * @param Money1   付款金额
     * @param Money2   付款金额
     * @param Money5    付款金额
     * @param SessionId
     */
    public void getPayOrderInfo1(String OrderSn, String OrderAmount, String Logo_ID, String Integral, String PassWordTwo, String Money1, final String Money2, String Money5, String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "支付中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoPay");
        params.put("OrderSn", OrderSn);
        params.put("OrderAmount", OrderAmount);
        params.put("LogoID", Logo_ID);
        params.put("Integral", Integral);
        params.put("PassWordTwo", PassWordTwo);
        params.put("Money1", Money1);
        params.put("Money2", Money2);
        params.put("Money5", Money5);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.sureGoodsOrder1(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String s, String status, ResultBean<String, Object> page) {

                        payContract.sureOrderSuccess(page.getDesc());

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        payContract.sureOrderFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

    public void getBalance(String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "获取数据中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "BankBase_GetBalance");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getBalance(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<BalanceBean, Object>() {
                    @Override
                    public void onResponse(BalanceBean balanceBean, String status, ResultBean<BalanceBean, Object> page) {
                        payContract.getBalanceSuccess(balanceBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        payContract.getBalanceFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

    /**
     * 　订单详情
     *
     * @param OrderSn
     * @param SessionId
     */
    public void orderDetail(String OrderSn, String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "获取订单中", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoVipDetail");
        params.put("OrderSn", OrderSn);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getOrderDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<OrderDetailInfo, Object>() {
                    @Override
                    public void onResponse(OrderDetailInfo orderDetailBeans, String status, ResultBean<OrderDetailInfo, Object> page) {
                        payContract.setDataSuccess(orderDetailBeans);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        payContract.setDataFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }


    /**
     * 调拨
     * @param OrderSn
     * @param HarmonicDate
     * @param Integral
     * @param SessionId
     */
    public void unloadPoint(String OrderSn,String HarmonicDate,String Integral, String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderInfo");
        params.put("fun", "OrderInfoHormonic");
        params.put("HarmonicDate", HarmonicDate);
        params.put("Integral", Integral);
        params.put("OrderSn", OrderSn);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.unloadPoint(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String s, String status, ResultBean<String, Object> page) {
                        payContract.getPointSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        payContract.getPointFail(msg);
                    }
                }));
    }


}
