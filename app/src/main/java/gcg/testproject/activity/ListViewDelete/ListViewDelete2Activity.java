package gcg.testproject.activity.ListViewDelete;

import android.os.Bundle;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.ToastUtils;
import gcg.testproject.widget.ListSlideView;

/**
 * listview条目单个侧滑删除
 *
 * @ClassName:ListViewDelete2Activity
 * @PackageName:gcg.testproject.activity.ListViewDelete
 * @Create On 2018/1/29   11:24
 * @Site:http://www.handongkeji.com
 * @author:gongchenghao
 * @Copyrights 2018/1/29 宫成浩 All rights reserved.
 */

public class ListViewDelete2Activity extends BaseActivity {

    @Bind(R.id.silideList)
    ListSlideView silideList;
    @Bind(R.id.activity_list_view_delete2)
    LinearLayout activityListViewDelete2;
    private SlideViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_delete2);
        ButterKnife.bind(this);

        adapter = new SlideViewAdapter(this);
        silideList.setAdapter(adapter);
        adapter.setRemoveListener(new SlideViewAdapter.OnRemoveListener() {

            @Override
            public void onRemoveItem(int position) {
                ToastUtils.showShort(ListViewDelete2Activity.this,"点击了" + position + "项的删除");
            }
        });
    }
}
