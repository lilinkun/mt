package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.OrderSureContract;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by LG on 2020/1/7.
 */
public class OrderSurePresenter extends BasePresenter {
    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private OrderSureContract orderListContract;

    @Override
    public void onCreate(Context context, IView view) {
        this.mContext = context;
        manager = new DataManager(mContext);
        orderListContract = (OrderSureContract) view;
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

        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "提交订单中...", true);
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
                    public void onResponse(String goodsBeans, String status, ResultBean<String, Object> page) {
                        orderListContract.getTlementSuccess(goodsBeans);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderListContract.getTlementFail(msg);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
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
    public void settlement(String GoodsId,String UserLevel,String OrderAmount,String ShippingFree,String goodsNum,String Consignee,String Mobile,String SingleCenterName,
                           String deliveryMethod,String prov,String city,String area,String OrderType,String Address,String UserName,String Post,
                           String PostScript,String Integral,String SessionId){
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "提交订单中...", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "UserBase");
        params.put("fun", "UserBaseUpgrade");
        params.put("GoodsId", GoodsId);
        params.put("UserLevel", UserLevel);
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
                    public void onResponse(String goodsBeans, String status, ResultBean<String, Object> page) {
                        orderListContract.getTlementSuccess(goodsBeans);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        orderListContract.getTlementFail(msg);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));
    }

}
