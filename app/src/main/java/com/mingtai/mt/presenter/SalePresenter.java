package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.SaleContract;
import com.mingtai.mt.entity.BankBean;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;
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
                    public void onResponse(StoreInfoAddressBean goodsListBeans, String status, ResultBean<StoreInfoAddressBean, Object> page) {
                        saleContract.getStoreAddressSuccess(goodsListBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        saleContract.getStoreAddressFail(msg);

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
                    public void onResponse(StoreInfoAddressBean goodsListBeans, String status, ResultBean<StoreInfoAddressBean, Object> page) {
                        saleContract.getPersonalAddressSuccess(goodsListBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        saleContract.getPersonalAddressFail(msg);

                    }
                }));
    }


    /**
     * 点击下一步
     * @param ParentUserName
     * @param UserName
     */
    public void saleNext(String ParentUserName,String UserName){

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserResettlementISExits");
        params.put("ParentUserName", ParentUserName);
        params.put("UserName", UserName);
        mCompositeSubscription.add(manager.saleNext(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String goodsListBeans, String status, ResultBean<String, Object> page) {
                        saleContract.saleNextSuccess(goodsListBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        saleContract.saleNextFail(msg);

                    }
                }));
    }

    /**
     * 点击亲友人获取信息
     * @param UserName
     * @param UserType
     * @param SessionId
     */
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
                    public void onResponse(FriendsBean friendsBean, String status, ResultBean<FriendsBean, Object> o) {
                        saleContract.queryNameSuccess(friendsBean,status);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        saleContract.queryNameFail(msg);

                    }
                }));
    }


    public void chooseUpdateLevel( String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserLevelNameList");
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.getBankInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<BankBean>, Object>() {

                    @Override
                    public void onResponse(ArrayList<BankBean> addressBeans, String status, ResultBean<ArrayList<BankBean>, Object> page) {
                        saleContract.getLevelInfoSuccess(addressBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        saleContract.getLevelInfoFail(msg);
                    }
                })
        );
    }

}
