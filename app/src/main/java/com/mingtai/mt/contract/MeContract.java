package com.mingtai.mt.contract;

import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/9.
 */
public interface MeContract extends IView {
    /**
     * 剩余余额
     */
    public void getBalanceSuccess(BalanceBean balanceBean);
    public void getBalanceFail(String msg);


    public void getSendVcodeSuccess(String s);
    public void getSendVcodeFail(String msg);
}
