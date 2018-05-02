package com.wemgmemgfang.bt.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.bean.VideoDetailsBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.database.CollectionInfoDao;
import com.wemgmemgfang.bt.entity.CollectionInfo;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.presenter.contract.DetailsActivityContract;
import com.wemgmemgfang.bt.presenter.impl.DetailsActivityPresenter;
import com.wemgmemgfang.bt.ui.adapter.Home_Title_Play_Adapter;
import com.wemgmemgfang.bt.utils.Defaultcontent;
import com.wemgmemgfang.bt.utils.DeviceUtils;
import com.wemgmemgfang.bt.utils.DownLoadHelper;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;
import com.wemgmemgfang.bt.utils.ShareUtils;
import com.wemgmemgfang.bt.utils.UmengUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import player.XLVideoPlayActivity;
import pub.devrel.easypermissions.EasyPermissions;

public class DetailsActivity extends BaseActivity implements DetailsActivityContract.View, EasyPermissions.PermissionCallbacks {

    @Inject
    DetailsActivityPresenter mPresenter;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.size)
    TextView size;
    @BindView(R.id.sizeNum)
    TextView sizeNum;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.title_list)
    RecyclerView titleList;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.llRight)
    LinearLayout llRight;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.ll_share)
    LinearLayout llShare;

    private String HrefUrl, imgUrl, Title;
    private int clickType;
    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private ProgressDialog loadPd, commonPd;
    private String thunderUrl;
    private String url;
    private CollectionInfoDao collectionInfoDao;
    private boolean isCollertion;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_details;
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

        UmengUtil.onEvent("DetailsActivity");
        setSwipeBackEnable(true);

        imgUrl = getIntent().getStringExtra("imgUrl");
        url = getIntent().getStringExtra("HrefUrl");
        HrefUrl = "https://www.80s.tt" + getIntent().getStringExtra("HrefUrl");
        Title = getIntent().getStringExtra("Title");
        mPresenter.Fetch_VideoDetailsInfo(HrefUrl);
        showLoadPd();

        ImgLoadUtils.GifloadImage(this, imgUrl, img);

        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        toolbar.setTitle(Title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title.setText(Title);
        collectionInfoDao = GreenDaoUtil.getDaoSession().getCollectionInfoDao();
        List<CollectionInfo> cList = collectionInfoDao.queryBuilder().where(CollectionInfoDao.Properties.Title.eq(Title)).list();
        if (cList != null && cList.size() != 0) {
            isCollertion = true;
            tvCollection.setText(R.string.Collection_Yes);
            ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc_ss));
        } else {
            isCollertion = false;
            tvCollection.setText(R.string.Collection_No);
            ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc));
        }
    }

    @Override
    public void showError(String message) {
        UmengUtil.onEvent("showError_DetailsActivity");
    }

    @OnClick({R.id.llRight,R.id.ll_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_share:
                ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title, Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.cash, SHARE_MEDIA.WEIXIN
                );
                break;
            case R.id.llRight:

                if (isCollertion) {
                    isCollertion = false;
                    CollectionInfo collectionInfo = collectionInfoDao.queryBuilder().where(CollectionInfoDao.Properties.Title.eq(Title)).unique();
                    collectionInfoDao.delete(collectionInfo);
                    tvCollection.setText(R.string.Collection_Yes);
                    ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc));

                } else {
                    isCollertion = true;
                    CollectionInfo collectionInfo = new CollectionInfo();
                    collectionInfo.setHrefUrl(url);
                    collectionInfo.setTitle(Title);
                    collectionInfo.setImgUrl(imgUrl);
                    collectionInfo.setItemType("ss6");
                    collectionInfoDao.insert(collectionInfo);
                    tvCollection.setText(R.string.Collection_No);
                    ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc_ss));

                }
                break;
        }
    }

    @Override
    public void Fetch_VideoDetailsInfo_Success(final VideoDetailsBean data) {
        dismissLoadPd();
        for (int i = 0; i < data.getVideoInfoBeans().size(); i++) {
            String type = data.getVideoInfoBeans().get(i).getType();
            String putType = data.getVideoInfoBeans().get(i).getPutType();

            if (type != null && !type.equals("")) {
                size.setText(type);
            }
            if (putType != null && !putType.equals("")) {
                sizeNum.setText(putType);
            }
        }
        content.setText(data.getMovieDescription());
        Home_Title_Play_Adapter mAdapter = new Home_Title_Play_Adapter(data.getVideoLinks(), DetailsActivity.this);
        titleList.setLayoutManager(new LinearLayoutManager(this));
        titleList.setAdapter(mAdapter);
        mAdapter.setOnPlayItemClickListener(new Home_Title_Play_Adapter.OnPlayItemClickListener() {
            @Override
            public void OnPlayItemClickListener(VideoDetailsBean.VideoLinks item) {

                if (!EasyPermissions.hasPermissions(DetailsActivity.this, perms)) {
                    EasyPermissions.requestPermissions(this, "需要读写权限", 1000, perms);
                } else {
                    thunderUrl = item.getThunder();
                    if (thunderUrl != null && thunderUrl.startsWith("thunder"))
                        XLVideoPlayActivity.intentTo(DetailsActivity.this, thunderUrl, item.getTitle());
                }
            }
        });

        mAdapter.setOnDownItemClickListener(new Home_Title_Play_Adapter.OnDownItemClickListener() {
            @Override
            public void OnDownItemClickListener(VideoDetailsBean.VideoLinks item) {

                if (!EasyPermissions.hasPermissions(DetailsActivity.this, perms)) {
                    EasyPermissions.requestPermissions(this, "需要读写权限", 2000, perms);
                } else {
                    thunderUrl = item.getThunder();
                    if (thunderUrl != null && thunderUrl.startsWith("thunder")) {
                        DownVideoInfo downVideoInfo = new DownVideoInfo();
                        downVideoInfo.setPlayPath(thunderUrl);
                        downVideoInfo.setPlayTitle(item.getTitle());
                        downVideoInfo.setPlayimgUrl(imgUrl);
                        downVideoInfo.setHrefUrl(url);
                        downVideoInfo.setType("sst");
                        downVideoInfo.setState(getString(R.string.Start));
                        downVideoInfo.setSaveVideoPath(DeviceUtils.getSDVideoPath(item.getTitle()));
                        DownLoadHelper.getInstance().submit(DetailsActivity.this, downVideoInfo);
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtils.showLongToast("没有权限无法下载电影");
    }
}
