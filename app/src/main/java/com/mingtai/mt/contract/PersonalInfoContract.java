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
}
