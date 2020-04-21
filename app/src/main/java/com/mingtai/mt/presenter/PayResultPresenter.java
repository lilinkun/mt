package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.PayResultContract;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.entity.OrderDetailInfo;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/8.
 */
public class PayResultPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private PayResultContract payContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.manager = new DataManager(context);
        this.mContext = context;
        this.mCompositeSubscription = new CompositeSubscription();
        payContract = (PayResultContract) view;
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
     * 　订单详情
     *
     * @param OrderSn
     * @param SessionId
     */
    public void orderDetail(String OrderSn, String SessionId) {
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
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        payContract.setDataFail(msg);
                    }
                }));
    }



}
