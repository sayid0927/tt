package com.wemgmemgfang.bt.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.bean.VideoDetailsBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.database.CollectionInfoDao;
import com.wemgmemgfang.bt.database.UserInfoDao;
import com.wemgmemgfang.bt.entity.CollectionInfo;
import com.wemgmemgfang.bt.entity.UserInfo;
import com.wemgmemgfang.bt.presenter.contract.DetailsActivityContract;
import com.wemgmemgfang.bt.presenter.impl.DetailsActivityPresenter;
import com.wemgmemgfang.bt.service.DownTorrentVideoService;
import com.wemgmemgfang.bt.ui.adapter.Home_Title_Play_Adapter;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;
import com.wemgmemgfang.bt.utils.PreferUtil;

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
    @BindView(R.id.llExit)
    LinearLayout llExit;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
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
    @BindView(R.id.connection_title)
    RelativeLayout connectionTitle;
    private String HrefUrl, imgUrl, Title;
    private int clickType;
    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private ProgressDialog loadPd, commonPd;
    private String thunderUrl;
    private String url;
    private CollectionInfoDao collectionInfoDao;
    private  boolean isCollertion;
    


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

        imgUrl = getIntent().getStringExtra("imgUrl");
        url = getIntent().getStringExtra("HrefUrl");
        HrefUrl = "https://www.80s.tt" + getIntent().getStringExtra("HrefUrl");
        Title = getIntent().getStringExtra("Title");
        mPresenter.Fetch_VideoDetailsInfo(HrefUrl);
        showLoadPd();

        ImgLoadUtils.GifloadImage(this, imgUrl, img);
        tvTitle.setText(Title);
        title.setText(Title);
        collectionInfoDao = GreenDaoUtil.getDaoSession().getCollectionInfoDao();
        List<CollectionInfo> cList = collectionInfoDao.queryBuilder().where(CollectionInfoDao.Properties.Title.eq(Title)).list();
        if(cList!=null&& cList.size()!=0) {
            isCollertion = true;
            tvCollection.setText("已收藏");
            ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc_ss));
        }else {
            isCollertion = false;
            tvCollection.setText("收藏");
            ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc));
        }
    }

    @Override
    public void showError(String message) {

    }

    @OnClick({R.id.llExit, R.id.llRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llExit:
                this.finish();
                break;
            case R.id.llRight:

                if(isCollertion){
                    isCollertion =false;
                    CollectionInfo collectionInfo = collectionInfoDao.queryBuilder().where(CollectionInfoDao.Properties.Title.eq(Title)).unique();
                    collectionInfoDao.delete(collectionInfo);
                    tvCollection.setText("收藏");
                    ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc));
                    
                }else {
                    isCollertion =true;
                    CollectionInfo collectionInfo = new CollectionInfo();
                    collectionInfo.setHrefUrl(url);
                    collectionInfo.setTitle(Title);
                    collectionInfo.setImgUrl(imgUrl);
                    collectionInfoDao.insert(collectionInfo);
                    tvCollection.setText("已收藏");
                    ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc_ss));

                }
                break;
        }
    }

    @Override
    public void Fetch_VideoDetailsInfo_Success(VideoDetailsBean data) {
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
                    if (thunderUrl != null && thunderUrl.startsWith("thunder"))

                        PreferUtil.getInstance().setPlayPath(thunderUrl);
                    PreferUtil.getInstance().setPlayTitle(item.getTitle());
                    PreferUtil.getInstance().setPlayimgUrl(imgUrl);

                    startService(new Intent(DetailsActivity.this, DownTorrentVideoService.class));

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
