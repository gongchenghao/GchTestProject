package gcg.testproject.activity.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by yukuo on 2016/5/10.
 * 一个可以自己设置是否可以滚动的viewpager
 */
public class SetingScrollBanner extends ViewPager {
    public SetingScrollBanner(Context context) {
        super(context);
    }

    public SetingScrollBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean scrollable = true;


    /**
     * 设置viewpager是否可以滚动
     *
     * @param enable
     */
    public void setScrollable(boolean enable) {
        scrollable = enable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 触摸事件不触发
        if (scrollable) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 分发事件，这个是必须要的，如果把这个方法覆盖了，那么ViewPager的子View就接收不到事件了
        if (scrollable) {
            return super.dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (scrollable) {
            return super.onInterceptTouchEvent(event);
        } else {
            return false;
        }
    }
}
