package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.HomeSectionBean;
import com.wemgmemgfang.bt.bean.HomeTitleItem;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class Home_Title_Adapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    private Context mContext;
    private List<MultiItemEntity> data;

    public Home_Title_Adapter(List<MultiItemEntity> data, Context mContext) {
        super(data);
        this.mContext = mContext;
        this.data = data;
        addItemType(TYPE_LEVEL_0, R.layout.item_text_type);
        addItemType(TYPE_LEVEL_1, R.layout.item_img_type);

    }


    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final HomeTitleItem homeTitleItem = (HomeTitleItem) item;
                helper.setText(R.id.tv_title, homeTitleItem.getTitle());
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onTitleItemClickListener.OnTitleItemClickListener(homeTitleItem);
                    }
                });

                break;
            case TYPE_LEVEL_1:

                final HomeSectionBean homeSectionBean = (HomeSectionBean) item;
                if(homeSectionBean.getLanguage()==null ||  homeSectionBean.getLanguage().equals("")){
                    helper.getView(R.id.bot_fl).setVisibility(View.GONE);
                }else {
                    helper.getView(R.id.bot_fl).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_language,homeSectionBean.getLanguage());
                }
                if(homeSectionBean.getScore()==null || homeSectionBean.getScore().equals("")){
                    helper.getView(R.id.top_fl).setVisibility(View.GONE);
                }else {
                    helper.getView(R.id.top_fl).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_score,homeSectionBean.getScore());
                }
                helper.setText(R.id.tv_title,homeSectionBean.getTitle());
                helper.setText(R.id.tv_em,homeSectionBean.getEm());

                ImageView iv = helper.getView(R.id.iv_home_item);
                ImgLoadUtils.GifloadImage(mContext, homeSectionBean.getImgUrl(), iv);

                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSectionItemClickListener.OnSectionItemClickListener(homeSectionBean);
                    }
                });

                break;
        }
    }

    public void expandAll() {
        for (int i = data.size() - 1 + getHeaderLayoutCount(); i >= getHeaderLayoutCount(); i--) {
            expandAll(i, false, false);
        }
    }

    private OnTitleItemClickListener onTitleItemClickListener;
    private OnSectionItemClickListener onSectionItemClickListener;

    public void setOnTitleItemClickListener(OnTitleItemClickListener onTitleItemClickListener) {
        this.onTitleItemClickListener = onTitleItemClickListener;
    }

    public interface OnTitleItemClickListener {
        void OnTitleItemClickListener(HomeTitleItem item);
    }

    public void setOnSectionItemClickListener(OnSectionItemClickListener onSectionItemClickListener) {
        this.onSectionItemClickListener = onSectionItemClickListener;
    }

    public interface OnSectionItemClickListener {
        void OnSectionItemClickListener(HomeSectionBean item);
    }




}
