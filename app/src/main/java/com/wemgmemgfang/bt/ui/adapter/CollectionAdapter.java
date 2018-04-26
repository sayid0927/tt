package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.MoreInfoBean;
import com.wemgmemgfang.bt.entity.CollectionInfo;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class CollectionAdapter extends BaseQuickAdapter<CollectionInfo, BaseViewHolder> {



    private Context mContext;
    private List<CollectionInfo> data;

    public CollectionAdapter(List<CollectionInfo> data, Context mContext) {
        super( R.layout.item_collection_list, data);
        this.mContext = mContext;
        this.data = data;

    }

    @Override
    protected void convert(BaseViewHolder helper, final CollectionInfo item) {
        helper.setText(R.id.file_title,item.getTitle());
        ImageView iv =   helper.getView(R.id.iv_down);
        ImgLoadUtils.loadImage(mContext,item.getImgUrl(),iv);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVideoItemListener.onVideoItemListener(item);
            }
        });
    }


    private  onVideoItemListener  onVideoItemListener;

    public void onVideoItemListener (onVideoItemListener  onVideoItemListener){
        this.onVideoItemListener =onVideoItemListener;
    }

    public  interface  onVideoItemListener {
        void  onVideoItemListener(CollectionInfo item);
    }

}
