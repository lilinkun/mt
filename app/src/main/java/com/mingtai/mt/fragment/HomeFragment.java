package com.mingtai.mt.fragment;

import android.support.v7.widget.RecyclerView;

import com.mingtai.mt.R;
import com.mingtai.mt.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by LG on 2019/12/25.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home)
    RecyclerView rv_home;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initEventAndData() {


    }
}
