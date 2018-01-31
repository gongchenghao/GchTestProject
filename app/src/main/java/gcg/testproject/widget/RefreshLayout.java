package gcg.testproject.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.load.model.file_descriptor.FileDescriptorStringLoader;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.ArrayList;

import gcg.testproject.R;
import gcg.testproject.utils.LogUtils;
import gcg.testproject.utils.ToastUtils;

/**
 * Created by Administrator on 2017/2/16 0016.
 */

public class RefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener{

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;
    /**
     * listview实例
     */
    private ListView mListView;

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

    /**
     * ListView的加载中footer
     */
    private View mListViewFooter;

    private View mListViewNoMoreFooter; //没有更多数据时的脚布局

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    private boolean hasMore = true; //是否还有更多数据

    /**
     * @param context
     */

//    public boolean isHaveDataEnough = true; //判断外界是否有数据或者当前页的数据是否>= pageSize
    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null, false);
        mListViewNoMoreFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer_no_more, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 初始化ListView对象
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取ListView对象
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                mListView.setOnScrollListener(this);
                Log.d(VIEW_LOG_TAG, "### 找到listview");
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp() && isRefreshing()==false ;
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {

        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作,并且没有在执行刷新操作，而且有更多数据，那么执行onLoad方法
     */
    private void loadData() {
        LogUtils.i("loadData");
        LogUtils.i("hasMore:"+hasMore);
        if (hasMore == false) //如果没有更多数据了
        {
            ToastUtils.showShort(getContext(),"没有更多数据了");
            setLoading(false);
            if (mListView.getFooterViewsCount() == 0)
            {
                setNoMoreFootView();
            }
        }

        if (mOnLoadListener != null && hasMore == true) {
            // 设置状态
            mOnLoadListener.onLoad();
            setLoading(true);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        // 滚动时到了最底部也可以加载更多
        if (canLoad()) {
            loadData();
        }

    }
    /**
     * 加载更多的监听器
     *
     * @author mrsimple
     */
    public static interface OnLoadListener {
        public void onLoad();
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

//    ================================================================== 外部调用方法 ========================================================

    public void hasMore(boolean hasMore) //设置是否还有更多数据
    {
        LogUtils.i("hasMore()");
        LogUtils.i("hasMore:"+hasMore);
        this.hasMore = hasMore;
    }
    /**
     * @param loading
     */
    public void setLoading(boolean loading) {  //判断是否显示正在加载的脚布局
        isLoading = loading;
        if (isLoading && isRefreshing() == false) {
            int footerViewsCount = mListView.getFooterViewsCount();
            if (footerViewsCount == 0) //保证只添加一个脚布局
            {
                mListView.addFooterView(mListViewFooter);
            }
        } else {
            LogUtils.i("else");
            if (mListView != null && mListViewFooter != null)
            {
                LogUtils.i("都不为null");
                mListView.removeFooterView(mListViewFooter);
                mYDown = 0;
                mLastY = 0;
            }
        }
    }

    //完完网络完成后，设置隐藏  “刷新的圆圈”  和  显示“正在加载”的脚布局
    public void setRefreshLayout(RefreshLayout swipe_layout) {
        //设置可以进行下拉刷新操作
        swipe_layout.setEnabled(true);
        if (swipe_layout.isRefreshing()) { //如果获取完数据后swipe_layout处于下拉刷新状态，就停止下拉刷新
            swipe_layout.setRefreshing(false);
        }
        if (isLoading) { //如果获取完数据后swipe_layout处于上拉加载状态，就停止上拉加载
            LogUtils.i("setRefreshLayout ----> isLoading:"+isLoading);
            swipe_layout.setLoading(false);
        }
    }

    //完成网络请求后，判断获取到的集合size是否为0，如果为0，显示暂无数据界面,否则显示数据
    public void setGetNoDate(boolean hasDate, RefreshLayout swipe_layout, ImageView ivZanWu) {
        if (hasDate == false) {
            swipe_layout.setVisibility(View.GONE);
            ivZanWu.setVisibility(View.VISIBLE);
        } else {
            swipe_layout.setVisibility(View.VISIBLE);
            ivZanWu.setVisibility(View.GONE);
        }
    }

    //在第一次加载时需要先进行以下设置，否则可能会报空指针
    public void setFirstLoadFootView(BaseAdapter adapter)
    {
        mListView.addFooterView(mListViewFooter);
        mListView.setAdapter(adapter);
        mListView.removeFooterView(mListViewFooter);
        adapter.notifyDataSetChanged();
    }

    //设置没有更多数据时显示的footview
    public void setNoMoreFootView()
    {
        mListView.addFooterView(mListViewNoMoreFooter);
    }

    //在下拉刷新时调用这个方法，移除“没有更多数据”的脚布局，同时将hasMore设置为true,方便上拉加载
    public void removeNoMoreFootView()
    {
        mListView.removeFooterView(mListViewNoMoreFooter);
        hasMore = true;
    }

}
