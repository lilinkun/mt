package com.mingtai.mt.http;


import com.mingtai.mt.entity.AccountBean;
import com.mingtai.mt.entity.BankBean;
import com.mingtai.mt.entity.CategoryBean;
import com.mingtai.mt.entity.FriendsBean;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.entity.PersonalInfoBean;
import com.mingtai.mt.entity.ProvinceBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;

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
    Observable<ResultBean<HomeBean, Object>> getHomeData(@FieldMap Map<String, String> params);

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
    Observable<ResultBean<ArrayList<GoodsBean>, Object>> getGoods(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("Api/")
    Observable<ResultBean<String, Object>> settlement(@FieldMap Map<String, String> params);

}
