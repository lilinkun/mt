package com.mingtai.mt.contract;

import com.mingtai.mt.entity.TiaoboHistoryBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/17.
 */
public interface PointContract extends IView {
    /**
     * 调拨
     * @param msg
     */
    public void getPointSuccess(String msg);
    public void getPointFail(String msg);


    public void getPointHistorySuccess(TiaoboHistoryBean tiaoboHistoryBean);
    public void getPointHistoryFail(String msg);
}
