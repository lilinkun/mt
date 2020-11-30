package com.mingtai.mt.http;


import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.entity.AddressBean;
import com.mingtai.mt.entity.ArticleBean;
import com.mingtai.mt.entity.BalanceBean;
import com.mingtai.mt.entity.BalanceDetailBean;
import com.mingtai.mt.entity.BankBean;
import com.mingtai.mt.entity.CategoryBean;
import com.mingtai.mt.entity.ChangeIsWdBean;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.HomeMobileBean;
import com.mingtai.mt.entity.OrderBean;
import com.mingtai.mt.entity.OrderDetailBean;
import com.mingtai.mt.entity.OrderDetailInfo;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.entity.PersonalInfoBean;
import com.mingtai.mt.entity.ProvinceBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.entity.TiaoboHistoryBean;
import com.mingtai.mt.entity.UserBankBean;
import com.mingtai.mt.entity.WxInfo;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by LG on 2018/11/7.
 */

public interface RetrofitService {

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> register(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> modifyPsd(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<FriendsBean, Object>> queryName(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> getRefereesName(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<ProvinceBean>,Object>> getLocalData(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<AccountBean,Object>> login(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<BankBean>, Object>> getBankInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<HomeBean, Object>> getHomeSettingData(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<HomeMobileBean, Object>> getHomeData(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<HomeMobileBean<ArrayList<ArticleBean>>, Object>> getHomeDatas(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<StoreInfoAddressBean, Object>> getStoreAddress(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<StoreInfoAddressBean, Object>> getPersonalAddress(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> saleNext(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<CategoryBean>, Object>> getCategoryDataSuccess(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<GoodsBean>, PageBean>> getGoods(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> settlement(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<BalanceBean, Object>> getBalance(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<WxInfo, Object>> sureGoodsOrder(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> sureGoodsOrder1(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<OrderDetailInfo, Object>> getOrderDetail(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> unloadPoint(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<TiaoboHistoryBean, Object>> getHistoryPoint(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<OrderBean>, Object>> getSelfOrderList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> exitOrder(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> sureReceipt(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<BalanceDetailBean>, PageBean>> getAmountPrice(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> loginout(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<GoodsBean, Object>> getGoodDetailInfo(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean> getDeleteAddress(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> isDefault(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<ArrayList<AddressBean>, Object>> getConsigneeAddress(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<Object,Object>> getSaveAddress(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<UserBankBean, Object>> getBankBean(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<Object, Object>> ChangeIsWd(@FieldMap Map<String, String> params);


}
