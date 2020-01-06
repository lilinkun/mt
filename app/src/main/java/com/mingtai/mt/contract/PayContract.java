package com.mingtai.mt.contract;

import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.entity.WxInfo;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2019/9/10.
 */
public interface PayContract extends IView {

    /**
     * 微信支付
     */
    public void sureWxOrderSuccess(WxInfo ordersn);

    public void sureWxOrderFail(String msg);

    /**
     * 余额支付
     */
    public void sureOrderSuccess(String str);

    public void sureOrderFail(String msg);


    /**
     * 剩余余额
     */
    public void getBalanceSuccess(BalanceBean balanceBean);

    public void getBalanceFail(String msg);


    public void setDataSuccess(OrderDetailBean orderDetailBeans);

    public void setDataFail(String msg);
}
