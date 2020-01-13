package com.mingtai.mt.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/31.
 */

public class SplashActivity extends BaseActivity {

    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(1500, 1000);

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this,getResources().getColor(R.color.white));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

        myCountDownTimer.start();

    }

    /**
     * 获取验证码倒计时
     */
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            turnHome();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void turnHome(){
        Intent intent = null;
        intent = new Intent(getBaseContext(), LoginActivity.class);
        //启动MainActivity
        startActivity(intent);
        finish();
    }
}

