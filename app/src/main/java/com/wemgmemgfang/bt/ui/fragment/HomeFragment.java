package com.wemgmemgfang.bt.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseFragment;
import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.bean.HomeInfoBean;
import com.wemgmemgfang.bt.bean.HomeSectionBean;
import com.wemgmemgfang.bt.bean.HomeTitleItem;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.presenter.contract.HomeContract;
import com.wemgmemgfang.bt.presenter.impl.HomeFragmentPresenter;
import com.wemgmemgfang.bt.ui.activity.DetailsActivity;
import com.wemgmemgfang.bt.ui.activity.MoreActivity;
import com.wemgmemgfang.bt.ui.adapter.Home_Title_Adapter;
import com.wemgmemgfang.bt.ui.fragment.homeChildFragment.HotsFilmFragment;
import com.wemgmemgfang.bt.ui.fragment.homeChildFragment.HotsMangaFragment;
import com.wemgmemgfang.bt.ui.fragment.homeChildFragment.HotsTeleplayFragment;
import com.wemgmemgfang.bt.ui.fragment.homeChildFragment.HotsVarietyFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * sayid ....
 * Created by wengmf on 2018/3/12.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    @Inject
    HomeFragmentPresenter mPresenter;

    @BindView(R.id.tl_4)
    SegmentTabLayout tabLayout;

    @BindView(R.id.vp_2)
    ViewPager vp;

    @BindView(R.id.rv_info)
    RecyclerView rvInfo;

    private Home_Title_Adapter mAdapter;

    private static final String sttBaseUrl = "https://www.80s.tt/";

    private String[] mTitles = {"电影", "电视剧", "综艺", "动漫"};

    private ArrayList<Fragment> mFragments = new ArrayList<>();


    @Override
    protected void initView(Bundle bundle) {

    }

    @Override
    public void loadData() {
        setState(Constant.STATE_SUCCESS);
        mPresenter.Fetch_80sHomeInfo(sttBaseUrl);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_hotfilm;
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
//        showDialog(message);
        mPresenter.Fetch_80sHomeInfo(sttBaseUrl);
    }

    @Override
    public void Fetch_80sHomeInfo_Success(HomeInfoBean data) {
        initHotsView(data);
        initadapterData(data);
    }

    private void initadapterData(HomeInfoBean data) {

        ArrayList<MultiItemEntity> res = new ArrayList<>();

        for (int i = 0; i < data.getColTitleBean().size(); i++) {

            HomeTitleItem homeTitleItem = new HomeTitleItem();
            homeTitleItem.setCountPop(data.getColTitleBean().get(i).getCountPop());
            homeTitleItem.setHrefUrl(data.getColTitleBean().get(i).getHrefUrl());
            homeTitleItem.setTitle(data.getColTitleBean().get(i).getTitle());

            for (int j = 0; j < data.getSectionBeans().size(); j++) {
                String title=  homeTitleItem.getTitle();
                String tt =  data.getSectionBeans().get(j).getType();
                if (title.contains(tt) ) {
                    HomeSectionBean homeSectionBean = new HomeSectionBean();
                    homeSectionBean.setEm(data.getSectionBeans().get(j).getEm());
                    homeSectionBean.setHerf(data.getSectionBeans().get(j).getHerf());
                    homeSectionBean.setImgUrl(data.getSectionBeans().get(j).getImgUrl());
                    homeSectionBean.setLanguage(data.getSectionBeans().get(j).getLanguage());
                    homeSectionBean.setScore(data.getSectionBeans().get(j).getScore());
                    homeSectionBean.setTitle(data.getSectionBeans().get(j).getTitle());
                    homeSectionBean.setType(data.getSectionBeans().get(j).getType());
                    homeTitleItem.addSubItem(homeSectionBean);
                }
            }
            res.add(homeTitleItem);
        }
        mAdapter = new Home_Title_Adapter(res, getActivity());
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) == Home_Title_Adapter.TYPE_LEVEL_1 ? 1 : manager.getSpanCount();
            }
        });
        rvInfo.setAdapter(mAdapter);
        rvInfo.setLayoutManager(manager);
        mAdapter.expandAll();
        mAdapter.setOnTitleItemClickListener(new Home_Title_Adapter.OnTitleItemClickListener() {
            @Override
            public void OnTitleItemClickListener(HomeTitleItem item) {

                Intent intent = new Intent(getActivity(), MoreActivity.class);
                intent.putExtra("HrefUrl",item.getHrefUrl());
                intent.putExtra("Title",item.getTitle());
                getActivity().startActivity(intent);

            }
        });
        mAdapter.setOnSectionItemClickListener(new Home_Title_Adapter.OnSectionItemClickListener() {
            @Override
            public void OnSectionItemClickListener(HomeSectionBean item) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("HrefUrl",item.getHerf());
                intent.putExtra("imgUrl",item.getImgUrl());
                intent.putExtra("Title",item.getTitle());
                getActivity().startActivity(intent);
            }
        });
    }

    private void initHotsView(HomeInfoBean data) {
        List<HomeInfoBean.HotsInfoBean> filmData = new ArrayList<>();
        List<HomeInfoBean.HotsInfoBean> teleplayData = new ArrayList<>();
        List<HomeInfoBean.HotsInfoBean> mangaData = new ArrayList<>();
        List<HomeInfoBean.HotsInfoBean> varietyData = new ArrayList<>();

        for (HomeInfoBean.HotsInfoBean d : data.getHotsInfoBeans()) {
            switch (d.getType()) {
                case "电影":
                    filmData.add(d);
                    break;
                case "电视剧":
                    teleplayData.add(d);
                    break;
                case "综艺":
                    varietyData.add(d);
                    break;
                case "动漫":
                    mangaData.add(d);
                    break;
            }
        }
        mFragments.add(HotsFilmFragment.getInstance(filmData));
        mFragments.add(HotsTeleplayFragment.getInstance(teleplayData));
        mFragments.add(HotsVarietyFragment.getInstance(varietyData));
        mFragments.add(HotsMangaFragment.getInstance(mangaData));

        vp.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        tabLayout.setTabData(mTitles);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
