package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.SaleContract;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/2.
 */
public class SalePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private SaleContract saleContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        saleContract = (SaleContract) view;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    public void getStoreAddress(String StoreNo,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Store");
        params.put("fun", "StoreGetAddress");
        params.put("StoreNo", StoreNo);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getStoreAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<StoreInfoAddressBean, Object>() {
                    @Override
                    public void onResponse(StoreInfoAddressBean goodsListBeans, String status, Object page) {
                        saleContract.getStoreAddressSuccess(goodsListBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        saleContract.getDataFail(msg);

                    }
                }));
    }



    public void getPersonalAddress(String UserName,String SessionId){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddress_GetListByUserName");
        params.put("UserName", UserName);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getPersonalAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<StoreInfoAddressBean, Object>() {
                    @Override
                    public void onResponse(StoreInfoAddressBean goodsListBeans, String status, Object page) {
                        saleContract.getPersonalAddressSuccess(goodsListBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        saleContract.getPersonalAddressFail(msg);

                    }
                }));
    }

}
