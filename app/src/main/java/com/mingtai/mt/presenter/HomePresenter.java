package com.mingtai.mt.presenter;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.HomeContract;
import com.mingtai.mt.entity.ArticleBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.HomeMobileBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/12/25.
 */
public class HomePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private HomeContract homeContract;


    @Override
    public void onCreate(Context context,IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        homeContract = (HomeContract) view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }


    public void getHomeData(String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "获取数据中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Home");
        params.put("fun", "Mobile");
        params.put("SessionId", SessionId);
        if (ProApplication.isLogin){
            mCompositeSubscription.add(manager.getHomeDatas(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpResultCallBack<HomeMobileBean<ArrayList<ArticleBean>>, Object>() {
                        @Override
                        public void onResponse(HomeMobileBean<ArrayList<ArticleBean>> goodsListBeans, String status, ResultBean<HomeMobileBean<ArrayList<ArticleBean>>, Object> page) {
                            homeContract.getHomeSuccess(goodsListBeans);
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onErr(String msg, String status) {
                            homeContract.getHomeFail(msg);
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }));
        }else {
            mCompositeSubscription.add(manager.getHomeData(params)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpResultCallBack<HomeMobileBean, Object>() {
                        @Override
                        public void onResponse(HomeMobileBean goodsListBeans, String status, ResultBean<HomeMobileBean, Object> page) {
                            homeContract.getHomeSuccess(goodsListBeans);
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onErr(String msg, String status) {
                            homeContract.getHomeFail(msg);
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    }));
        }
    }

}
