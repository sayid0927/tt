package com.wemgmemgfang.bt.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseFragment;
import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.presenter.contract.FilmFragmentContract;
import com.wemgmemgfang.bt.presenter.impl.FilmFragmentPresenter;
import com.wemgmemgfang.bt.ui.activity.MoreActivity;
import com.wemgmemgfang.bt.ui.activity.SearchActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * sayid ....
 * Created by wengmf on 2018/3/12.
 */

public class FilmFragment extends BaseFragment implements FilmFragmentContract.View, MaterialSearchBar.OnSearchActionListener {

    @Inject
    FilmFragmentPresenter mPresenter;

    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;
    @BindView(R.id.but_video)
    Button butVideo;
    @BindView(R.id.but_dalu)
    Button butDalu;
    @BindView(R.id.but_gantai)
    Button butGantai;
    @BindView(R.id.but_rihan)
    Button butRihan;
    @BindView(R.id.but_aomei)
    Button butAomei;
    @BindView(R.id.but_yyi)
    Button butYyi;
    @BindView(R.id.but_dongman)
    Button butDongman;


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
        intent.putExtra("Keyword", text.toString());
        getActivity().startActivity(intent);
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }

    @OnClick({R.id.but_video, R.id.but_dalu, R.id.but_gantai, R.id.but_rihan, R.id.but_aomei, R.id.but_yyi, R.id.but_dongman})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but_video:
                Intent intent = new Intent(getActivity(), MoreActivity.class);
                intent.putExtra("HrefUrl","/movie/1-0-0-0-0-0");
                intent.putExtra("Title","电影");
                getActivity().startActivity(intent);
                break;
            case R.id.but_dalu:
                Intent i = new Intent(getActivity(), MoreActivity.class);
                i.putExtra("HrefUrl","/movie/9-0-0-0-0-0");
                i.putExtra("Title","大陆剧");
                getActivity().startActivity(i);
                break;
            case R.id.but_gantai:
                Intent g = new Intent(getActivity(), MoreActivity.class);
                g.putExtra("HrefUrl","/movie/10-0-0-0-0-0");
                g.putExtra("Title","港台剧");
                getActivity().startActivity(g);
                break;
            case R.id.but_rihan:
                Intent r = new Intent(getActivity(), MoreActivity.class);
                r.putExtra("HrefUrl","/movie/11-0-0-0-0-0");
                r.putExtra("Title","日韩剧");
                getActivity().startActivity(r);
                break;
            case R.id.but_aomei:
                Intent o = new Intent(getActivity(), MoreActivity.class);
                o.putExtra("HrefUrl","/movie/12-0-0-0-0-0");
                o.putExtra("Title","欧美剧");
                getActivity().startActivity(o);
                break;
            case R.id.but_yyi:
                Intent y = new Intent(getActivity(), MoreActivity.class);
                y.putExtra("HrefUrl","/movie/4-0-0-0-0-0");
                y.putExtra("Title","综艺");
                getActivity().startActivity(y);
                break;
            case R.id.but_dongman:
                Intent d = new Intent(getActivity(), MoreActivity.class);
                d.putExtra("HrefUrl","/movie/14-0-0-0-0-0");
                d.putExtra("Title","动漫");
                getActivity().startActivity(d);
                break;
        }
    }
}
