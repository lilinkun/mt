package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.AddressPickerContract;
import com.mingtai.mt.entity.ProvinceBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class AddressPickerPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private AddressPickerContract addressPickerContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        addressPickerContract = (AddressPickerContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }


    public void getLocalData(String parentId,final int localType){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls","Region");
        params.put("fun","RegionListDrop");
        params.put("ParentId",parentId);
        mCompositeSubscription.add(manager.getLocalData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<ProvinceBean>,Object>() {
                    @Override
                    public void onResponse(ArrayList<ProvinceBean> provinceBeans, String status, Object page) {
                        addressPickerContract.getDataSuccess(provinceBeans,localType);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        addressPickerContract.getDataFail(msg);
                    }
                }));
    }
}
