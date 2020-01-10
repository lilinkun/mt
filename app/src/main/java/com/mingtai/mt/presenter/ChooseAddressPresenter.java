package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.ChooseAddressContract;
import com.mingtai.mt.entity.AddressBean;
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
public class ChooseAddressPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ChooseAddressContract chooseAddressContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        chooseAddressContract = (ChooseAddressContract) view;
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
     * 获取收货地址
     *
     * @param PageIndex
     * @param PageCount
     * @param SessionId
     */
    public void getAddress(String PageIndex, String PageCount, String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.getConsigneeAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<AddressBean>, Object>() {

                    @Override
                    public void onResponse(ArrayList<AddressBean> addressBeans, String status, ResultBean<ArrayList<AddressBean>, Object> page) {
                        chooseAddressContract.setDataSuccess(addressBeans);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        chooseAddressContract.setDataFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                })
        );
    }


    /**
     * 删除地址
     *
     * @param userAddressId
     */
    public void deletAddress(String userAddressId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressDelete");
        params.put("AddressID", userAddressId);
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>() {

                    @Override
                    public void onResponse(String o, String status, ResultBean<String,Object> page) {
                        chooseAddressContract.deleteSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        chooseAddressContract.deleteFail(msg);
                    }
                })
        );
    }


    /**
     * 设置默认地址
     *
     * @param UserAddressId
     * @param SessionId
     */
    public void isDefault(String UserAddressId, String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "ReceiptAddress");
        params.put("fun", "ReceiptAddressSetDefault");
        params.put("AddressID", UserAddressId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.isDefault(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {

                    @Override
                    public void onResponse(String isDefaultStr, String status, ResultBean<String, Object> page) {
                        chooseAddressContract.isDefaultSuccess(isDefaultStr);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        chooseAddressContract.isDefaultFail(msg);
                    }
                }));
    }
}
