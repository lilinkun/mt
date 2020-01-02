package com.mingtai.mt.fragment;

import android.support.v7.widget.RecyclerView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseFragment;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.HomeContract;
import com.mingtai.mt.entity.HomeBean;
import com.mingtai.mt.presenter.HomePresenter;

import butterknife.BindView;

/**
 * Created by LG on 2019/12/25.
 */
public class HomeFragment extends BaseFragment implements HomeContract {

    @BindView(R.id.rv_home)
    RecyclerView rv_home;

    private HomePresenter homePresenter = new HomePresenter();

    @Override
    public int getlayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initEventAndData() {

        homePresenter.onCreate(getActivity(),this);
        homePresenter.getSettingParameter(ProApplication.SESSIONID(getActivity()));

        homePresenter.getHomeData(ProApplication.SESSIONID(getActivity()));

    }

    @Override
    public void getDataSuccess(HomeBean msg) {

    }

    @Override
    public void getDataFail(String msg) {

    }
}
