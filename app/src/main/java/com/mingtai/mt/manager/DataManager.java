package com.mingtai.mt.manager;

import android.content.Context;

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
import com.mingtai.mt.entity.ProvinceBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.entity.StoreInfoAddressBean;
import com.mingtai.mt.entity.TiaoboHistoryBean;
import com.mingtai.mt.entity.UserBankBean;
import com.mingtai.mt.entity.WxInfo;
import com.mingtai.mt.http.RetrofitHelper;
import com.mingtai.mt.http.RetrofitService;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Observable;


/**
 * Created by LG on 2018/11/27.
 */
public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context){
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<ResultBean<String, Object>> register(HashMap<String,String> hashMap){
        return mRetrofitService.register(hashMap);
    }

    /**
     * 修改支付密码
     *
     * @param mHashMap
     * @return
     */
    public Observable<ResultBean<String, Object>> modifyPsd(HashMap<String, String> mHashMap) {
        return mRetrofitService.modifyPsd(mHashMap);
    }


    /**
     * 点击亲友人获取信息
     */
    public Observable<ResultBean<FriendsBean, Object>> queryName(HashMap<String,String> hashMap){
        return mRetrofitService.queryName(hashMap);
    }


    /**
     * 登录
     */
    public Observable<ResultBean<AccountBean,Object>> login(HashMap<String,String> mHashMap){
        return mRetrofitService.login(mHashMap);
    }


    /**
     * 点击服务人获取信息
     */
    public Observable<ResultBean<String, Object>> getRefereesName(HashMap<String,String> hashMap){
        return mRetrofitService.getRefereesName(hashMap);
    }


    /**
     * 获取收货区域
     */
    public Observable<ResultBean<ArrayList<ProvinceBean>,Object>> getLocalData(HashMap<String,String> mHashMap){
        return mRetrofitService.getLocalData(mHashMap);
    }


    /**
     * 获取银行信息
     */
    public Observable<ResultBean<ArrayList<BankBean>, Object>> getBankInfo(HashMap<String, String> mHashMap) {
        return mRetrofitService.getBankInfo(mHashMap);
    }

    /**
     * 获取全局设置
     */
    public Observable<ResultBean<HomeBean, Object>> getHomeSettingData(HashMap<String, String> mHashMap) {
        return mRetrofitService.getHomeSettingData(mHashMap);
    }


    /**
     * 获取全局设置
     */
    public Observable<ResultBean<HomeMobileBean, Object>> getHomeData(HashMap<String, String> mHashMap) {
        return mRetrofitService.getHomeData(mHashMap);
    }
    /**
     * 获取全局设置
     */
    public Observable<ResultBean<HomeMobileBean<ArrayList<ArticleBean>>, Object>> getHomeDatas(HashMap<String, String> mHashMap) {
        return mRetrofitService.getHomeDatas(mHashMap);
    }

    /**
     * 获取店铺收货地址
     */
    public Observable<ResultBean<StoreInfoAddressBean, Object>> getStoreAddress(HashMap<String, String> mHashMap) {
        return mRetrofitService.getStoreAddress(mHashMap);
    }

    /**
     * 获取个人收货地址
     */
    public Observable<ResultBean<StoreInfoAddressBean, Object>> getPersonalAddress(HashMap<String, String> mHashMap) {
        return mRetrofitService.getPersonalAddress(mHashMap);
    }

    /**
     * 消费下一步
     */
    public Observable<ResultBean<String, Object>> saleNext(HashMap<String, String> mHashMap) {
        return mRetrofitService.saleNext(mHashMap);
    }

    /**
     * 获取商品分类
     */
    public Observable<ResultBean<ArrayList<CategoryBean>, Object>> getCategoryDataSuccess(HashMap<String, String> mHashMap) {
        return mRetrofitService.getCategoryDataSuccess(mHashMap);
    }

    /**
     * 获取商品分类
     */
    public Observable<ResultBean<ArrayList<GoodsBean>, PageBean>> getGoods(HashMap<String, String> mHashMap) {
        return mRetrofitService.getGoods(mHashMap);
    }

    /**
     * 结算
     */
    public Observable<ResultBean<String, Object>> settlement(HashMap<String, String> mHashMap) {
        return mRetrofitService.settlement(mHashMap);
    }

    /**
     * 余额
     */
    public Observable<ResultBean<BalanceBean, Object>> getBalance(HashMap<String, String> mHashMap) {
        return mRetrofitService.getBalance(mHashMap);
    }

    /**
     * 支付订单信息
     */
    public Observable<ResultBean<WxInfo, Object>> sureGoodsOrder(HashMap<String, String> mHashMap) {
        return mRetrofitService.sureGoodsOrder(mHashMap);
    }


    /**
     * 支付订单信息
     */
    public Observable<ResultBean<String, Object>> sureGoodsOrder1(HashMap<String, String> mHashMap) {
        return mRetrofitService.sureGoodsOrder1(mHashMap);
    }


    /**
     * 获取订单详情
     */
    public Observable<ResultBean<OrderDetailInfo, Object>> getOrderDetail(HashMap<String, String> mHashMap) {
        return mRetrofitService.getOrderDetail(mHashMap);
    }

    /**
     * 调拨
     */
    public Observable<ResultBean<String, Object>> unloadPoint(HashMap<String, String> mHashMap) {
        return mRetrofitService.unloadPoint(mHashMap);
    }

    /**
     * 获取调拨历史记录
     */
    public Observable<ResultBean<TiaoboHistoryBean, Object>> getHistoryPoint(HashMap<String, String> mHashMap) {
        return mRetrofitService.getHistoryPoint(mHashMap);
    }


    /**
     * 获取订单列表
     */
    public Observable<ResultBean<ArrayList<OrderBean>, Object>> getSelfOrderList(HashMap<String, String> mHashMap) {
        return mRetrofitService.getSelfOrderList(mHashMap);
    }

    /**
     * 删除订单
     */
    public Observable<ResultBean<String, Object>> exitOrder(HashMap<String, String> mHashMap) {
        return mRetrofitService.exitOrder(mHashMap);
    }

    /**
     * 确认收货
     */
    public Observable<ResultBean<String, Object>> sureReceipt(HashMap<String, String> mHashMap) {
        return mRetrofitService.sureReceipt(mHashMap);
    }


    /**
     * 获取购物金额
     */
    public Observable<ResultBean<ArrayList<BalanceDetailBean>, PageBean>> getAmountPrice(HashMap<String, String> mHashMap) {
        return mRetrofitService.getAmountPrice(mHashMap);
    }


    /**
     * 登出
     *
     * @param mHashMap
     * @return
     */
    public Observable<ResultBean<String, Object>> loginout(HashMap<String, String> mHashMap) {
        return mRetrofitService.loginout(mHashMap);
    }

    /**
     * 获取商品详情
     */
    public Observable<ResultBean<GoodsBean, Object>> getGoodDetailInfo(HashMap<String, String> mHashMap) {
        return mRetrofitService.getGoodDetailInfo(mHashMap);
    }


    /**
     * 增加收货地址
     */
    public Observable<ResultBean> getDeleteAddress(HashMap<String, String> mHashMap) {
        return mRetrofitService.getDeleteAddress(mHashMap);
    }


    /**
     * 　设置默认地址
     */
    public Observable<ResultBean<String, Object>> isDefault(HashMap<String, String> mHashMap) {
        return mRetrofitService.isDefault(mHashMap);
    }

    /**
     * 获取收货地址
     */
    public Observable<ResultBean<ArrayList<AddressBean>, Object>> getConsigneeAddress(HashMap<String, String> mHashMap) {
        return mRetrofitService.getConsigneeAddress(mHashMap);
    }


    /**
     * 增加收货地址
     */
    public Observable<ResultBean<Object,Object>> getSaveAddress(HashMap<String, String> mHashMap) {
        return mRetrofitService.getSaveAddress(mHashMap);
    }


    /**
     * 获取银行卡信息
     */
    public Observable<ResultBean<UserBankBean, Object>> getBankBean(HashMap<String, String> mHashMap) {
        return mRetrofitService.getBankBean(mHashMap);
    }

    /**
     * 获取银行卡信息
     */
    public Observable<ResultBean<Object, Object>> ChangeIsWd(HashMap<String, String> mHashMap) {
        return mRetrofitService.ChangeIsWd(mHashMap);
    }

}
