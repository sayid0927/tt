package com.wemgmemgfang.bt.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by quantan.liu on 2017/3/22.
 */

public class BaseFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> mTitleList;

    public BaseFragmentPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public BaseFragmentPageAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitleList){
        super(fm);
        this.fragmentList = fragmentList;
        this.mTitleList = mTitleList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }



    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position);
        } else {
            return "";
        }
    }
}
