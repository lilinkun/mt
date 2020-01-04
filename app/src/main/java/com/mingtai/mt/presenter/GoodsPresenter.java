package com.mingtai.mt.presenter;

import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.GoodsContract;
import com.mingtai.mt.entity.CategoryBean;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/3.
 */
public class GoodsPresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private GoodsContract goodsContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(context);
        goodsContract = (GoodsContract) view;
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        mCompositeSubscription.unsubscribe();
    }


    /**
     * 获取商品分类
     */
    public void getCategory(String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Category");
        params.put("fun", "CategoryGuestList");
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getCategoryDataSuccess(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<CategoryBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<CategoryBean> categoryBeans, String status, Object page) {
                        goodsContract.getCategoryDataSuccess(categoryBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        goodsContract.getCategoryDataFail(msg);

                    }
                }));

    }

    /**
     * 获取商品
     */
    public void getGoods(String PageIndex,String PageCount,String CategoryId,String GoodsType,String UserLevel,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        params.put("fun", "GoodsRecurringListVip");
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("CategoryId", CategoryId);
        params.put("GoodsType", GoodsType);
        params.put("UserLevel", UserLevel);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getGoods(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsBean>, Object>() {
                    @Override
                    public void onResponse(ArrayList<GoodsBean> goodsBeans, String status, Object page) {
                        goodsContract.getGoodsDataSuccess(goodsBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        goodsContract.getGoodsDataFail(msg);

                    }
                }));

    }


    /**
     *  结算
     * @param GoodsId    商品
     * @param OrderAmount  金额
     * @param ShippingFree 运费
     * @param goodsNum    数量
     * @param Consignee   收货人
     * @param Mobile      手机
     * @param SingleCenterName  店铺
     * @param deliveryMethod   配送方式
     * @param prov      省份
     * @param city      城市
     * @param area      地区
     * @param OrderType  订单类型
     * @param Address    收货地址
     * @param UserName   经销商
     * @param Post       邮编
     * @param PostScript  说明
     * @param Integral    总分值
     * @param SessionId
     */
    public void settlement(String GoodsId,String OrderAmount,String ShippingFree,String goodsNum,String Consignee,String Mobile,String SingleCenterName,
                           String deliveryMethod,String prov,String city,String area,String OrderType,String Address,String UserName,String Post,
                           String PostScript,String Integral,String SessionId){
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseRecurring");
        params.put("GoodsId", GoodsId);
        params.put("OrderAmount", OrderAmount);
        params.put("ShippingFree", ShippingFree);
        params.put("goodsNum", goodsNum);
        params.put("Consignee", Consignee);
        params.put("Mobile", Mobile);
        params.put("SingleCenterName", SingleCenterName);
        params.put("deliveryMethod", deliveryMethod);
        params.put("prov", prov);
        params.put("city", city);
        params.put("area", area);
        params.put("OrderType", OrderType);
        params.put("Address", Address);
        params.put("UserName", UserName);
        params.put("Post", Post);
        params.put("PostScript", PostScript);
        params.put("Integral", Integral);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.settlement(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<String, Object>() {
                    @Override
                    public void onResponse(String goodsBeans, String status, Object page) {
                        goodsContract.settlementSuccess(goodsBeans);

                    }

                    @Override
                    public void onErr(String msg, String status) {
                        goodsContract.settlementFail(msg);

                    }
                }));
    }


}
