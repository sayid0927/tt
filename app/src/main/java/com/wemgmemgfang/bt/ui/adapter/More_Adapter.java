package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.QuickContactBadge;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.HomeSectionBean;
import com.wemgmemgfang.bt.bean.HomeTitleItem;
import com.wemgmemgfang.bt.bean.MoreInfoBean;
import com.wemgmemgfang.bt.bean.VideoDetailsBean;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class More_Adapter extends BaseQuickAdapter<MoreInfoBean.MoreVideoInfoBean, BaseViewHolder> {



    private Context mContext;
    private List<MoreInfoBean.MoreVideoInfoBean> data;

    public More_Adapter(List<MoreInfoBean.MoreVideoInfoBean> data, Context mContext) {
        super( R.layout.item_img_type, data);
        this.mContext = mContext;
        this.data = data;

    }

    @Override
    protected void convert(BaseViewHolder helper, final MoreInfoBean.MoreVideoInfoBean item) {
              helper.setText(R.id.tv_score,item.getScore());
              helper.setText(R.id.tv_language,item.getLanguage());
              helper.setText(R.id.tv_title,item.getTitle());
              helper.setText(R.id.tv_em,item.getEm());
              ImageView iv = helper.getView(R.id.iv_home_item);
              ImgLoadUtils.GifloadImage(mContext,item.getImgUrl(),iv);
              helper.itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                       onPlayItemClickListener.OnPlayItemClickListener(item);
                  }
              });
    }

    private OnPlayItemClickListener onPlayItemClickListener;

    public void setOnPlayItemClickListener(OnPlayItemClickListener onPlayItemClickListener) {
        this.onPlayItemClickListener = onPlayItemClickListener;
    }

    public interface OnPlayItemClickListener {
        void OnPlayItemClickListener(MoreInfoBean.MoreVideoInfoBean item);
    }

}
