package com.wemgmemgfang.bt.ui.fragment.homeChildFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseFragment;
import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.bean.HomeInfoBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.presenter.contract.HotsVarietyContract;
import com.wemgmemgfang.bt.presenter.impl.HotsVarietyPresenter;
import com.wemgmemgfang.bt.ui.activity.DetailsActivity;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;
import com.wemgmemgfang.bt.view.RoundedImageView;
import com.wemgmemgfang.bt.view.gallerlib.GallerAdapter;
import com.wemgmemgfang.bt.view.gallerlib.GallerViewPager;
import com.wemgmemgfang.bt.view.gallerlib.ScaleGallerTransformer;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * sayid ....
 * Created by wengmf on 2018/3/12.
 */

public class HotsVarietyFragment extends BaseFragment implements  HotsVarietyContract.View {



    @Inject
    HotsVarietyPresenter mPresenter;

    private List<HomeInfoBean.HotsInfoBean> data;

    @BindView(R.id.view_pager)
    GallerViewPager viewPager;

    public static HotsVarietyFragment getInstance(List<HomeInfoBean.HotsInfoBean> data) {
        HotsVarietyFragment sf = new HotsVarietyFragment();
        sf.data = data;
        return sf;
    }


    @Override
    protected void initView(Bundle bundle) {
        viewPager.setAdapter(new Adapter(getActivity(),data));
        viewPager.setPageTransformer(true, new ScaleGallerTransformer());
        viewPager.setDuration(4000);
        viewPager.startAutoCycle();
        viewPager.setSliderTransformDuration(1500, null);
    }

    @Override
    public void loadData() {
        setState(Constant.STATE_SUCCESS);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_hots_film;
    }

    @Override
    public void attachView() {
        mPresenter.attachView(this);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public void showError(String message) {

    }

    class Adapter extends GallerAdapter {
        private  List<HomeInfoBean.HotsInfoBean> data;
        private Context context;
        public  Adapter(Context context, List<HomeInfoBean.HotsInfoBean> data){
            this.data = data;
            this.context =context;
        }

        @Override
        public int getGallerSize() {
            if (data!=null && data.size() != 0)
                return data.size();
            else
                return 0;
        }

        @Override
        public View getItemView(final int position) {
            View view =  LayoutInflater.from(getActivity()).inflate(R.layout.item_hotsimg, null);
            RoundedImageView rouiv = (RoundedImageView) view.findViewById(R.id.rou_iv);
            TextView tv =(TextView) view.findViewById(R.id.rou_txt);
            tv.setText(data.get(position-1).getTitle());
            ImgLoadUtils.GifloadImage(context,data.get(position-1).getImgUrl(),rouiv);

            rouiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailsActivity.class);
                    intent.putExtra("HrefUrl",data.get(position-1).getHerf());
                    intent.putExtra("imgUrl",data.get(position-1).getImgUrl());
                    intent.putExtra("Title",data.get(position-1).getTitle());
                    getActivity().startActivity(intent);
                }
            });
            return  view;
        }
    }
}
