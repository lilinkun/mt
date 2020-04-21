package com.mingtai.mt.contract;

import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.entity.OrderDetailInfo;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/8.
 */
public interface AllOrderContract extends IView {

    public void setDataSuccess(OrderDetailInfo orderDetailBeans);

    public void setDataFail(String msg);

    public void exitOrderSuccess(String collectDeleteBean);

    public void exitOrderFail(String msg);

    public void sureReceiptSuccess(String collectDeleteBean);

    public void sureReceiptFail(String msg);
}
