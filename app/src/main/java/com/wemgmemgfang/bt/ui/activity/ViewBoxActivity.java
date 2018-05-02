package com.wemgmemgfang.bt.ui.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.ZipUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.bean.DownHrefBean;
import com.wemgmemgfang.bt.bean.VideoDetailsBean;
import com.wemgmemgfang.bt.bean.ViewBoxBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.database.CollectionInfoDao;
import com.wemgmemgfang.bt.entity.CollectionInfo;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.presenter.contract.ViewBoxContract;
import com.wemgmemgfang.bt.presenter.impl.ViewBoxPresenter;
import com.wemgmemgfang.bt.ui.adapter.Home_Title_Play_Adapter;
import com.wemgmemgfang.bt.utils.Defaultcontent;
import com.wemgmemgfang.bt.utils.DeviceUtils;
import com.wemgmemgfang.bt.utils.DownLoadHelper;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;
import com.wemgmemgfang.bt.utils.ShareUtils;
import com.wemgmemgfang.bt.utils.UmengUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import player.XLVideoPlayActivity;
import pub.devrel.easypermissions.EasyPermissions;

import static com.wemgmemgfang.bt.utils.DeviceUtils.ReadTxtFile;


/**
 * sayid ....
 * Created by wengmf on 2018/3/12.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class ViewBoxActivity extends BaseActivity implements ViewBoxContract.View, EasyPermissions.PermissionCallbacks {

    @Inject
    ViewBoxPresenter mPresenter;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    TextView title;
    //    @BindView(R.id.llExit)
//    LinearLayout llExit;
//    @BindView(R.id.tvTitle)
//    TextView tvTitle;
    @BindView(R.id.size)
    TextView size;
    @BindView(R.id.sizeNum)
    TextView sizeNum;
    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.llRight)
    LinearLayout llRight;
    @BindView(R.id.title_list)
    RecyclerView titleList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.ll_share)
    LinearLayout llShare;

    private String hrefUrl;
    private DownHrefBean downHrefBean;
    private String strTitle;
    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private CollectionInfoDao collectionInfoDao;
    private boolean isCollertion;
    private String Url, ImgUrl;
    private ArrayList<VideoDetailsBean.VideoLinks> videoLinksList;
    private long taskId;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_viewbox;
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
//        XLTaskHelper.init(getApplicationContext());
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        UmengUtil.onEvent("ViewBoxActivity");
        setSwipeBackEnable(true);
        Url = getIntent().getStringExtra("HrefUrl");
        ImgUrl = getIntent().getStringExtra("ImgUrl");
        mPresenter.Fetch_ViewBoxInfo("http://www.zei8.me" + Url);
        showLoadPd();

    }

    @Override
    public void showError(String message) {
        if (loadPd.isShowing()) {
            loadPd.dismiss();
        }
        showDialog(message);
        UmengUtil.onEvent("showError_ViewBoxActivity");
    }


    @Override
    public void Fetch_ViewBoxInfo_Success(ViewBoxBean data) {

        strTitle = data.getAlt().trim();
        ImgLoadUtils.loadImage(ViewBoxActivity.this, ImgUrl, img);

        toolbar.setTitle(strTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title.setText(strTitle);
        size.setText(data.getSize());
        sizeNum.setText(data.getSizeNum());
        content.setText(data.getContext());
        hrefUrl = "http://www.zei8.me" + data.getHref();

        collectionInfoDao = GreenDaoUtil.getDaoSession().getCollectionInfoDao();
        List<CollectionInfo> cList = collectionInfoDao.queryBuilder().where(CollectionInfoDao.Properties.Title.eq(strTitle)).list();
        if (cList != null && cList.size() != 0) {
            isCollertion = true;
            tvCollection.setText(R.string.Collection_Yes);
            ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc_ss));
        } else {
            isCollertion = false;
            tvCollection.setText(R.string.Collection_No);
            ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc));
        }
        mPresenter.Fetch_HrefUrl(hrefUrl);
    }

    @Override
    public void Fetch_HrefUrl_Success(DownHrefBean data) {
        this.downHrefBean = data;
        mPresenter.download_Zip(data);
    }

    @Override
    public void download_Zip_Success(String filePath) {
        dismissLoadPd();
        final String destFileDir = DeviceUtils.getSDPath(downHrefBean.getTitle());
        videoLinksList = new ArrayList<>();
        try {
            boolean jieya = ZipUtils.unzipFile(filePath, destFileDir);
            if (jieya) {
                FileUtils.deleteFile(filePath);
                List<File> files = FileUtils.listFilesInDir(destFileDir);
                for (File f : files) {
                    if (f.getAbsolutePath().endsWith(".torrent")) {
                        VideoDetailsBean.VideoLinks videoLinks = new VideoDetailsBean.VideoLinks();
                        videoLinks.setThunder(f.getAbsolutePath());
                        videoLinks.setTitle(downHrefBean.getTitle());
                        videoLinksList.add(videoLinks);
                    } else if (f.getAbsolutePath().endsWith(".txt")) {

                        for (String txt : ReadTxtFile(f.getAbsolutePath())) {
                            if (txt.contains("thunder")) {
                                VideoDetailsBean.VideoLinks videoLinks = new VideoDetailsBean.VideoLinks();
                                String tmp = txt.substring(0, txt.indexOf("t"));
                                videoLinks.setThunder(txt.substring(txt.indexOf("t"), txt.length()).trim());
                                if (tmp.equals(""))
                                    videoLinks.setTitle(downHrefBean.getTitle());
                                else
                                    videoLinks.setTitle(tmp);
                                videoLinksList.add(videoLinks);
                            }
                        }
//                        mPresenter.ReadTxtFiles(f.getAbsolutePath());
                    }
                }
                Home_Title_Play_Adapter mAdapter = new Home_Title_Play_Adapter(videoLinksList, ViewBoxActivity.this);
                titleList.setLayoutManager(new LinearLayoutManager(this));
                titleList.setAdapter(mAdapter);
                mAdapter.setOnDownItemClickListener(new Home_Title_Play_Adapter.OnDownItemClickListener() {
                    @Override
                    public void OnDownItemClickListener(VideoDetailsBean.VideoLinks item) {
                        if (!EasyPermissions.hasPermissions(ViewBoxActivity.this, perms)) {
                            EasyPermissions.requestPermissions(this, "需要读写权限", 2000, perms);
                        } else {

                            DownVideoInfo downVideoInfo = new DownVideoInfo();
                            downVideoInfo.setPlayPath(item.getThunder());
                            downVideoInfo.setPlayTitle(item.getTitle());
                            downVideoInfo.setPlayimgUrl(ImgUrl);
                            downVideoInfo.setHrefUrl(Url);
                            downVideoInfo.setHrefTitle(strTitle);
                            downVideoInfo.setType("zei8");
                            downVideoInfo.setState(getString(R.string.Start));
                            downVideoInfo.setSaveVideoPath(DeviceUtils.getSDVideoPath(item.getTitle()));
                            DownLoadHelper.getInstance().submit(ViewBoxActivity.this, downVideoInfo);
                        }
                    }
                });
                mAdapter.setOnPlayItemClickListener(new Home_Title_Play_Adapter.OnPlayItemClickListener() {
                    @Override
                    public void OnPlayItemClickListener(VideoDetailsBean.VideoLinks item) {
                        if (!EasyPermissions.hasPermissions(ViewBoxActivity.this, perms)) {
                            EasyPermissions.requestPermissions(this, "需要读写权限", 1000, perms);
                        } else {
                            String thunderUrl = item.getThunder();
                            Log.e("TAG", thunderUrl);
                            if (thunderUrl != null)
                                XLVideoPlayActivity.intentTo(ViewBoxActivity.this, thunderUrl, item.getTitle());
                        }
                    }
                });
                if (videoLinksList.size() == 0) {
                    showDialog(getString(R.string.Video_No));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Down_Torrent_File_Success() {

    }


    @OnClick({R.id.llRight,R.id.ll_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_share:

                ShareUtils.shareWeb(this, Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.cash, SHARE_MEDIA.WEIXIN
                );

                break;

            case R.id.llRight:

                if (isCollertion) {
                    isCollertion = false;
                    CollectionInfo collectionInfo = collectionInfoDao.queryBuilder().where(CollectionInfoDao.Properties.Title.eq(strTitle)).unique();
                    collectionInfoDao.delete(collectionInfo);
                    tvCollection.setText("收藏");
                    ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc));

                } else {
                    isCollertion = true;
                    CollectionInfo collectionInfo = new CollectionInfo();
                    collectionInfo.setHrefUrl(Url);
                    collectionInfo.setTitle(strTitle);
                    collectionInfo.setImgUrl(ImgUrl);
                    collectionInfo.setItemType("zei8");
                    collectionInfoDao.insert(collectionInfo);
                    tvCollection.setText("收藏");
                    ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc_ss));
                }
                break;

            case R.id.tvTitle:

                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        mPresenter.Fetch_HrefUrl(hrefUrl);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtils.showLongToast("没有权限无法下载电影");
    }

}
