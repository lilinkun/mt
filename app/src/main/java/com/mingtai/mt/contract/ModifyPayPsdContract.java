package com.mingtai.mt.contract;

import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/8.
 */
public interface ModifyPayPsdContract extends IView {
    public void onSendVcodeSuccess();

    public void onSendVcodeFail(String str);

    public void modifySuccess();

    public void modifyFail(String str);

    public void modifyPsdSuccess();

    public void modifyPsdFail(String msg);

}
