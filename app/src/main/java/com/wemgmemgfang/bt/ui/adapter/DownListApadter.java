package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.DownVideoBean;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.ui.activity.DetailsActivity;
import com.wemgmemgfang.bt.ui.activity.ViewBoxActivity;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import java.util.List;

/**
 * sayid ....
 * Created by wengmf on 2018/3/22.
 */

public class DownListApadter extends BaseQuickAdapter<DownVideoInfo, BaseViewHolder> {

    private Context mContext;
    private List<DownVideoInfo> data;

    public DownListApadter(List<DownVideoInfo> data, Context mContext) {
        super(R.layout.item_down_list, data);
        this.mContext = mContext;
        this.data = data;
    }


    @Override
    protected void convert(BaseViewHolder helper, final DownVideoInfo item) {
        ImageView iv = helper.getView(R.id.iv_down);
        Button but = helper.getView(R.id.bu_delete);
        ProgressBar progressBar= helper.getView(R.id.progressBar);
        ImgLoadUtils.loadImage(mContext, item.getPlayimgUrl(), iv);
        helper.setText(R.id.down_title, item.getPlayTitle());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(item.getType().equals("zei8")){
                   Intent intent = new Intent(mContext, ViewBoxActivity.class);
                   intent.putExtra("HrefUrl",item.getHrefUrl());
                   intent.putExtra("ImgUrl",item.getPlayimgUrl());
                   mContext.startActivity(intent);
               }else {
                   Intent intent = new Intent(mContext, DetailsActivity.class);
                   intent.putExtra("HrefUrl",item.getHrefUrl());
                   intent.putExtra("imgUrl",item.getPlayimgUrl());
                   intent.putExtra("Title",item.getHrefTitle());
                   mContext.startActivity(intent);
               }
            }
        });


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
        void OnDeleteItemListenter(DownVideoInfo item);
    }

}
