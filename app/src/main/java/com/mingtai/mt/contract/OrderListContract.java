package com.mingtai.mt.contract;

import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/6.
 */
public interface OrderListContract extends IView {
    public void getTlementSuccess(String msg);
    public void getTlementFail(String msg);
}
