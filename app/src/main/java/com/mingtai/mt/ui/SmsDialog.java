package com.mingtai.mt.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.util.UToast;

import okhttp3.internal.Util;

/**
 * Created by LG on 2020/1/10.
 */
public class SmsDialog extends Dialog {

    private Context context;
    private EditText editText;
    private Button button;
    private Handler handler;
    private TextView tv_mobile;
    private TextView tv_send;
    private TextView tv_psd;
    private LinearLayout ll_mobile;
    private String mobile;
    private int type;
    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);

    public SmsDialog(Context context,String msg,Handler handler,int type) {
        super(context);
        this.context = context;
        this.handler = handler;
        this.mobile = msg;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sms);

        editText = (EditText) findViewById(R.id.et_sms);
        button = (Button) findViewById(R.id.btn_sure);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_send = (TextView) findViewById(R.id.tv_send);
        ll_mobile = (LinearLayout) findViewById(R.id.ll_mobile);
        tv_psd = (TextView) findViewById(R.id.tv_psd);

        if (type == 1 || type == 2){
            tv_send.setVisibility(View.GONE);
            ll_mobile.setVisibility(View.GONE);
            editText.setHint("请输入支付密码");
            tv_psd.setVisibility(View.VISIBLE);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }


        tv_mobile.setText(mobile);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (editText.getText().toString().trim().length()>0) {
                        Message message = new Message();
                        if (type == 1 || type == 2){
                            message.what = 0x222;
                        }else {
                            message.what = 0x112;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("VCode",editText.getText().toString());
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }else {
                        if (type == 1 || type == 2){
                            UToast.show(context,"请输入密码");
                        }else {
                            UToast.show(context, "请输入验证码");
                        }
                    }


            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0x1234);
            }
        });

    }

    public void setStart(){
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
            //防止计时过程中重复点击
            tv_send.setClickable(false);
            tv_send.setText(l / 1000 + "s");
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tv_send.setText(R.string.register_send_vcoed);
            //设置可点击
            tv_send.setClickable(true);

            tv_send.setTextColor(context.getResources().getColor(R.color.white));
        }
    }


}
