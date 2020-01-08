package com.mingtai.mt.contract;

import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/7.
 */
public interface OrderSureContract extends IView {
    public void getTlementSuccess(String msg);
    public void getTlementFail(String msg);
}
