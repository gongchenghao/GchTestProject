package gcg.testproject.activity.banner;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.model.file_descriptor.FileDescriptorStringLoader;

import java.util.ArrayList;
import java.util.List;

import gcg.testproject.R;


/**
 * Created by yukuo on 2016/5/10.
 * 这是一个横向滚动的viewpager
 */
public class BannerViewPager extends LinearLayout implements ViewPager.OnPageChangeListener {
    private OnItemClickListener onItemClickListener;
    private FrameLayout fl_banner_viewpager;
    private LinearLayout ll_scroll_banner_indicator;
    private SetingScrollBanner vp_banner;
    private List<String> list = new ArrayList<>();//数据源集合
    private List<ImageView> imageViews = new ArrayList<ImageView>();//图片集合
    private int scrolltime = 3000; // 默认滚动时间
    private long releaseTime = 0; // 手指松开、页面不滚动时间，防止手机松开后短时间进行切换
    private int LOOP = 200; // 转动
    private int LOOP_WAIT = 201; // 等待
    //是否循环 默认循环
    private boolean isLoop = true;
    private boolean isWheel = true; // 是否轮播
    private boolean isScrolling = false;//是否在滚动
    private ImageView[] indicators;
    private ViewPagerAdapter viewPagerAdapter;
    private int currentPosition;
    private int icon1; //选中时
    private int icon2; //未选中时


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LOOP && imageViews.size() != 0) {
                if (!isScrolling) {
                    int max = imageViews.size() + 1;
                    int position = (currentPosition + 1) % imageViews.size();
                    vp_banner.setCurrentItem(position, true);
                    if (position == max) { // 最后一页时回到第一页
                        vp_banner.setCurrentItem(1, false);
                    }
                }

                releaseTime = System.currentTimeMillis();
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, scrolltime);
                return;
            }
            if (msg.what == LOOP_WAIT && imageViews.size() != 0) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, scrolltime);
            }

        }
    };

    public BannerViewPager(Context context) {
        super(context);
        init(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //设置宽高
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        View view = View.inflate(context, R.layout.view_scrollbanner, null);
        fl_banner_viewpager = (FrameLayout) view.findViewById(R.id.fl_banner_viewpager);
        vp_banner = (SetingScrollBanner) view.findViewById(R.id.vp_banner);
        ll_scroll_banner_indicator = (LinearLayout) view.findViewById(R.id.ll_scroll_banner_indicator);
        addView(view);
    }

    /**
     * 设置数据源
     *
     * @param views    viee集合
     * @param list     数据源集合
     * @param listener 回调监听
     */
    public void setData(List<ImageView> views, List<String> list, OnItemClickListener listener) {
        setData(views, list, listener, 0);
    }

    /**
     * 设置是否轮播，默认不轮播,轮播一定是循环的
     *
     * @param isWheel
     */
    public void setWheel(boolean isWheel) {
        this.isWheel = isWheel;
        isLoop = true;
        if (isWheel) {
            handler.postDelayed(runnable, scrolltime);
        }
    }

    /**
     * 是否处于轮播状态
     *
     * @return
     */
    public boolean isWheel() {
        return isWheel;
    }

    /**
     * 设置数据源
     *
     * @param views        view集合
     * @param list         数据源集合
     * @param listener     回调监听
     * @param showPosition 默认选中的位置
     */
    public void setData(List<ImageView> views, List<String> list, OnItemClickListener listener, int showPosition) {
        this.onItemClickListener = listener;
        this.list = list;
        imageViews.clear();
        /**
         * 如果没有数据的话就隐藏展示
         */
        if (list.size() == 0) {
            fl_banner_viewpager.setVisibility(View.GONE);
            return;
        }
        /**
         * 如果只有一个图片就不显示下边的指针了
         */
        if (list.size() == 1) {
            setScrollable(false);
            isWheel = false;
            ll_scroll_banner_indicator.setVisibility(View.GONE);
            this.imageViews.add(views.get(0));
        }else{
            //添加数据
            for (ImageView item : views) {
                this.imageViews.add(item);
            }
        }

        //设置指示器
        int ivSize = views.size();
        indicators = new ImageView[ivSize];
        //如果是需要循环的话就重新设置指示器
        if (isLoop)
            indicators = new ImageView[ivSize - 2];
        ll_scroll_banner_indicator.removeAllViews();
        for (int i = 0; i < indicators.length; i++) {
            View view = View.inflate(getContext(), R.layout.view_banner_viewpager_indicator, null);
            indicators[i] = (ImageView) view.findViewById(R.id.iv_baner_indicator);
            ll_scroll_banner_indicator.addView(view);
        }
        viewPagerAdapter = new ViewPagerAdapter();
        // 默认指向第一项，下方viewPager.setCurrentItem将触发重新计算指示器指向
        setIndicator(0);
        vp_banner.setOffscreenPageLimit(3);//默认缓存所有的界面
        vp_banner.setOnPageChangeListener(this);

        vp_banner.setAdapter(viewPagerAdapter);
        if (showPosition < 0 || showPosition > views.size()) {
            showPosition = 0;
        }
        if (isLoop) {
            showPosition = showPosition + 1;
        }
        vp_banner.setCurrentItem(showPosition);
    }

    /**
     * 设置指示器
     *
     * @param selectedPosition 默认指示器位置
     */
    private void setIndicator(int selectedPosition) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(R.mipmap.icon_point);
        }
        if (indicators.length > selectedPosition)
            indicators[selectedPosition].setBackgroundResource(R.mipmap.icon_point_pre);
    }

    /**
     * 是否循环，默认不开启，开启前，请将views的最前面与最后面各加入一个视图，用于循环
     * 如果需要设置请在添加数据之前的时候进行使用
     *
     * @param isLoop 是否循环
     */
    public void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    /**
     * 是否处于循环状态
     *
     * @return
     */
    public boolean isLoop() {
        return isLoop;
    }

    final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (getContext() != null
                    && isWheel) {
                long now = System.currentTimeMillis();
                // 检测上一次滑动时间与本次之间是否有触击(手滑动)操作，有的话等待下次轮播
                if (now - releaseTime > scrolltime - 500) {
                    handler.sendEmptyMessage(LOOP);
                } else {
                    handler.sendEmptyMessage(LOOP_WAIT);
                }
            }
        }
    };


    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable 是否
     */
    public void setScrollable(boolean enable) {
        vp_banner.setScrollable(enable);
    }

    /**
     * 设置轮播暂停时间，即没多少秒切换到下一张视图.默认3000ms
     *
     * @param time 毫秒为单位
     */
    public void setScrollTime(int time) {
        this.scrolltime = time;
    }

    /**
     * 设置指示器居中，默认指示器在右方
     */
    public void setIndicatorCenter() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        ll_scroll_banner_indicator.setLayoutParams(params);
    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (viewPagerAdapter != null)
            viewPagerAdapter.notifyDataSetChanged();
    }

    /**
     * banner点击的监听器
     */
    public interface OnItemClickListener {
        /**
         * 条目点击回调的方法
         *
         * @param postion 位置
         */
        void onItemClick(int postion);
    }

    /**
     * 页面适配器 返回对应的view
     *
     * @author Yuedong Li
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            ImageView v = imageViews.get(position);
            if (onItemClickListener != null) {
                v.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list.size() == 1) {
                            onItemClickListener.onItemClick(0);
                        } else {
                            onItemClickListener.onItemClick(position - 1);
                        }
                    }
                });
            }
//            Log.i("test","list:position:"+list.toString());
//            Glide.with(getContext()).load(list.get(position)).into(v);
            container.addView(v);
            return v;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    /**
     * 隐藏Banner
     */
    public void hideBanner() {
        fl_banner_viewpager.setVisibility(View.GONE);
    }

    /**
     * 隐藏指示器
     */
    public void hideIndicator() {
        ll_scroll_banner_indicator.setVisibility(View.GONE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int max = imageViews.size() - 1;
        int positi = position;
        currentPosition = position;
        if (isLoop) {
            if (position == 0) {
                currentPosition = max - 1;
            } else if (position == max) {
                currentPosition = 1;
            }
            positi = currentPosition - 1;
        }
        setIndicator(positi);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 1) { // viewPager在滚动
            isScrolling = true;
            return;
        } else if (state == 0) { // viewPager滚动结束
            if (vp_banner != null)
                vp_banner.setScrollable(true);

            releaseTime = System.currentTimeMillis();

            vp_banner.setCurrentItem(currentPosition, false);

        }
        isScrolling = false;
    }
}
