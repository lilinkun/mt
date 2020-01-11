package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.IntegralContract;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.BalanceDetailBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.entity.UserBankBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/9.
 */
public class IntegralPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private IntegralContract integralContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        integralContract = (IntegralContract) view;
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


    public void getPriceData(String PageIndex, String PageCount, String type, String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "获取数据中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "BankCurrency");
        params.put("fun", "CurrencyList");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("type", type);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getAmountPrice(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<BalanceDetailBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<BalanceDetailBean> balanceDetailBeans, String status, ResultBean<ArrayList<BalanceDetailBean> ,Object> page) {
                        integralContract.getDataSuccess(balanceDetailBeans);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        integralContract.getDataFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }


    public void getBalance(String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "", true);

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "BankBase_GetBalance");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getBalance(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<BalanceBean, Object>() {
                    @Override
                    public void onResponse(BalanceBean balanceBean, String status, ResultBean<BalanceBean, Object> page) {
                        integralContract.getBalanceSuccess(balanceBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        integralContract.getBalanceFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }


    public void getSafetyVerificationCode(String code,String SessionId) {
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "", true);

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
                        integralContract.safetyVerificationCodeSuccess(str);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        integralContract.safetyVerificationCodeFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
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
                        integralContract.onSendVcodeSuccess(o);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        integralContract.onSendVcodeFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

    /**
     * 获取银行卡信息
     *
     * @param SessionId
     */
    public void getBankCard(String SessionId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseBankGet");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getBankBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<UserBankBean, Object>() {
                    @Override
                    public void onResponse(UserBankBean balanceDetailBeans, String status, ResultBean<UserBankBean,Object> page) {
                        integralContract.getBankSuccess(balanceDetailBeans);
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        integralContract.getBankFail(msg);
                    }
                }));
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
                        integralContract.getSendVcodeSuccess(o);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        integralContract.getSendVcodeFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

}