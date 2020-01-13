package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.ForgetPsdContract;
import com.mingtai.mt.contract.IntegralContract;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class ForgetPsdPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private ForgetPsdContract forgetPsdContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        forgetPsdContract = (ForgetPsdContract) view;
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
     * 发送短信验证码
     *
     * @param sessionId
     */
    public void SendSms(String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "SendSms");
        params.put("fun", "SendSafetyVerificationCode");
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>() {

                    @Override
                    public void onResponse(String o, String status, ResultBean<String ,Object> page) {
                        forgetPsdContract.onSendVcodeSuccess(o);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        forgetPsdContract.onSendVcodeFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

    /**
     * 忘记密码生成新的密码
     * @param sessionId
     */
    public void ForgetPsd(String UserName,String Code,String PassWord,String ConfirmPassWord,String UserCode,String Mobile,String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "ForgotPassword");
        params.put("UserName", UserName);
        params.put("Code", Code);
        params.put("PassWord", PassWord);
        params.put("ConfirmPassWord", ConfirmPassWord);
        params.put("UserCode", UserCode);
        params.put("Mobile", Mobile);
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>() {

                    @Override
                    public void onResponse(String o, String status, ResultBean<String ,Object> page) {
                        forgetPsdContract.onSendVcodeSuccess(o);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        forgetPsdContract.onSendVcodeFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }
}
