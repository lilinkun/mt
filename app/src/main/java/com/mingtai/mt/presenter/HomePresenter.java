package com.mingtai.mt.presenter;

import android.content.Context;
import android.view.View;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.HomeContract;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/12/25.
 */
public class HomePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private HomeContract homeContract;


    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        homeContract = (HomeContract) view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }


    public void getSettingParameter(String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Home");
        params.put("fun", "SettingParameter");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getHomeSettingData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<HomeBean, Object>() {
                    @Override
                    public void onResponse(HomeBean goodsListBeans, String status, Object page) {
                        homeContract.getDataSuccess(goodsListBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        homeContract.getDataFail(msg);

                    }
                }));
    }

    public void getHomeData(String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Home");
        params.put("fun", "Guest");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getHomeData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<HomeBean, Object>() {
                    @Override
                    public void onResponse(HomeBean goodsListBeans, String status, Object page) {
                        homeContract.getDataSuccess(goodsListBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        homeContract.getDataFail(msg);

                    }
                }));
    }



}
