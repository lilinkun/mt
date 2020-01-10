package com.mingtai.mt.contract;

import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.BalanceDetailBean;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/9.
 */
public interface IntegralContract extends IView {
    /**
     * 剩余余额
     */
    public void getBalanceSuccess(BalanceBean balanceBean);
    public void getBalanceFail(String msg);


    public void getDataSuccess(ArrayList<BalanceDetailBean> amountPriceBean);
    public void getDataFail(String msg);

    /**
     * 发送安全验证
     * @param msg
     */
    public void safetyVerificationCodeSuccess(String msg);
    public void safetyVerificationCodeFail(String msg);
}
