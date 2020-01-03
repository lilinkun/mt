package com.mingtai.mt.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.GoodsAdapter;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.GoodsContract;
import com.mingtai.mt.entity.CategoryBean;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.presenter.GoodsPresenter;
import com.mingtai.mt.ui.PagerSlidingTabStrip;
import com.mingtai.mt.ui.SpaceItemDecoration;
import com.mingtai.mt.util.MingtaiUtil;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by kai
 * on 2019/12/31
 */
public class GoodsActivity extends BaseActivity implements GoodsContract{

    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip pagerSlidingTabStrip;
    @BindView(R.id.rv_goods)
    RecyclerView rv_goods;

    private GoodsPresenter goodsPresenter = new GoodsPresenter();
    private int position = 0;
    private ArrayList<CategoryBean> categoryBeans;
    private ArrayList<GoodsBean> goodsBeans;
    private int goodsType = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x110:

                    position = msg.getData().getInt("position");

                    goodsPresenter.getGoods("1","20",categoryBeans.get(position).getCategoryID(),goodsType+"",ProApplication.SESSIONID(GoodsActivity.this));

                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods;
    }

    @Override
    public void initEventAndData() {

        goodsPresenter.onCreate(this,this);
        goodsPresenter.getCategory(ProApplication.SESSIONID(this));

        goodsType = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("GoodsType");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        rv_goods.setLayoutManager(linearLayoutManager);
        rv_goods.addItemDecoration(new SpaceItemDecoration(0, 20, 0));
    }

    @Override
    public void getCategoryDataSuccess(ArrayList<CategoryBean> categoryBeans) {
        this.categoryBeans = categoryBeans;
        pagerSlidingTabStrip.setTitles(categoryBeans, 0, handler);

        goodsPresenter.getGoods("1","20",categoryBeans.get(0).getCategoryID(),goodsType+"",ProApplication.SESSIONID(GoodsActivity.this));

    }

    @Override
    public void getCategoryDataFail(String msg) {
        toast(msg);
    }

    @Override
    public void getGoodsDataSuccess(ArrayList<GoodsBean> goodsBeans) {
        this.goodsBeans = goodsBeans;
        rv_goods.setVisibility(View.VISIBLE);
        GoodsAdapter goodsAdapter = new GoodsAdapter(this,goodsBeans);
        rv_goods.setAdapter(goodsAdapter);
    }

    @Override
    public void getGoodsDataFail(String msg) {
        if (msg.contains("查无数据")){
            rv_goods.setVisibility(View.GONE);
        }else {
            toast(msg);
        }
    }


}
