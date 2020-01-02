package com.mingtai.mt.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mingtai.mt.R;
import com.mingtai.mt.activity.RegisterActivity;
import com.mingtai.mt.activity.SaleActivity;
import com.mingtai.mt.adapter.DeclarationAdapter;
import com.mingtai.mt.base.BaseFragment;
import com.mingtai.mt.ui.SpaceItemDecoration;
import com.mingtai.mt.util.UiHelper;

import butterknife.BindView;

/**
 * Created by LG on 2019/12/25.
 */
public class DeclarationFragment extends BaseFragment implements DeclarationAdapter.OnItemClickListener {

    @BindView(R.id.rv_declaration)
    RecyclerView rv_declaration;

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
        rv_declaration.addItemDecoration(new SpaceItemDecoration(spanCount, spacing, 0));

        rv_declaration.setLayoutManager(gridLayoutManager);
        rv_declaration.setAdapter(declarationAdapter);

        declarationAdapter.setItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:

                UiHelper.launcher(getActivity(), RegisterActivity.class);

                break;

            case 1:

                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                UiHelper.launcherBundle(getActivity(), SaleActivity.class,bundle);

                break;

            case 2:

                Bundle bundle1 = new Bundle();
                bundle1.putInt("type",2);
                UiHelper.launcherBundle(getActivity(), SaleActivity.class,bundle1);

                break;

            case 3:

                Bundle bundle2 = new Bundle();
                bundle2.putInt("type",3);
                UiHelper.launcherBundle(getActivity(), SaleActivity.class,bundle2);

                break;
        }
    }

   /* @OnClick({R.id.ll_register,R.id.ll_sale,R.id.ll_update,R.id.ll_tiaobo})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_register:

                UiHelper.launcher(getActivity(), RegisterActivity.class);

                break;

            case R.id.ll_sale:

                Bundle bundle = new Bundle();
                bundle.putInt("type",1);
                UiHelper.launcherBundle(getActivity(), SaleActivity.class,bundle);

                break;

            case R.id.ll_update:

                Bundle bundle1 = new Bundle();
                bundle1.putInt("type",2);
                UiHelper.launcherBundle(getActivity(), SaleActivity.class,bundle1);
                break;

            case R.id.ll_tiaobo:

                Bundle bundle2 = new Bundle();
                bundle2.putInt("type",3);
                UiHelper.launcherBundle(getActivity(), SaleActivity.class,bundle2);

                break;

        }
    }*/
}
