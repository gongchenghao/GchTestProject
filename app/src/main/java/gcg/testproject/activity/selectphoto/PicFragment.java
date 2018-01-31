package gcg.testproject.activity.selectphoto;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseFragment;
import gcg.testproject.widget.ZoomImageView;


/**
 * 服务
 * A simple {@link Fragment} subclass.
 */
public class PicFragment extends BaseFragment {


    @Bind(R.id.iv_big_pic)
    ZoomImageView ivBigPic;

    public PicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic, container, false);
        ButterKnife.bind(this, view);
        initView(); //加载数据的方法
        return view;
    }


    private void initView() {
        Bundle bundle = getArguments();//从activity传过来的Bundle
        if(bundle!=null){
            String pisUrl = bundle.getString("pisUrl");
//            Log.i("test111", "pisUrl----------:" + pisUrl);

//            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setMsg("正在加载图片");
//            progressDialog.show();
            Log.i("test111", "progressDialog.show");

            //先加载十分之一尺寸的缩略图，然后再加载全图
//            Glide.with(getContext()).load(pisUrl).thumbnail(0.1f).into(ivBigPic);
            Glide.with(getContext()).load(pisUrl).into(new SimpleTarget<GlideDrawable>() {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                    progressDialog.dismiss();
                    Log.i("test111", "progressDialog.dismiss");

                    ivBigPic.setImageDrawable(resource);
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
