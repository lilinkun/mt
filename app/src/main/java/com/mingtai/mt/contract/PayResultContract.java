package com.mingtai.mt.contract;

import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/8.
 */
public interface PayResultContract extends IView {

    public void setDataSuccess(OrderDetailBean orderDetailBeans);

    public void setDataFail(String msg);


    /**
     * 剩余余额
     */
    public void getBalanceSuccess(BalanceBean balanceBean);

    public void getBalanceFail(String msg);

}