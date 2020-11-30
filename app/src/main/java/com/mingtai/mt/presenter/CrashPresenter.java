package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.LoginContract;
import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;
import com.mingtai.mt.util.UToast;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lilinkun
 * on 2019/12/28
 */
public class CrashPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;

    @Override
    public void onCreate(Context context, IView view) {
        mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void setCrashFile(String account,String psw){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Home");
        params.put("fun", "WriteLog");
        params.put("UserName", account);
        params.put("Error", psw);

        mCompositeSubscription.add(manager.modifyPsd(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String o, String status, ResultBean<String,Object> page) {
                        UToast.show(mContext,o+"");
                    }

                    @Override
                    public void onErr(String msg, String status) {
                    }
                }));

    }


}
