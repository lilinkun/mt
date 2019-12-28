package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.LoginContract;
import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;
import com.mingtai.mt.util.MingtaiUtil;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by kai
 * on 2019/12/28
 */
public class LoginPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private LoginContract loginContract;

    @Override
    public void onCreate(Context context, IView view) {

        manager = new DataManager(context);
        loginContract = (LoginContract) view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void setLogin(String account,String psw){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Login");
        params.put("UserName", account);
        params.put("PassWord", psw);
        params.put("CheckCode", "APP");
        params.put("key", "APP");
        params.put("SessionId", MingtaiUtil.SESSIONID(mContext));

        mCompositeSubscription.add(manager.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<AccountBean, Object>() {
                    @Override
                    public void onResponse(AccountBean goodsListBeans, String status, Object o) {
                        loginContract.setDataSuccess(goodsListBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        loginContract.setDataFail(msg);

                    }
                }));

    }
}
