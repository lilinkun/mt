package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.TransferAccountsContract;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/10.
 */
public class TransferAccountsPresenter extends BasePresenter {

    private Context context;
    private DataManager manager;
    private CompositeSubscription compositeSubscription;
    private TransferAccountsContract transferAccountsContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.context = context;
        this.manager = new DataManager(context);
        compositeSubscription = new CompositeSubscription();
        transferAccountsContract = (TransferAccountsContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (compositeSubscription.hasSubscriptions()){
            compositeSubscription.unsubscribe();
        }
    }

    public void getTransferAccounts(String BounsPrice,String PassWordTwo,String AcceptUserName,String type,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(context,"请稍等...","获取数据中...",true);

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "BankCurrency");
        params.put("fun", "CurrencyGive");
        params.put("BounsPrice", BounsPrice);
        params.put("PassWordTwo", PassWordTwo);
        params.put("AcceptUserName", AcceptUserName);
        params.put("type", type);
        params.put("SessionId", SessionId);

        compositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {

                    @Override
                    public void onResponse(String goodsBean, String status, ResultBean<String,Object> page) {
                        transferAccountsContract.getTransferAccountsSuccess(page.getDesc());
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        transferAccountsContract.getTransferAccountsFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                }));

    }

}
