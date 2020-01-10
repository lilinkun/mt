package com.mingtai.mt.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.GoodsAdapter;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.GoodsContract;
import com.mingtai.mt.entity.CategoryBean;
import com.mingtai.mt.entity.ChooseItemBean;
import com.mingtai.mt.entity.CustomPriceBean;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.presenter.GoodsPresenter;
import com.mingtai.mt.ui.PagerSlidingTabStrip;
import com.mingtai.mt.ui.SpaceItemDecoration;
import com.mingtai.mt.util.ActivityUtil;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lilinkun
 * on 2019/12/31
 */
public class GoodsActivity extends BaseActivity implements GoodsContract, GoodsAdapter.CheckInterface {

    @BindView(R.id.tab_strip)
    PagerSlidingTabStrip pagerSlidingTabStrip;
    @BindView(R.id.rv_goods)
    RecyclerView rv_goods;
    @BindView(R.id.tv_total_goods_price)
    TextView tv_total_goods_price;
    @BindView(R.id.tv_total_integral)
    TextView tv_total_integral;
    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_total_ShippingFree)
    TextView tv_total_ShippingFree;
    @BindView(R.id.tv_point)
    TextView tv_point;

    private GoodsPresenter goodsPresenter = new GoodsPresenter();
    private int position = 0;
    private ArrayList<CategoryBean> categoryBeans = new ArrayList<>();
    private ArrayList<GoodsBean> goodsBeans;
    private ArrayList<ChooseItemBean> chooseItemBeans = new ArrayList<>();
    private ArrayList<GoodsBean> chooseGoodsBeans = new ArrayList<>();
    private ArrayList<String> chooseGoodsList = new ArrayList<>();
    private StoreInfoAddressBean addressBean;
    private int userlevel = 0;
    private int goodsType = 0;
    private int deliveryMethod = 0;
    private int ShippingFree;
    private int ShippingFreePrice;
    private int total_ShippingFree = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x110:

                    position = msg.getData().getInt("position");

                    goodsPresenter.getGoods("1","20",categoryBeans.get(position).getCategoryID(),goodsType+"",userlevel+"",ProApplication.SESSIONID(GoodsActivity.this));

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

        ActivityUtil.addHomeActivity(this);
        ActivityUtil.addActivity(this);

        ShippingFree = ProApplication.mHomeBean.getIsShippingFree();
        ShippingFreePrice = ProApplication.mHomeBean.getShippingPrice();

        userlevel = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("UserLevel");
        goodsType = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("GoodsType");
        deliveryMethod = getIntent().getBundleExtra(MingtaiUtil.TYPEID).getInt("deliveryMethod");
        addressBean = (StoreInfoAddressBean)getIntent().getBundleExtra(MingtaiUtil.TYPEID).getSerializable("StoreInfoAddressBean");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        rv_goods.setLayoutManager(linearLayoutManager);
        rv_goods.addItemDecoration(new SpaceItemDecoration(0, 20, 0));
    }

    @Override
    public void getCategoryDataSuccess(ArrayList<CategoryBean> categoryBeans) {
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.setCategoryName("全部");
        categoryBean.setCategoryID("");

        this.categoryBeans.add(categoryBean);
        this.categoryBeans.addAll(categoryBeans);
        pagerSlidingTabStrip.setTitles(this.categoryBeans, 0, handler);

        goodsPresenter.getGoods("1","50","",goodsType+"",userlevel+"",ProApplication.SESSIONID(GoodsActivity.this));

    }

    @Override
    public void getCategoryDataFail(String msg) {
        toast(msg);
    }

    @Override
    public void getGoodsDataSuccess(ArrayList<GoodsBean> goodsBeans) {
        this.goodsBeans = goodsBeans;
        rv_goods.setVisibility(View.VISIBLE);
        GoodsAdapter goodsAdapter = new GoodsAdapter(this,goodsBeans,chooseItemBeans);
        goodsAdapter.setCheckInterface(this);
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


    @Override
    public void checkItem(String goodsSn,int position, int num) {

        if (chooseGoodsList.size() > 0){

            if (chooseGoodsList.contains(goodsSn)){
                for (int i = 0; i < chooseItemBeans.size(); i++) {
                    if (chooseItemBeans.get(i).getGoodsSn().equals(goodsSn)) {
                        int oldNum = chooseItemBeans.get(i).getNum();
                        chooseItemBeans.get(i).setNum(num);
                        int rechangeNum = num - oldNum;

                        double p = Double.valueOf(tv_total_price.getText().toString()) + goodsBeans.get(position).getPrice() * rechangeNum;
                        int intergral = Integer.valueOf(tv_total_integral.getText().toString()) + goodsBeans.get(position).getIntegral() * rechangeNum;
                        tv_total_price.setText(p+"");
                        tv_total_integral.setText("" +intergral);
                        tv_point.setText("  分值:" + intergral );

                        if (p < ShippingFree){
                            if (tv_total_ShippingFree.getText().toString().equals("0")) {
                                total_ShippingFree = ShippingFreePrice;
                                p = p +total_ShippingFree;
                            }
                        }else {
                            total_ShippingFree = 0;
                        }
                        tv_total_ShippingFree.setText(total_ShippingFree+"");
                        tv_total_goods_price.setText(MingtaiUtil.isCoin(p) +"");
                    }
                }
            }else {
                chooseGoodsList.add(goodsSn);
                ChooseItemBean chooseItemBean = new ChooseItemBean();
                chooseItemBean.setGoodsSn(goodsSn);
                chooseItemBean.setNum(num);
                chooseItemBean.setGoodsId(goodsBeans.get(position).getGoodsId());
                chooseItemBeans.add(chooseItemBean);
                chooseGoodsBeans.add(goodsBeans.get(position));

                double p = goodsBeans.get(position).getPrice() * num + Double.valueOf(tv_total_price.getText().toString());
                int intergral = goodsBeans.get(position).getIntegral() * num + Integer.valueOf(tv_total_integral.getText().toString());
                tv_total_price.setText(p+"");
                tv_total_integral.setText("" +intergral);
                tv_point.setText("  分值:" + intergral );

                if (p < ShippingFree){
                    if (tv_total_ShippingFree.getText().toString().equals("0")) {
                        total_ShippingFree = ShippingFreePrice;
                        p = p +total_ShippingFree;
                    }
                }else {
                    total_ShippingFree = 0;
                }
                tv_total_ShippingFree.setText(total_ShippingFree+"");
                tv_total_goods_price.setText(MingtaiUtil.isCoin(p+total_ShippingFree) +"");
            }

        }else {
            chooseGoodsList.add(goodsSn);
            ChooseItemBean chooseItemBean = new ChooseItemBean();
            chooseItemBean.setGoodsSn(goodsSn);
            chooseItemBean.setGoodsId(goodsBeans.get(position).getGoodsId());
            chooseItemBean.setNum(num);

            chooseItemBeans.add(chooseItemBean);
            chooseGoodsBeans.add(goodsBeans.get(position));

            double p = goodsBeans.get(position).getPrice() * num;
            int intergral = goodsBeans.get(position).getIntegral() * num;
            tv_total_price.setText(p+"");
            tv_total_integral.setText("" +intergral);
            tv_point.setText("  分值:" + intergral );

            if (p < ShippingFree){
                total_ShippingFree = ShippingFreePrice;
            }else {
                total_ShippingFree = 0;
            }
            tv_total_ShippingFree.setText(total_ShippingFree+"");
            tv_total_goods_price.setText(MingtaiUtil.isCoin(p+total_ShippingFree) +"");
        }
    }

    @Override
    public void unCheckItem(String goodsSn, int position) {
        chooseGoodsList.remove(goodsSn);

        for (int i = 0; i < chooseItemBeans.size();i++) {
            if (chooseItemBeans.get(i).getGoodsSn().equals(goodsSn)){
                int num = chooseItemBeans.get(i).getNum();
                chooseGoodsBeans.remove(i);

                double p = Double.valueOf(tv_total_price.getText().toString()) - goodsBeans.get(position).getPrice() * num;
                int intergral = Integer.valueOf(tv_total_integral.getText().toString()) - goodsBeans.get(position).getIntegral() * num;
                tv_total_price.setText( p +"");
                tv_total_integral.setText(intergral + "");
                tv_point.setText("  分值:" + intergral );

                if (p < ShippingFree){
                    total_ShippingFree = ShippingFreePrice;
                }else {
                    total_ShippingFree = 0;
                }
                tv_total_ShippingFree.setText(total_ShippingFree+"");
                tv_total_goods_price.setText(MingtaiUtil.isCoin(p+total_ShippingFree) +"");
                chooseItemBeans.remove(i);
            }
        }

    }


    @OnClick({R.id.tv_settlement})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_settlement:

                if (chooseGoodsList.size() > 0) {

                    CustomPriceBean CustomPriceBean = new CustomPriceBean(tv_total_goods_price.getText().toString(),tv_total_ShippingFree.getText().toString(),
                            tv_total_integral.getText().toString(),tv_total_price.getText().toString(),deliveryMethod,goodsType,userlevel);

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("ChooseItemBeans", chooseItemBeans);
                    bundle.putParcelableArrayList("GOODSBEANS", chooseGoodsBeans);
                    bundle.putSerializable("ADDRESS", addressBean);
                    bundle.putSerializable("CUSTOMPRICEBEAN",CustomPriceBean);

                    UiHelper.launcherBundle(this, OrderSureActivity.class, bundle);

                    /*for (int i = 0; i < chooseItemBeans.size();i++){
                        if ( goodsIdStr.equals("") && goodsNum.equals("")) {
                            goodsIdStr = chooseItemBeans.get(i).getGoodsId();
                            goodsNum = chooseItemBeans.get(i).getNum()+"";
                        }else {
                            goodsIdStr = goodsIdStr + ("," + chooseItemBeans.get(i).getGoodsId());
                            goodsNum = goodsNum + "," + chooseItemBeans.get(i).getNum();
                        }
                    }
                    goodsPresenter.settlement(goodsIdStr,tv_total_goods_price.getText().toString(),tv_total_ShippingFree.getText().toString(),goodsNum
                            ,addressBean.getName(),addressBean.getMobile(),ProApplication.mAccountBean.getStoreNo(),deliveryMethod+"",addressBean.getProv()+"",
                            addressBean.getCity()+"",addressBean.getArea()+"",goodsType+"",addressBean.getAddress(),addressBean.getUserName(),addressBean.getPost(),
                            "",tv_total_integral.getText().toString(),ProApplication.SESSIONID(this));
                }*/

                    break;
                }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.removeHomeActivity(this);
        ActivityUtil.removeActivity(this);
    }

}
