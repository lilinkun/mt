package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.PointContract;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/17.
 */
public class PointPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private PointContract pointContract;


    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        pointContract = (PointContract) view;
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
                        pointContract.getPointSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        pointContract.getPointFail(msg);
                    }
                }));
    }


    public void getPointHistory(String UserName){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "OrderHormonic");
        params.put("fun", "OrderHormonicVipList");
        params.put("UserName", UserName);
        mCompositeSubscription.add(manager.unloadPoint(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String s, String status, ResultBean<String, Object> page) {
                        pointContract.getPointSuccess(s);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        pointContract.getPointFail(msg);
                    }
                }));
    }



}
