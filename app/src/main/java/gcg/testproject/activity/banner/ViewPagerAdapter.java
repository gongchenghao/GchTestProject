package gcg.testproject.activity.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yukuo on 2016/5/10.
 * 这是一个滚动视图的适配器
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<ImageView> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ViewPagerAdapter(List<ImageView> list, OnItemClickListener onItemClickListener) {
        this.list = list;
        this.onItemClickListener = onItemClickListener;
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

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView v = list.get(position);
        if (onItemClickListener != null) {
            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
