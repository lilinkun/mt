package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.AddAddressContract;
import com.mingtai.mt.entity.ProvinceBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/10.
 */
public class AddAddressPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private AddAddressContract addAddressContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        addAddressContract = (AddAddressContract) view;
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

    public void getLocalData(String parentId, final int localType) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Dict_Region");
        params.put("fun", "RegionListDrop");
        params.put("parentId", parentId);
        mCompositeSubscription.add(manager.getLocalData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<ProvinceBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<ProvinceBean> provinceBeans, String status, ResultBean<ArrayList<ProvinceBean>, Object> page) {
                        addAddressContract.getDataSuccess(provinceBeans, localType);
                    }

                    @Override
                    public void onErr(String msg, String status) {

                    }
                }));
    }

    public void getSaveAddress(String Name, String Province, String City, String District, String Address, String ZipCode, String Mobile, String IsDefault, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressCreate");
        params.put("Name", Name);
        params.put("prov", Province);
        params.put("city", City);
        params.put("area", District);
        params.put("Address", Address);
        params.put("Post", ZipCode);
        params.put("Mobile", Mobile);
        params.put("IsDefault", IsDefault);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getSaveAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<Object,Object>() {

                    @Override
                    public void onResponse(Object o, String status, ResultBean<Object,Object> page) {
                        addAddressContract.getSaveSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        addAddressContract.getSaveFail(msg);
                    }
                }));
    }

    /**
     * 修改地址
     */
    public void modifyAddress(String AddressId, String Consignee, String Province, String City, String District, String Address, String ZipCode, String Mobile, String IsDefault, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressUpdate");
        params.put("Name", Consignee);
        params.put("AddressID", AddressId);
        params.put("prov", Province);
        params.put("city", City);
        params.put("area", District);
        params.put("Address", Address);
        params.put("Post", ZipCode);
        params.put("Mobile", Mobile);
        params.put("IsDefault", IsDefault);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getSaveAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<Object,Object>() {

                    @Override
                    public void onResponse(Object o, String status, ResultBean<Object,Object> page) {
                        addAddressContract.modifySuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        addAddressContract.modifyFail(msg);
                    }
                }));
    }
}
