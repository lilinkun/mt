package com.mingtai.mt.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.util.UToast;

/**
 * Created by LG on 2020/1/10.
 */
public class SmsDialog extends Dialog {

    private Context context;
    private EditText editText;
    private Button button;
    private Handler handler;
    private TextView tv_mobile;
    private TextView tv_code;
    private String mobile;
    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);

    public SmsDialog(Context context,String msg,Handler handler) {
        super(context);
        this.context = context;
        this.handler = handler;
        this.mobile = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sms);

        editText = (EditText) findViewById(R.id.et_sms);
        button = (Button) findViewById(R.id.btn_sure);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);

        tv_mobile.setText(mobile);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length()>0) {
                    Message message = new Message();
                    message.what = 0x112;
                    Bundle bundle = new Bundle();
                    bundle.putString("VCode",editText.getText().toString());
                    message.setData(bundle);
                    handler.sendMessage(message);
                }else {
                    UToast.show(context,"请输入验证码");
                }
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
            tv_code.setClickable(false);
            tv_code.setText(l / 1000 + "s");
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tv_code.setText(R.string.register_send_vcoed);
            //设置可点击
            tv_code.setClickable(true);

            tv_code.setTextColor(context.getResources().getColor(R.color.white));
        }
    }


}
