package gcg.testproject.activity.ListCountDown;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.widget.TimeTextView;

/**
 * 列表倒计时
 *
 * @ClassName:ListCountDownActivity
 * @PackageName:gcg.testproject.activity.ListCountDown
 * @Create On 2018/1/29   13:34
 * @Site:http://www.handongkeji.com
 * @author:gongchenghao
 * @Copyrights 2018/1/29 宫成浩 All rights reserved.
 */

public class ListCountDownActivity extends BaseActivity {

    @Bind(R.id.lv_count_down)
    ListView lvCountDown;
    @Bind(R.id.activity_list_count_down)
    LinearLayout activityListCountDown;
    private ArrayList<Long> arrayList;

    private int totalTime = 5 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_count_down);
        ButterKnife.bind(this);
        initData();
        lvCountDown.setAdapter(new MyCountDownAdapter());
    }

    private void initData() {
        arrayList = new ArrayList<>();

        arrayList.add(System.currentTimeMillis());
        arrayList.add(System.currentTimeMillis() - 1000);
        arrayList.add(System.currentTimeMillis() - 2000);
        arrayList.add(System.currentTimeMillis() - 3000);
        arrayList.add(System.currentTimeMillis() - 4000);
        arrayList.add(System.currentTimeMillis() - 5000);
        arrayList.add(System.currentTimeMillis() - 6000);
        arrayList.add(System.currentTimeMillis() - 7000);
        arrayList.add(System.currentTimeMillis() - 8000);
        arrayList.add(System.currentTimeMillis() - 9000);
    }

    class MyCountDownAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (arrayList == null || arrayList.size() == 0) {
                return 0;
            } else {
                return arrayList.size();
            }
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(ListCountDownActivity.this).inflate(R.layout.item_count_down, null, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            holder = (ViewHolder) view.getTag();
            holder.tvTime.setTimes(arrayList.get(i),holder.iv_time);
            return view;
        }
    }
    static class ViewHolder {
        @Bind(R.id.tv_time)
        TimeTextView tvTime;
        @Bind(R.id.iv_time)
        ImageView iv_time;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
