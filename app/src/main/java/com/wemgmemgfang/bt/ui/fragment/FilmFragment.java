package com.wemgmemgfang.bt.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseFragment;
import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.presenter.contract.FilmFragmentContract;
import com.wemgmemgfang.bt.presenter.impl.FilmFragmentPresenter;
import com.wemgmemgfang.bt.ui.activity.SearchActivity;
import com.wemgmemgfang.bt.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * sayid ....
 * Created by wengmf on 2018/3/12.
 */

public class FilmFragment extends BaseFragment implements FilmFragmentContract.View, MaterialSearchBar.OnSearchActionListener {

    @Inject
    FilmFragmentPresenter mPresenter;

    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;

    @Override
    public void loadData() {
        setState(Constant.STATE_SUCCESS);
    }

    @Override
    protected void initView(Bundle bundle) {
        searchBar.setOnSearchActionListener(FilmFragment.this);
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

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("Keyword",text.toString());
        getActivity().startActivity(intent);
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode){
            case MaterialSearchBar.BUTTON_NAVIGATION:
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }
}
