package com.mingtai.mt.contract;

import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/10.
 */
public interface GetCashContract extends IView {
    public void getCashSuccess(String msg);

    public void getCashFail(String msg);

}
