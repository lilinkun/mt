package com.mingtai.mt.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.mingtai.mt.util.UToast;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LG on 2019/12/12.
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    Unbinder unbinder = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        initEventAndData();
    }

    protected void toast(@StringRes int resId) {
        UToast.show(this, resId);
    }

    protected void toast(@StringRes int resId, int duration) {
        UToast.show(this, resId, duration);
    }

    protected void toast(@NonNull String text) {
        UToast.show(this, text);
    }

    protected void toast(@NonNull String text, int duration) {
        UToast.show(this, text, duration);
    }

    public abstract int getLayoutId();

    public abstract void initEventAndData();
}
