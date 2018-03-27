package com.wemgmemgfang.bt.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseFragment;
import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.bean.DownRaningBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.presenter.contract.DownRankingContract;
import com.wemgmemgfang.bt.presenter.impl.DownRankingPresenter;
import com.wemgmemgfang.bt.ui.activity.ViewBoxActivity;
import com.wemgmemgfang.bt.ui.adapter.DownRanking_Adapter;
import com.wemgmemgfang.bt.view.MyLoadMoreView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * sayid ....
 * Created by wengmf on 2018/3/12.
 */

public class DownRankingFragment extends BaseFragment implements DownRankingContract.View,BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {


    @Inject
    DownRankingPresenter mPresenter;

    @BindView(R.id.title_list)
    RecyclerView rvList;
    @BindView(R.id.srl_android)
    SwipeRefreshLayout srlAndroid;

    private DownRanking_Adapter mAdapter;
    private List<DownRaningBean> dataBean;
    private static String DownRankingUrl = "http://www.zei8.me/movie/lunli/";
    private boolean isRefresh = false;
    private int index = 1;

    @Override
    public void loadData() {
        mPresenter.Fetch_DownRankingInfo(DownRankingUrl);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_downranking;
    }

    @Override
    protected void initView(Bundle bundle) {

        mAdapter = new DownRanking_Adapter(dataBean, getSupportActivity());
        mAdapter.setOnLoadMoreListener(DownRankingFragment.this, rvList);
        mAdapter.setLoadMoreView(new MyLoadMoreView());
        srlAndroid.setOnRefreshListener(this);
        rvList.setLayoutManager(new GridLayoutManager(getSupportActivity(), 1));
        rvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new DownRanking_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(DownRaningBean item) {
                Intent intent = new Intent(getActivity(), ViewBoxActivity.class);
                intent.putExtra("HrefUrl",item.getHref());
                intent.putExtra("ImgUrl",item.getImgUrl());
                getActivity().startActivity(intent);

            }
        });
    }

    @Override
    public void attachView() {
        mPresenter.attachView(this);
    }



    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        mAdapter.setEnableLoadMore(false);
        mPresenter.Fetch_DownRankingInfo(DownRankingUrl);

    }


    @Override
    public void showError(String message) {

    }

    @Override
    public void Fetch_DownRankingInfo_Success(List<DownRaningBean> dataBean) {
        if (isRefresh) {
            srlAndroid.setRefreshing(false);
            mAdapter.setEnableLoadMore(true);
            isRefresh = false;
            mAdapter.setNewData(dataBean);
        } else {
            setState(Constant.STATE_SUCCESS);
            srlAndroid.setEnabled(true);
            mAdapter.addData(dataBean);
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (index < 46) {
            index++;
            mPresenter.Fetch_DownRankingInfo(DownRankingUrl + "index_" +index+ ".html");
            srlAndroid.setEnabled(false);
        }
    }
}
