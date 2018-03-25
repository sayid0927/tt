package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.DownVideoBean;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import java.util.List;

/**
 * sayid ....
 * Created by wengmf on 2018/3/22.
 */

public class DownListApadter extends BaseQuickAdapter<DownVideoBean, BaseViewHolder> {

    private Context mContext;
    private List<DownVideoBean> data;

    public DownListApadter(List<DownVideoBean> data, Context mContext) {
        super(R.layout.item_down_list, data);
        this.mContext = mContext;
        this.data = data;
    }


    @Override
    protected void convert(BaseViewHolder helper, final DownVideoBean item) {
        ImageView iv = helper.getView(R.id.iv_down);
        Button but = helper.getView(R.id.bu_delete);
        ProgressBar progressBar= helper.getView(R.id.progressBar);
        ImgLoadUtils.GifloadImage(mContext, item.getPlayimgUrl(), iv);
        helper.setText(R.id.down_title, item.getPlayTitle());

        if (item.getmTaskStatus() == 1) {
            progressBar.setVisibility(View.VISIBLE);
            if (item.getmDownloadSize() != 0 && item.getmFileSize() != 0) {
                int ff = (int) (item.getmDownloadSize() * 100 / item.getmFileSize());
                progressBar.setProgress(ff);
                helper.setText(R.id.tv_pro,String.valueOf(ff)+"%" +"         " + String.valueOf(item.getmFileSize())+" / "+String.valueOf(item.getmDownloadSize()) );
            }
        }else {
            progressBar.setVisibility(View.GONE);
        }

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteItemListenter.OnDeleteItemListenter(item);
            }
        });

    }

    private OnDeleteItemListenter onDeleteItemListenter;

    public void OnDeleteItemListenter(OnDeleteItemListenter onDeleteItemListenter) {
        this.onDeleteItemListenter = onDeleteItemListenter;
    }

    public interface OnDeleteItemListenter {
        void OnDeleteItemListenter(DownVideoBean item);
    }

}
