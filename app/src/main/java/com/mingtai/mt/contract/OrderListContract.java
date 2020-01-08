package com.mingtai.mt.contract;

import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/6.
 */
public interface OrderListContract extends IView {
    public void sureReceiptSuccess(String collectDeleteBean);

    public void sureReceiptFail(String msg);
}
