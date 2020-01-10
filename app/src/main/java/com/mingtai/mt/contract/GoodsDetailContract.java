package com.mingtai.mt.contract;

import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.mvp.IView;

/**
 * Created by LG on 2020/1/10.
 */
public interface GoodsDetailContract extends IView {

    public void getDataSuccess(GoodsBean goodsBean);
    public void getDataFail(String msg);

}
