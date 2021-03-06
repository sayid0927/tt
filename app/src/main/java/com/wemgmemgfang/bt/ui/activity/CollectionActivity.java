package com.wemgmemgfang.bt.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.database.CollectionInfoDao;
import com.wemgmemgfang.bt.entity.CollectionInfo;
import com.wemgmemgfang.bt.ui.adapter.CollectionAdapter;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.utils.UmengUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CollectionActivity extends BaseActivity {

    @BindView(R.id.rv_coll)
    RecyclerView rvColl;
    @BindView(R.id.llExit)
    LinearLayout llExit;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private CollectionAdapter collectionAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        CollectionInfoDao collectionInfoDao = GreenDaoUtil.getDaoSession().getCollectionInfoDao();
        List<CollectionInfo> collectionInfoList = collectionInfoDao.loadAll();

        collectionAdapter = new CollectionAdapter(collectionInfoList, this);
        rvColl.setAdapter(collectionAdapter);
        rvColl.setLayoutManager(new LinearLayoutManager(this));
        collectionAdapter.onVideoItemListener(new CollectionAdapter.onVideoItemListener() {
            @Override
            public void onVideoItemListener(CollectionInfo item) {
                if(item.getItemType().equals("zei8")){
                    Intent intent = new Intent(CollectionActivity.this, ViewBoxActivity.class);
                    intent.putExtra("HrefUrl", item.getHrefUrl());
                    intent.putExtra("ImgUrl", item.getImgUrl());
                    intent.putExtra("Title", item.getTitle());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(CollectionActivity.this, DetailsActivity.class);
                    intent.putExtra("HrefUrl", item.getHrefUrl());
                    intent.putExtra("imgUrl", item.getImgUrl());
                    intent.putExtra("Title", item.getTitle());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void initView() {

        UmengUtil.onEvent("CollectionActivity");
        setSwipeBackEnable(true);
        tvTitle.setText("我的收藏");
    }

    @OnClick(R.id.llExit)
    public void onViewClicked() {
        this.finish();
    }

}
