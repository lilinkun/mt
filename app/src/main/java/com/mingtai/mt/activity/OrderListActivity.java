package com.mingtai.mt.activity;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.entity.ChooseItemBean;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.util.MingtaiUtil;

import java.util.ArrayList;

/**
 * Created by LG on 2020/1/4.
 */
public class OrderListActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initEventAndData() {

        ArrayList<ChooseItemBean> chooseItemBeans = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getParcelableArrayList("ChooseItemBeans");
        ArrayList<GoodsBean> goodsBeans = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getParcelableArrayList("GOODSBEANS");

    }
}
