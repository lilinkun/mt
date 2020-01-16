package com.mingtai.mt.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;

import com.mingtai.mt.activity.LoginActivity;
import com.mingtai.mt.util.ActivityUtil;
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
        if (text.contains("登录已失效")){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("loginerror","error");
            startActivity(intent);
            ActivityUtil.finishAll1();
        }
    }

    protected void toast(@NonNull String text, int duration) {
        UToast.show(this, text, duration);
    }

    public abstract int getLayoutId();

    public abstract void initEventAndData();



    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void myPermission() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
