package com.mingtai.mt.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mingtai.mt.R;
import com.mingtai.mt.activity.WebviewActivity;
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
import com.mingtai.mt.util.UiHelper;
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
    private ArrayList<ArticleBean> notice;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initEventAndData() {

        homePresenter.onCreate(getActivity(),this);

        homePresenter.getHomeData(ProApplication.SESSIONID(getActivity()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv_home.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void getHomeSuccess(final HomeMobileBean homeMobileBean) {
        articleBeans = homeMobileBean.getNews();

        if (ProApplication.isLogin) {
            notice = (ArrayList<ArticleBean>)homeMobileBean.getNewsOpen();
            if (notice.get(0).getSummary().trim().length() > 0) {
                new AlertDialog.Builder(getActivity()).setTitle(notice.get(0).getTitle())
                        .setMessage(notice.get(0).getSummary()).setNegativeButton("详情", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (notice.get(0).getLink() != null && notice.get(0).getLink().trim().length() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("CategoryName", notice.get(0).getCategoryName());
                            bundle.putString("URL", notice.get(0).getLink());
                            bundle.putString("type", "home");
                            UiHelper.launcherBundle(getActivity(), WebviewActivity.class, bundle);
                        }
                        dialog.cancel();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        }

        ProApplication.isLogin = false;

        HomeAdapter homeAdapter = new HomeAdapter(getActivity(),articleBeans);
        rv_home.setAdapter(homeAdapter);
        homeAdapter.setItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (articleBeans.get(position).getLink() != null && articleBeans.get(position).getLink().trim().length() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("CategoryName", articleBeans.get(position).getCategoryName());
                    bundle.putString("URL", articleBeans.get(position).getLink());
                    bundle.putString("type","home");
                    UiHelper.launcherBundle(getActivity(), WebviewActivity.class, bundle);
                }
            }
        });

        CustomBannerView.startBanner(homeMobileBean.getFlash(), banner, getActivity(), true);
    }

    @Override
    public void getHomeFail(String msg) {
        UToast.show(getActivity(),msg);
    }
}
