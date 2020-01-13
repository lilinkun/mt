package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.LoginContract;
import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;
import com.mingtai.mt.util.MingtaiUtil;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lilinkun
 * on 2019/12/28
 */
public class LoginPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private LoginContract loginContract;

    @Override
    public void onCreate(Context context, IView view) {
        mContext = context;
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

    public void setLogin(String account,String psw,String sessionId){

        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "登录中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Login");
        params.put("UserName", account);
        params.put("PassWord", psw);
        params.put("CheckCode", "APP");
        params.put("key", "APP");
        params.put("SessionId", sessionId);

        mCompositeSubscription.add(manager.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<AccountBean, Object>() {
                    @Override
                    public void onResponse(AccountBean goodsListBeans, String status, ResultBean<AccountBean, Object> o) {
                        loginContract.setDataSuccess(goodsListBeans);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        loginContract.setDataFail(msg);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));

    }



    public void getSettingParameter(String SessionId){

        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "获取数据中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Home");
        params.put("fun", "SettingParameter");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getHomeSettingData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<HomeBean, Object>() {
                    @Override
                    public void onResponse(HomeBean goodsListBeans, String status, ResultBean<HomeBean, Object> page) {
                        loginContract.getDataSuccess(goodsListBeans);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        loginContract.getDataFail(msg);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }
}
