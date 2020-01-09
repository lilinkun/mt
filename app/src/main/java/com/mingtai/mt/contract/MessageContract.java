package com.mingtai.mt.contract;

import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/9.
 */
public interface MessageContract extends IView {

    public void onSendVcodeSuccess(String msg);
    public void onSendVcodeFail(String msg);

    public void IsVerifySmsSuccess(String msg);
    public void IsVerifySmsFail(String msg);
}
