package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.DownRaningBean;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class DownRanking_Adapter extends BaseQuickAdapter<DownRaningBean, BaseViewHolder> {

   private Context mContext;

    public DownRanking_Adapter(List<DownRaningBean> data, Context mContext) {
        super(R.layout.item_downranking, data);
        this.mContext= mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, final DownRaningBean item) {

        ImageView iv = helper.getView(R.id.iv_android_pic);
        ImgLoadUtils.loadImage(mContext,item.getImgUrl(),iv);

        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_add,item.getAdd());
        helper.setText(R.id.tv_intro,item.getIntro());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(item);
            }
        });
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(DownRaningBean item);
    }

}
