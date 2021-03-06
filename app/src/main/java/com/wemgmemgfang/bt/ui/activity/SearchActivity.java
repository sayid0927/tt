package com.wemgmemgfang.bt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.bean.SearchBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.presenter.contract.SearchContract;
import com.wemgmemgfang.bt.presenter.impl.SearchPresenter;
import com.wemgmemgfang.bt.ui.adapter.Search_Adapter;
import com.wemgmemgfang.bt.utils.ToastUtils;
import com.wemgmemgfang.bt.utils.UmengUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements SearchContract.View {

    @Inject
    SearchPresenter mPresenter;
    @BindView(R.id.search_rv)
    RecyclerView searchRv;
    @BindView(R.id.llExit)
    LinearLayout llExit;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private String keyword;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
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
          UmengUtil.onEvent("SearchActivity");
        setSwipeBackEnable(true);
        keyword = getIntent().getStringExtra("Keyword");
        mPresenter.Fetch_Search_Info(keyword);
    }

    @Override
    public void showError(String message) {
     UmengUtil.onEvent("showError_SearchActivity");
    }

    @Override
    public void Fetch_Search_Info_Success(List<SearchBean> searchBeanList) {
        if (searchBeanList.size() != 0) {
            tvTitle.setText(R.string.SearchRe);
            Search_Adapter adapter = new Search_Adapter(searchBeanList, SearchActivity.this);
            searchRv.setAdapter(adapter);
            searchRv.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

            adapter.setOnPlayItemClickListener(new Search_Adapter.OnPlayItemClickListener() {
                @Override
                public void OnPlayItemClickListener(SearchBean item) {
                    Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
                    intent.putExtra("HrefUrl", item.getHref());
                    intent.putExtra("imgUrl", item.getImgUrl());
                    intent.putExtra("Title", item.getH4());
                    startActivityIn(intent,SearchActivity.this);
                }
            });
        } else {
            ToastUtils.showLongToast(getString(R.string.Search_No));
        }
    }

    @OnClick(R.id.llExit)
    public void onViewClicked() {
        this.finish();
    }


}
