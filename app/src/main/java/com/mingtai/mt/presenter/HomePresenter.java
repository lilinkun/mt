package com.mingtai.mt.presenter;

import android.content.Context;
import android.view.View;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2019/12/25.
 */
public class HomePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private HomePresenter homePresenter;


    @Override
    public void onCreate(Context context,IView view) {
        homePresenter = (HomePresenter) view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }



}
