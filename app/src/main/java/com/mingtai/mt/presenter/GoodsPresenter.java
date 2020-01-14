package com.mingtai.mt.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.mingtai.mt.base.BasePresenter;
import com.mingtai.mt.contract.GoodsContract;
import com.mingtai.mt.entity.CategoryBean;
import com.mingtai.mt.entity.GoodsBean;
import com.mingtai.mt.entity.PageBean;
import com.mingtai.mt.entity.ResultBean;
import com.mingtai.mt.http.callback.HttpResultCallBack;
import com.mingtai.mt.manager.DataManager;
import com.mingtai.mt.mvp.IView;
import com.mingtai.mt.util.MingtaiUtil;

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
                    public void onResponse(ArrayList<CategoryBean> categoryBeans, String status, ResultBean<ArrayList<CategoryBean>, Object> page) {
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
        final ProgressDialog progressDialog = ProgressDialog.show(mContext, "请稍等...", "", true);
        HashMap<String, String> params = new HashMap<>();
        params.put("cls", "Goods");
        if (GoodsType.equals(MingtaiUtil.UPDATEINT+"")){
            params.put("fun", "GoodsListVip");
        }else {
            params.put("fun", "GoodsRecurringListVip");
        }
        params.put("PageIndex", PageIndex);
        params.put("PageCount", PageCount);
        params.put("CategoryId", CategoryId);
        params.put("GoodsType", GoodsType);
        params.put("UserLevel", UserLevel);
        params.put("SessionId", SessionId);
        mCompositeSubscription.add(manager.getGoods(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpResultCallBack<ArrayList<GoodsBean>, PageBean>() {
                    @Override
                    public void onResponse(ArrayList<GoodsBean> goodsBeans, String status, ResultBean<ArrayList<GoodsBean>, PageBean> page) {
                        goodsContract.getGoodsDataSuccess(goodsBeans,page.getPage());

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onErr(String msg, String status) {
                        goodsContract.getGoodsDataFail(msg);

                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }));

    }





}