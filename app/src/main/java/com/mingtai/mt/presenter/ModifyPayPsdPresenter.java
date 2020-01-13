package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.ModifyPayPsdContract;
import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/8.
 */
public class ModifyPayPsdPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ModifyPayPsdContract modifyPayPsdContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        modifyPayPsdContract = (ModifyPayPsdContract) view;
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
     * 修改支付密码
     *
     * @param sessionId
     */
    public void modifyPsd(String oldPwd, String PassWord, String ConfirmPassWord,int type, String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        if (type == 1) {
            params.put("fun", "UserBaseChangePassWord_Two");
        }else {
            params.put("fun","UserBaseChangePassWord");
        }
        params.put("oldPwd", oldPwd);
        params.put("PassWord", PassWord);
        params.put("ConfirmPassWord", ConfirmPassWord);
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.modifyPsd(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>() {

                    @Override
                    public void onResponse(String o, String status, ResultBean<String,Object> page) {
                        modifyPayPsdContract.modifySuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        modifyPayPsdContract.modifyFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

    /**
     * 修改密码
     *
     * @param mobile
     * @param code
     */
    public void modify(String mobile, String code, String psd, String SessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UpdatePassWord");
        params.put("new_password", psd);
        params.put("mobile", mobile);
        params.put("Code", code);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>() {

                    @Override
                    public void onResponse(String o, String status,ResultBean<String,Object> page) {
                        modifyPayPsdContract.modifyPsdSuccess();
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        modifyPayPsdContract.modifyPsdFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }
}

