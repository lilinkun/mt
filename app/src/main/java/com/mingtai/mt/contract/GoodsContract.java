package com.mingtai.mt.contract;

import com.mingtai.mt.entity.CategoryBean;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/3.
 */
public interface GoodsContract extends IView {

    public void getCategoryDataSuccess(ArrayList<CategoryBean> categoryBeans);
    public void getCategoryDataFail(String msg);

    public void getGoodsDataSuccess(ArrayList<GoodsBean> categoryBeans);
    public void getGoodsDataFail(String msg);
}
