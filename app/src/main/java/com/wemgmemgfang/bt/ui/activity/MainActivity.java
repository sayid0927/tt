package com.wemgmemgfang.bt.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.pgyersdk.update.PgyUpdateManager;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.base.BaseFragmentPageAdapter;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.presenter.contract.MainContract;
import com.wemgmemgfang.bt.presenter.impl.MainActivityPresenter;
import com.wemgmemgfang.bt.ui.fragment.DownRankingFragment;
import com.wemgmemgfang.bt.ui.fragment.FilmFragment;
import com.wemgmemgfang.bt.ui.fragment.HomeFragment;
import com.wemgmemgfang.bt.ui.fragment.MeFragment;
import com.wemgmemgfang.bt.utils.UmengUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject
    MainActivityPresenter mPresenter;


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.vp)
    ViewPager vp;

    private BaseFragmentPageAdapter myAdapter;

    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    public static int FileSize;
    public static String Apk_Name;
    public static MainActivity mainActivity;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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

        UmengUtil.onEvent("MainActivity");


        mTitleList.add("推荐");
        mTitleList.add("首页");
        mTitleList.add("图片");
        mTitleList.add("我");

        HomeFragment homeFragment = new HomeFragment();
        DownRankingFragment downRankingFragment = new DownRankingFragment();
        FilmFragment filmFragment = new FilmFragment();
        MeFragment meFragment = new MeFragment();

        mFragments.add(homeFragment);
        mFragments.add(downRankingFragment);

        mFragments.add(filmFragment);
        mFragments.add(meFragment);

        myAdapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        vp.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(vp);

        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(this);
        mainActivity = this;

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void killAll() {
        super.killAll();
    }


}
