package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.RegisterContract;
import com.mingtai.mt.entity.FriendsBean;
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
public class RegisterPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private RegisterContract registerContract;

    @Override
    public void onCreate(Context context, IView view) {
        manager = new DataManager(context);
        registerContract = (RegisterContract) view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void setData(String PageIndex, String PageCount, String GoodsType){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsListVip");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("GoodsType", GoodsType);

        mCompositeSubscription.add(manager.homelist(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, PageBean>() {
                    @Override
                    public void onResponse(String goodsListBeans, String status, PageBean page) {
                        registerContract.setDataSuccess(goodsListBeans, page);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.setDataFail(msg);

                    }
                }));
    }


    public void queryName(String UserName, String UserType, String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseQueryNameGet");
        params.put("UserName", UserName);
        params.put("UserType", UserType);
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.queryName(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<FriendsBean, Object>() {
                    @Override
                    public void onResponse(FriendsBean friendsBean, String status, Object o) {
                        registerContract.queryNameSuccess(friendsBean,status);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.queryNameFail(msg);

                    }
                }));
    }

    public void getRefereesName(String UserName, String UserType, String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "GetRefereesLeftSubarea");
        params.put("UserName", UserName);
        params.put("UserType", UserType);
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.getRefereesName(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<FriendsBean, Object>() {
                    @Override
                    public void onResponse(FriendsBean friendsBean, String status, Object o) {
                        registerContract.getRefereesNameSuccess(friendsBean,status);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        registerContract.getRefereesNameFail(msg);

                    }
                }));
    }
}
