package gcg.testproject.activity.SplashAndGuide;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.activity.MainActivity;
import gcg.testproject.base.BaseActivity;

public class GuideActivity extends BaseActivity {

    @Bind(R.id.vg_guide)
    ViewPager viewPager;
    @Bind(R.id.activity_guide)
    RelativeLayout activityGuide;
    @Bind(R.id.indicater_container)
    LinearLayout indicaterContainer;

    private GuideAdapter guideAdapter;
    private ArrayList<Integer> guideList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initView();
        initIndicator(); //设置指示器
    }

    private void initView() {
        guideList = new ArrayList<>();
        guideList.add(R.mipmap.cp_guanzhu);
        guideList.add(R.mipmap.dd_xiaoxi);
        guideList.add(R.mipmap.gouwuche);
        guideList.add(R.mipmap.icon_add_jia);


        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                page.setScaleY(1); //设置切换时不缩放
                page.setScaleX(1);
            }
        });
        guideAdapter = new GuideAdapter();
        viewPager.setAdapter(guideAdapter);
    }

    private void initIndicator() {
        for (int i = 0; i < guideList.size(); i++) {
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(40, 40);
            // 设置每个小圆点距离左边的间距
            margin.setMargins(12, 0, 12, 0);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(40, 40);
            iv.setImageResource(R.drawable.guide_indicator_bg); //可以在R.drawable.guide_indicator_bg文件中改变指示器的颜色
            if (i == 0) {
                iv.setSelected(true);
            }
            indicaterContainer.addView(iv, margin);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int childCount = indicaterContainer.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (i == position) {
                        indicaterContainer.getChildAt(i).setSelected(true);
                    } else {
                        indicaterContainer.getChildAt(i).setSelected(false);
                    }
                }
                if (position == 3) //最后一页的时候，指示器隐藏，显示“立即体验”
                {
                    indicaterContainer.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (guideList == null) {
                return 0;
            } else {
                return guideList.size();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(GuideActivity.this).inflate(R.layout.splash_item, container, false);

            TextView tv_tiyan = (TextView) view.findViewById(R.id.tv_tiyan); //立即体验
            TextView tv_skip = (TextView) view.findViewById(R.id.tv_skip); //跳过
            ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic); //图片

            Glide.with(GuideActivity.this).load(guideList.get(position)).into(iv_pic);

            if (position == guideList.size() - 1) {
                tv_tiyan.setVisibility(View.VISIBLE);
                tv_tiyan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(GuideActivity.this, MainActivity.class));
                        GuideActivity.this.finish();
                    }
                });
            }
            container.addView(view);
            return view;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
