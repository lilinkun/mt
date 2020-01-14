package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.WebviewContract;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/14.
 */
public class WebviewPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private WebviewContract webviewContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        this.manager = new DataManager(context);
        mCompositeSubscription = new CompositeSubscription();
        webviewContract = (WebviewContract) view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    public void getWebview(String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext,"请稍等...","获取数据中...",true);

        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "User_UserLogins");
        params.put("SessionId", SessionId);

        mCompositeSubscription.add(manager.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {

                    @Override
                    public void onResponse(String goodsBean, String status, ResultBean<String,Object> page) {
                        webviewContract.webviewSuccess(goodsBean);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        webviewContract.webviewFail(msg);
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                }));
    }
}
