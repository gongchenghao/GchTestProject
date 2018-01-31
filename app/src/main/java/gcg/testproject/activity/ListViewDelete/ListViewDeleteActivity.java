package gcg.testproject.activity.ListViewDelete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.ToastUtils;

/**
 * listview所有条目整体侧滑删除
 *
 * @ClassName:ListViewDeleteActivity
 * @PackageName:gcg.testproject.activity.ListViewDelete
 * @Create On 2018/1/29   9:31
 * @Site:http://www.handongkeji.com
 * @author:gongchenghao
 * @Copyrights 2018/1/29 宫成浩 All rights reserved.
 */

public class ListViewDeleteActivity extends BaseActivity {

    @Bind(R.id.btn_slide)
    Button btnSlide;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.activity_list_view_delete)
    LinearLayout activityListViewDelete;

    private MyAdapter myAdapter;
    private ArrayList<String> arrayList;
    private boolean haveSlided = true; //是否已经滑动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_delete);
        ButterKnife.bind(this);

        initView();
    }
    private void initView() {
        arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(i+"");
        }
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);

        btnSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                haveSlided = !haveSlided;
                myAdapter.notifyDataSetChanged();
            }
        });
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (arrayList == null || arrayList.size() == 0)
            {
                return 0;
            }else {
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(ListViewDeleteActivity.this).inflate(R.layout.item_listview_delete, null, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }
            holder = (ViewHolder) view.getTag();
            if (haveSlided == false)
            {
                int dex = 0-270;
                view.scrollTo(dex,0);
            }else {
                view.scrollTo(0,0);
            }
            holder.tvPrice.setText(arrayList.get(i));
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    arrayList.remove(i);
                    notifyDataSetChanged();
                    ToastUtils.showShort(ListViewDeleteActivity.this,"删除成功");
                }
            });
            return view;
        }
    }
    static class ViewHolder {
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.imgIcon)
        ImageView imgIcon;
        @Bind(R.id.tvPrice)
        TextView tvPrice;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.ratingBar)
        RatingBar ratingBar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
