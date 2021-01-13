package com.mingtai.mt.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mingtai.mt.R;
import com.mingtai.mt.activity.RegisterActivity;
import com.mingtai.mt.activity.SaleActivity;
import com.mingtai.mt.adapter.DeclarationAdapter;
import com.mingtai.mt.base.BaseFragment;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.ui.SimpleDividerItemDecoration;
import com.mingtai.mt.ui.SpaceItemDecoration;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import butterknife.BindView;

/**
 * Created by LG on 2019/12/25.
 */
public class DeclarationFragment extends BaseFragment implements DeclarationAdapter.OnItemClickListener {

    @BindView(R.id.rv_declaration)
    RecyclerView rv_declaration;

    private boolean isRegister = false;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_declaration;
    }

    @Override
    public void initEventAndData() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);

        DeclarationAdapter declarationAdapter = new DeclarationAdapter(getActivity());

        int spanCount = 1; // 2 columns
        int spacing = 1; // 50px

        boolean includeEdge = false;
        rv_declaration.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        rv_declaration.setLayoutManager(gridLayoutManager);
        rv_declaration.setAdapter(declarationAdapter);

        declarationAdapter.setItemClickListener(this);


        if (ProApplication.mAccountBean != null) {
            if (ProApplication.mAccountBean.getUserLevel() == 0) {
                rv_declaration.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onItemClick(int position) {

        if (ProApplication.mAccountBean != null) {

            if (ProApplication.mAccountBean.getIsSingleCenter() > 0 || ProApplication.mAccountBean.isAgreement()) {

                isRegister = true;

            } else {

                isRegister = false;

            }
        }
        switch (position) {
            case 0:

                if (isRegister) {
                    UiHelper.launcher(getActivity(), RegisterActivity.class);
                }else {
                    dumpActivity(MingtaiUtil.SALEINT);
                }

                break;

            case 1:

                if (isRegister) {
                    dumpActivity(MingtaiUtil.UPDATEINT);
                }else {
                    dumpActivity(MingtaiUtil.TIAOBOINT);
                }

                break;

            case 2:

                if (isRegister) {
                    dumpActivity(MingtaiUtil.SALEINT);
                }

                break;

            case 3:

                dumpActivity(MingtaiUtil.TIAOBOINT);

                break;

        }
    }

    private void dumpActivity(int type){

        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        UiHelper.launcherBundle(getActivity(), SaleActivity.class, bundle);
    }

}
