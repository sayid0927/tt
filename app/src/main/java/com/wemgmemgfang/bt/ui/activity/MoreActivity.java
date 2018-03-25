package com.wemgmemgfang.bt.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.bean.MoreInfoBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.database.UserInfoDao;
import com.wemgmemgfang.bt.entity.UserInfo;
import com.wemgmemgfang.bt.presenter.contract.MoreActivityContract;
import com.wemgmemgfang.bt.presenter.impl.MoreActivityPresenter;
import com.wemgmemgfang.bt.ui.adapter.More_Adapter;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.view.MyLoadMoreView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreActivity extends BaseActivity implements MoreActivityContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    MoreActivityPresenter mPresenter;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private String imgUrl, HrefUrl,Title;

    @BindView(R.id.llExit)
    LinearLayout llExit;

    @BindView(R.id.rv_info)
    RecyclerView rvInfo;
    @BindView(R.id.srl_android)
    SwipeRefreshLayout srlAndroid;


    private boolean isRefresh = false;
    private More_Adapter more_adapter;

    private int index = 1;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    public void attachView() {
        mPresenter.attachView(this);
    }

    @Override
    public void detachView() {
        mPresenter.detachView();
    }

    @Override
    public void initView() {

//
//        UserInfoDao userInfoDao = GreenDaoUtil.getDaoSession().getUserInfoDao();
//        List<UserInfo> user = userInfoDao.queryBuilder().where(UserInfoDao.Properties.UserName.eq("wmf")).list();

        HrefUrl = "https://www.80s.tt" + getIntent().getStringExtra("HrefUrl");

        Title = getIntent().getStringExtra("Title");

        tvTitle.setText(Title);

        mPresenter.Fetch_MoreTypeInfo(HrefUrl);

        more_adapter = new More_Adapter(null, MoreActivity.this);
        rvInfo.setAdapter(more_adapter);
        rvInfo.setLayoutManager(new GridLayoutManager(MoreActivity.this, 2));

        more_adapter.setOnLoadMoreListener(MoreActivity.this, rvInfo);
        more_adapter.setLoadMoreView(new MyLoadMoreView());
        srlAndroid.setOnRefreshListener(MoreActivity.this);

        more_adapter.setOnPlayItemClickListener(new More_Adapter.OnPlayItemClickListener() {
            @Override
            public void OnPlayItemClickListener(MoreInfoBean.MoreVideoInfoBean item) {
                Intent intent = new Intent(MoreActivity.this, DetailsActivity.class);
                intent.putExtra("HrefUrl", item.getHerf());
                intent.putExtra("imgUrl", item.getImgUrl());
                intent.putExtra("Title", item.getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void Fetch_MoreTypeInfo_Success(MoreInfoBean data) {
        if (isRefresh) {
            srlAndroid.setRefreshing(false);
            more_adapter.setEnableLoadMore(true);
            isRefresh = false;
            more_adapter.setNewData(data.getMoreVideoInfoBeans());
        } else {
            srlAndroid.setEnabled(true);
            more_adapter.addData(data.getMoreVideoInfoBeans());
            more_adapter.loadMoreComplete();
        }
    }

    @OnClick({R.id.llExit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llExit:
                this.finish();
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (index < 46) {
            index++;
            mPresenter.Fetch_MoreTypeInfo(HrefUrl + "?page=" + index);
            srlAndroid.setEnabled(false);
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        more_adapter.setEnableLoadMore(false);
        mPresenter.Fetch_MoreTypeInfo(HrefUrl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
