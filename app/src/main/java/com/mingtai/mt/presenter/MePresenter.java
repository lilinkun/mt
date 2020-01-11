package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.MeContract;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.PersonalInfoBean;
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
public class MePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private MeContract meContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        mCompositeSubscription = new CompositeSubscription();
        meContract = (MeContract) view;
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

    public void getBalance(String SessionId) {
//        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "BankBase_GetBalance");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getBalance(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<BalanceBean, Object>() {
                    @Override
                    public void onResponse(BalanceBean balanceBean, String status, ResultBean<BalanceBean,Object> page) {
                        meContract.getBalanceSuccess(balanceBean);
//                        if (progressDialog != null && progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        meContract.getBalanceFail(msg);
//                        if (progressDialog != null && progressDialog.isShowing()) {
//                            progressDialog.dismiss();
//                        }
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
                        meContract.getSendVcodeSuccess(o);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        meContract.getSendVcodeFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }

    /**
     *
     * @param sessionId
     */
    public void verificationPsd(String PassWordTwo,String sessionId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "VerifyPassWord");
        params.put("PassWordTwo", PassWordTwo);
        params.put("SessionId", sessionId);
        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String,Object>() {

                    @Override
                    public void onResponse(String o, String status, ResultBean<String ,Object> page) {
                        meContract.verificationPsdSuccess(o);


                    }

                    @Override
                    public void onErr(String msg, String status) {
                        meContract.verificationPsdFail(msg);
                    }

                    @Override
                    public void onNext(ResultBean o) {
                        super.onNext(o);
                    }

                })
        );
    }


}
