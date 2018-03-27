package com.wemgmemgfang.bt.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseFragment;
import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.presenter.contract.FilmFragmentContract;
import com.wemgmemgfang.bt.presenter.impl.FilmFragmentPresenter;
import com.wemgmemgfang.bt.ui.activity.SearchActivity;
import com.wemgmemgfang.bt.ui.activity.ViewBoxActivity;
import com.wemgmemgfang.bt.view.searchview.ICallBack;
import com.wemgmemgfang.bt.view.searchview.SearchView;
import com.wemgmemgfang.bt.view.searchview.bCallBack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * sayid ....
 * Created by wengmf on 2018/3/12.
 */

public class FilmFragment extends BaseFragment implements FilmFragmentContract.View {

    @Inject
    FilmFragmentPresenter mPresenter;
    @BindView(R.id.search_view)
    SearchView searchView;


    @Override
    public void loadData() {
        setState(Constant.STATE_SUCCESS);
    }

    @Override
    protected void initView(Bundle bundle) {
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("Keyword",string);
                getActivity().startActivity(intent);
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {

            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_film;
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
    public void showError(String message) {

    }

}
