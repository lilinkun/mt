package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.GetCashContract;
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
public class GetCashPresenter extends BasePresenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private GetCashContract getCashContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        getCashContract = (GetCashContract) view;
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

    public void getCash(String TransactionAamount, String Brokerage, String PassWordTwo,String CurrencyTypeId, String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "提现中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "WithdrawCash");
        params.put("fun", "WithdrawCash_Add");
        params.put("TransactionAamount", TransactionAamount);
        params.put("Brokerage", Brokerage);
        params.put("PassWordTwo", PassWordTwo);
        params.put("CurrencyTypeId", CurrencyTypeId);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getSaveAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<Object, Object>() {

                    @Override
                    public void onResponse(Object grouponDetailBean, String status, ResultBean<Object, Object> page) {
                        getCashContract.getCashSuccess(page.getDesc());
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        getCashContract.getCashFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                }));

    }

}
