package com.mingtai.mt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.mingtai.mt.R;
import com.mingtai.mt.adapter.ChooseAddressAdapter;
import com.mingtai.mt.base.BaseActivity;
import com.mingtai.mt.base.ProApplication;
import com.mingtai.mt.contract.ChooseAddressContract;
import com.mingtai.mt.entity.AddressBean;
import com.mingtai.mt.presenter.ChooseAddressPresenter;
import com.mingtai.mt.util.ButtonUtils;
import com.mingtai.mt.util.Eyes;
import com.mingtai.mt.util.MingtaiUtil;
import com.mingtai.mt.util.UiHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by LG on 2020/1/10.
 */
public class ChooseAddressActivity extends BaseActivity implements ChooseAddressContract, ChooseAddressAdapter.OnDeleteAddress, ChooseAddressAdapter.SetOnItemClickListener {

    @BindView(R.id.rv_choose_address)
    RecyclerView mChooseAddressRv;
    @BindView(R.id.ll_empty)
    LinearLayout mEmptyLayout;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    ChooseAddressPresenter chooseAddressPresenter = new ChooseAddressPresenter();
    private ChooseAddressAdapter chooseAddressAdapter;
    private int resultAddAddress = 0x1231;
    private ArrayList<AddressBean> addressBeans;
    private boolean isMe = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_address;
    }

    @Override
    public void initEventAndData() {

        Eyes.setStatusBarWhiteColor(this, getResources().getColor(R.color.white));

        chooseAddressPresenter.onCreate(this, this);

        if (getIntent() != null) {
            if (getIntent().getBundleExtra(MingtaiUtil.TYPEID) != null) {
                if (getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("type") != null && !getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("type").isEmpty()) {
                    if (getIntent().getBundleExtra(MingtaiUtil.TYPEID).getString("type").equals("me")) {
                        isMe = true;
                    }
                }
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        chooseAddressPresenter.getAddress("1", "40", ProApplication.SESSIONID(this));
//        ChooseAddressAdapter chooseAddressAdapter = new ChooseAddressAdapter(this,null,getLayoutInflater());

        mChooseAddressRv.setLayoutManager(linearLayoutManager);
//        mChooseAddressRv.setAdapter(chooseAddressAdapter);
        if (refreshLayout != null) {
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    chooseAddressPresenter.getAddress("1", "40", ProApplication.SESSIONID(ChooseAddressActivity.this));
                }
            });
        }
    }

    @OnClick({R.id.tv_add_address, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_address:

                UiHelper.launcherForResult(this, AddAddressActivity.class, resultAddAddress);

                break;

            case R.id.ll_back:

                finish();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == resultAddAddress) {
                chooseAddressPresenter.getAddress("1", "40", ProApplication.SESSIONID(this));
            }
        }
    }

    @Override
    public void setDataSuccess(ArrayList<AddressBean> addressBeanArrayList) {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        mChooseAddressRv.setVisibility(View.VISIBLE);
        this.addressBeans = addressBeanArrayList;
        if (addressBeanArrayList.size() > 0) {
            mEmptyLayout.setVisibility(View.GONE);
        }
        if (chooseAddressAdapter == null) {
            chooseAddressAdapter = new ChooseAddressAdapter(this, addressBeanArrayList, getLayoutInflater(), this);
            mChooseAddressRv.setAdapter(chooseAddressAdapter);
            chooseAddressAdapter.setOnItemclick(this);
        } else {
            chooseAddressAdapter.setData(addressBeanArrayList);
        }
    }

    @Override
    public void setDataFail(String msg) {
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (msg.contains("查无数据")) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mChooseAddressRv.setVisibility(View.GONE);
        }
    }

    @Override
    public void deleteSuccess() {
        chooseAddressPresenter.getAddress("1", "40", ProApplication.SESSIONID(this));
    }

    @Override
    public void deleteFail(String msg) {

    }

    @Override
    public void isDefaultSuccess(String isDefault) {
        toast("设置默认成功");
        chooseAddressPresenter.getAddress("1", "40", ProApplication.SESSIONID(this));
    }

    @Override
    public void isDefaultFail(String msg) {

    }

    @Override
    public void delete(String userAddressId) {
        chooseAddressPresenter.deletAddress(userAddressId, ProApplication.SESSIONID(this));
    }

    @Override
    public void modify(int position) {
        AddressBean addressBean = addressBeans.get(position);
        if (addressBean.isDefault()) {
            String isDefault = "1";
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("addressBean", addressBean);
        UiHelper.launcherForResultBundle(this, AddAddressActivity.class, resultAddAddress, bundle);

    }

    @Override
    public void isDefault(int addressId) {
        chooseAddressPresenter.isDefault(addressBeans.get(addressId).getAddressID(), ProApplication.SESSIONID(this));
    }

    @Override
    public void onItemClick(int position) {

        if (!ButtonUtils.isFastDoubleClick()) {
            if (!isMe) {
                Intent intent = new Intent();
                AddressBean addressBean = addressBeans.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("address", addressBean);
                intent.putExtra(MingtaiUtil.TYPEID, bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
