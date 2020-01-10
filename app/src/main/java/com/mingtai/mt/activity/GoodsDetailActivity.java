package com.mingtai.mt.activity;

import android.graphics.Paint;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.GoodsDetailContract;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.presenter.GoodsDetailPresenter;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;

import butterknife.BindView;

/**
 * Created by LG on 2020/1/10.
 */
public class GoodsDetailActivity extends BaseActivity implements GoodsDetailContract {

    @BindView(R.id.tv_goods_price)
    TextView tv_goods_price;
    @BindView(R.id.tv_goods_old_price)
    TextView tv_goods_old_price;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.tv_goods_name)
    TextView tv_goods_name;

    private GoodsDetailPresenter goodsDetailPresenter = new GoodsDetailPresenter();

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    public void initEventAndData() {
        Eyes.translucentStatusBar(this, false);

        goodsDetailPresenter.onCreate(this,this);

        String str = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("GOODSID");

        goodsDetailPresenter.getGoodsDetail(str, ProApplication.SESSIONID(this));

    }


    @Override
    public void getDataSuccess(GoodsBean goodsBean) {
        tv_goods_price.setText(goodsBean.getPrice()+"");
        tv_goods_old_price.setText(goodsBean.getMarketPrice()+"");
        tv_goods_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_number.setText(goodsBean.getIntegral() + "");
        tv_goods_name.setText(goodsBean.getGoodsName());
    }

    @Override
    public void getDataFail(String msg) {

    }
}
