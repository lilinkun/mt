package com.mingtai.mt.servicer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.mingtai.mt.contract.MessageContract;
import com.mingtai.mt.presenter.MessagePresenter;

/**
 * Created by LG on 2020/1/9.
 */
public class MessageServicer extends Service implements MessageContract{

    private MessagePresenter messagePresenter = new MessagePresenter();

    private final IBinder mBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    @Override
    public void onSendVcodeSuccess(String msg) {

    }

    @Override
    public void onSendVcodeFail(String msg) {

    }

    @Override
    public void IsVerifySmsSuccess(String msg) {

    }

    @Override
    public void IsVerifySmsFail(String msg) {

    }
    public class LocalBinder extends Binder{
        MessageServicer getService(){
            return MessageServicer.this;
        }
    }

}
