package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2019/12/25.
 */
public interface Presenter {
    void onCreate(Context context,IView view);

    void onStart();

    void onStop();

}