package com.mingtai.mt.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.HomeAdapter;
import com.mingtai.mt.base.BaseFragment;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.HomeContract;
import com.mingtai.mt.entity.ArticleBean;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.entity.HomeMobileBean;
import com.mingtai.mt.presenter.HomePresenter;
import com.mingtai.mt.ui.CustomBannerView;
import com.mingtai.mt.util.UToast;
import com.youth.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by LG on 2019/12/25.
 */
public class HomeFragment extends BaseFragment implements HomeContract {

    @BindView(R.id.rv_home)
    RecyclerView rv_home;
    @BindView(R.id.bannerView)
    Banner banner;

    private HomePresenter homePresenter = new HomePresenter();
    private ArrayList<ArticleBean> articleBeans ;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initEventAndData() {

        homePresenter.onCreate(getActivity(),this);
        homePresenter.getSettingParameter(ProApplication.SESSIONID(getActivity()));

        homePresenter.getHomeData(ProApplication.SESSIONID(getActivity()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_home.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void getDataSuccess(HomeBean msg) {
        ProApplication.mHomeBean = msg;
        ProApplication.BANNERIMG = msg.getImgUrl();
    }

    @Override
    public void getDataFail(String msg) {
        UToast.show(getActivity(),msg);
    }

    @Override
    public void getHomeSuccess(HomeMobileBean homeMobileBean) {
        articleBeans = homeMobileBean.getNews();

        HomeAdapter homeAdapter = new HomeAdapter(getActivity(),articleBeans);
        rv_home.setAdapter(homeAdapter);


        CustomBannerView.startBanner(homeMobileBean.getFlash(), banner, getActivity(), true);
    }

    @Override
    public void getHomeFail(String msg) {
        UToast.show(getActivity(),msg);
    }
}
