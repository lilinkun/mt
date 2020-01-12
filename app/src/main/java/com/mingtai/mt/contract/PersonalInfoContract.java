package com.mingtai.mt.contract;

import com.mingtai.mt.entity.PersonalInfoBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/9.
 */
public interface PersonalInfoContract extends IView {
    public void modifySuccess();

    public void modifyFail(String msg);

    public void getInfoSuccess(PersonalInfoBean loginBean);

    public void LoginOutSuccess(String msg);
    public void LoginOutFail(String msg);


    public void getSendVcodeSuccess(String s);
    public void getSendVcodeFail(String msg);


    public void onSendVcodeSuccess(String msg);
    public void onSendVcodeFail(String msg);

    /**
     * 发送安全验证
     * @param msg
     */
    public void safetyVerificationCodeSuccess(String msg);
    public void safetyVerificationCodeFail(String msg);
}
