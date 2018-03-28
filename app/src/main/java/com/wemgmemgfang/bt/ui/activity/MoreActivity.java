package com.wemgmemgfang.bt.ui.activity;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.bean.MoreInfoBean;
import com.wemgmemgfang.bt.bean.SearchDialogBean;
import com.wemgmemgfang.bt.bean.SearchHorizontalBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.presenter.contract.MoreActivityContract;
import com.wemgmemgfang.bt.presenter.impl.MoreActivityPresenter;
import com.wemgmemgfang.bt.ui.adapter.More_Adapter;
import com.wemgmemgfang.bt.ui.adapter.More_Search_Adapter;
import com.wemgmemgfang.bt.view.SearchDialog;
import com.wemgmemgfang.bt.view.MyLoadMoreView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MoreActivity extends BaseActivity implements MoreActivityContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    MoreActivityPresenter mPresenter;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.llExit)
    LinearLayout llExit;
    @BindView(R.id.rv_info)
    RecyclerView rvInfo;
    @BindView(R.id.srl_android)
    SwipeRefreshLayout srlAndroid;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.llRight)
    LinearLayout llRight;
    @BindView(R.id.rl_search)
    RecyclerView rlSearch;

    private String imgUrl, HrefUrl, Title;
    private boolean isRefresh = false;
    private More_Adapter more_adapter;
    private int index = 1;
    private List<SearchDialogBean> searchDialogBeanList = new ArrayList<>();

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
    public void Fetch_MoreTypeInfo_Success(final MoreInfoBean data) {

        final List<SearchHorizontalBean> searchHorizontalBeanList = new ArrayList<>();
        String tmp = "aa";
        for (int i = 0; i < data.getMoreTypeBeans().size(); i++) {
            SearchHorizontalBean searchHorizontalBean = new SearchHorizontalBean();
            String dd = data.getMoreTypeBeans().get(i).getType();
            if (dd != null && !tmp.equals(dd)) {
                searchHorizontalBean.setStart(true);
                searchHorizontalBean.setType(data.getMoreTypeBeans().get(i).getType());
                searchHorizontalBean.setName(data.getMoreTypeBeans().get(i).getTypeName());
                searchHorizontalBeanList.add(searchHorizontalBean);
                tmp = data.getMoreTypeBeans().get(i).getType();
            }
        }
        if (searchDialogBeanList.size() != 0) {
            for (int n = 0; n < searchHorizontalBeanList.size(); n++) {
                for (int k = 0; k < searchDialogBeanList.size(); k++) {
                    if (searchHorizontalBeanList.get(n).getType().equals(searchDialogBeanList.get(k).getType())) {
                        searchHorizontalBeanList.get(n).setName(searchDialogBeanList.get(k).getTypeName());
                        break;
                    }
                }
            }
        }
        final More_Search_Adapter moreSearchAdapter = new More_Search_Adapter(searchHorizontalBeanList, MoreActivity.this);
        rlSearch.setAdapter(moreSearchAdapter);
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlSearch.setLayoutManager(ms);
        moreSearchAdapter.setOnPlayItemClickListener(new More_Search_Adapter.OnPlayItemClickListener() {
            @Override
            public void OnPlayItemClickListener(SearchHorizontalBean item) {

                final SearchDialog dialog = new SearchDialog(MoreActivity.this, item, data.getMoreTypeBeans());
                dialog.OnMoreDialogItemClick(new SearchDialog.OnMoreDialogItemClick() {
                    @Override
                    public void OnMoreDialogItemClick(SearchDialogBean item) {
                        isRefresh = true;
                        HrefUrl = "https://www.80s.tt" + item.getHref();
                        if (searchDialogBeanList.size() != 0) {
                            for (int i = 0; i < searchDialogBeanList.size(); i++) {
                                if (item.getType().equals(searchDialogBeanList.get(i).getType())) {
                                    searchDialogBeanList.get(i).setTypeName(item.getTypeName());
                                }else
                                    searchDialogBeanList.add(item);
                            }
                        } else
                            searchDialogBeanList.add(item);
                        mPresenter.Fetch_MoreTypeInfo(HrefUrl);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

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

    @OnClick({R.id.llExit, R.id.llRight})
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
}
