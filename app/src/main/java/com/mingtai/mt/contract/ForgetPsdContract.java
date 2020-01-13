package com.mingtai.mt.contract;

import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.mvp.IView;

public interface ForgetPsdContract extends IView {

    public void onSendVcodeSuccess(String msg);
    public void onSendVcodeFail(String msg);


    public void setDataSuccess(AccountBean msg);
    public void setDataFail(String msg);
}
