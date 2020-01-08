package com.mingtai.mt.contract;

import com.mingtai.mt.entity.OrderBean;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/7.
 */
public interface SelfOrderContract extends IView{
    public void getDataSuccess(ArrayList<OrderBean> selfOrderBeans);

    public void getDataFail(String msg);

    public void exitOrderSuccess(String collectDeleteBean);

    public void exitOrderFail(String smg);

    public void cancelOrderSuccess(String collectDeleteBean);

    public void cancelOrderFail(String smg);
}
