package com.mingtai.mt.contract;

import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by kai
 * on 2019/12/28
 */
public interface LoginContract extends IView {
    public void setDataSuccess(AccountBean msg);
    public void setDataFail(String msg);
}
