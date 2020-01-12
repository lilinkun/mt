package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.PersonalInfoContract;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/9.
 */
public class PersonalInfoPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private PersonalInfoContract personalInfoContract;


    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        personalInfoContract = (PersonalInfoContract) view;
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


    public void LoginOut(String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "退出登录中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "Logout");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.loginout(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {

                    @Override
                    public void onResponse(String selfGoodsBeans, String status, ResultBean<String,Object> page) {
                        personalInfoContract.LoginOutSuccess(selfGoodsBeans);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        personalInfoContract.LoginOutFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

    /**
     * 验证密码是否有效
     *
     * @param sessionId
     */
    public void SendSms(String type,String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "SendSms");
        params.put("fun", "IsVerifySms");
        params.put("type", type);
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>() {

                    @Override
                    public void onResponse(String o, String status, ResultBean<String ,Object> page) {
                        personalInfoContract.getSendVcodeSuccess(o);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        personalInfoContract.getSendVcodeFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
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
                        personalInfoContract.onSendVcodeSuccess(o);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        personalInfoContract.onSendVcodeFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

    public void getSafetyVerificationCode(String code,String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "验证中...", true);

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "SendSms");
        params.put("fun", "SafetyVerificationCode");
        params.put("code",code);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String str, String status, ResultBean<String, Object> page) {
                        personalInfoContract.safetyVerificationCodeSuccess(str);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        personalInfoContract.safetyVerificationCodeFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

}
