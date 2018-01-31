package gcg.testproject.activity.SelectVideo;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.donkingliang.imageselector.constant.Constants;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;

import java.util.ArrayList;

import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.ToastUtils;

/**
 * 选择视频的类
 * @ClassName:VideoSelectActivity

 * @PackageName:com.newtonapple.zhangyiyan.zhangyiyan.activity

 * @Create On 2017/5/23 0023   15:55

 * @Site:http://www.handongkeji.com

 * @author:gongchenghao

 * @Copyrights 2017/5/23 0023 handongkeji All rights reserved.
 */

public class VideoSelectActivity extends BaseActivity {

    private TextView tvTime;
    private TextView tvFolderName;
    private TextView tvConfirm;
    private TextView tvPreview;
    private FrameLayout btnConfirm; //确定按钮
    private FrameLayout btnPreview;
    private RecyclerView rvImage;
    private RecyclerView rvFolder;
    private View masking;

    private GridLayoutManager mLayoutManager;

    private boolean isSingle;
    private int mMaxCount;

    private ArrayList<VideoItem> videoItems;
    private VideoSelectAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.donkingliang.imageselector.R.layout.activity_image_select);

        Intent intent = getIntent();
        isSingle = intent.getBooleanExtra("isSingle",true);
        mMaxCount = intent.getIntExtra("num",0);

        // 判断屏幕方向
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(this, 3);
        } else {
            mLayoutManager = new GridLayoutManager(this, 5);
        }

        setStatusBarColor(); //设置状态栏的颜色
        initView(); // findViewById()找到需要用到的控件
        initListener(); //设置控件中的点击事件
        initData();
        setSelectImageCount(0);
    }


    /**
     * 修改状态栏颜色
     */
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#373c3d"));
        }
    }

    private void initView() {
        rvImage = (RecyclerView) findViewById(R.id.rv_image);
        rvFolder = (RecyclerView) findViewById(R.id.rv_folder);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tvPreview = (TextView) findViewById(R.id.tv_preview);
        btnConfirm = (FrameLayout) findViewById(R.id.btn_confirm);
        btnPreview = (FrameLayout) findViewById(R.id.btn_preview);
        tvFolderName = (TextView) findViewById(R.id.tv_folder_name);
        tvTime = (TextView) findViewById(R.id.tv_time);
        masking = findViewById(R.id.masking);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("视频");
        rvFolder.setVisibility(View.GONE);
    }

    private void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<VideoItem> videoList = new ArrayList<>();
                videoList.addAll(adapter.getSelectVideo());
                toPreviewActivity(videoList, 0);
            }
        });

        //点击确定按钮
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

    }
    private void setSelectImageCount(int count) {
        if (count == 0) {
            btnConfirm.setEnabled(false);
            btnPreview.setEnabled(false);
            tvConfirm.setText("确定");
            tvPreview.setText("预览");
        } else {
            btnConfirm.setEnabled(true);
            btnPreview.setEnabled(true);
            tvPreview.setText("预览(" + count + ")");
            if (isSingle) {
                tvConfirm.setText("确定");
            } else if (mMaxCount > 0) {
                tvConfirm.setText("确定(" + count + "/" + mMaxCount + ")");
            } else {
                tvConfirm.setText("确定(" + count + ")");
            }
        }
    }


//    private int getFirstVisibleItem() {
//        return mLayoutManager.findFirstVisibleItemPosition();
//    }


    private void confirm() {
        if (adapter == null) {
            return;
        }
        //因为图片的实体类是Image，而我们返回的是String数组，所以要进行转换。
        ArrayList<VideoItem> selectVideo = adapter.getSelectVideo();
        ArrayList<String> videoPathList = new ArrayList<>();
        for (VideoItem videoItem : selectVideo) {
            videoPathList.add(videoItem.data);
        }

        //点击确定，把选中的图片通过Intent传给上一个Activity。
        Intent intent = new Intent();
        intent.putStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT, videoPathList);
        setResult(RESULT_OK, intent);

        finish();
    }

    private void toPreviewActivity(ArrayList<VideoItem> videoItems, int position) {
        if (videoItems != null && !videoItems.isEmpty()) {

            ToastUtils.showShort(VideoSelectActivity.this,"播放视频");
            Intent intent = new Intent(this,VideoPlayActivity.class);
            intent.putExtra("path",videoItems.get(position).data);
            startActivity(intent);
        }
    }

    /**
     * 处理视频播放页返回的结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RESULT_CODE) {

            if (data != null && data.getBooleanExtra(Constants.IS_CONFIRM, false)) {
                //如果用户在预览页点击了确定，就直接把用户选中的图片返回给用户。
                confirm();
            } else {
                //否则，就刷新当前页面。
                adapter.notifyDataSetChanged();
                setSelectImageCount(adapter.getSelectVideo().size());
            }
        }
    }

    /**
     * 横竖屏切换处理
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mLayoutManager != null && adapter != null) {
            //切换为竖屏
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                mLayoutManager.setSpanCount(3);
            }
            //切换为横屏
            else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mLayoutManager.setSpanCount(5);
            }
            adapter.notifyDataSetChanged();
        }
    }

    //查询视频数据
    public void initData() {
        AsyncQueryHandler queryHandler = new AsyncQueryHandler(getContentResolver()) {
            /** 当查询完成的时候会调用这个方法，这个方法会运行在UI线程，参数cursor就是查询出来的结果  */
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                int count = cursor.getCount(); //获取cursor中的行数
                if (count > 0)
                {
                    videoItems = getVideoItems(cursor);
                    rvImage.setLayoutManager(mLayoutManager);
                    adapter = new VideoSelectAdapter(VideoSelectActivity.this,mMaxCount,isSingle,videoItems);
                    rvImage.setAdapter(adapter);
                    ((SimpleItemAnimator) rvImage.getItemAnimator()).setSupportsChangeAnimations(false);

                    adapter.setOnVideoSelectListener(new VideoSelectAdapter.OnVideoSelectListener() {
                        @Override
                        public void OnVideoSelect(VideoItem videoItem, boolean isSelect, int selectCount) {
                            setSelectImageCount(selectCount);
                        }
                    });
                    adapter.setOnItemClickListener(new VideoSelectAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(VideoItem videoItem, int position) {
                            toPreviewActivity(adapter.getData(), position);
                        }
                    });

                }else {
                    ToastUtils.showShort(VideoSelectActivity.this,"暂无视频");
                }

            }
        };

        int token = 0;      // 相当于Message.what
        Object cookie = null;// 相当于Message.obj
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;   // 指定要查询的内容为视频
        String[] projection = {MediaStore.Video.Media._ID, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DURATION, MediaStore.Video.Media.DATA};   // 指定要查询的列
        String selection = null;    // 指定查询条件  name=? and age>?
        String[] selectionArgs = null;  // 指定查询条件中的参数
        String orderBy = MediaStore.Video.Media.TITLE + " ASC"; // 对标题进行升序
        queryHandler.startQuery(token, cookie, uri, projection, selection, selectionArgs, orderBy);// 这个查询方法会运行在子线程
    }

    /**
     * 把Cursor中所有的记录读取出来，保存到ArrayList中
     * @param cursor
     * @return
     */
    private ArrayList<VideoItem> getVideoItems(Cursor cursor) {
        ArrayList<VideoItem> items = new ArrayList<>();
        cursor.moveToFirst();   // 移动到第一条
        do {
            VideoItem item = VideoItem.fromCursor(cursor);
            items.add(item);
        } while (cursor.moveToNext());

        return items;
    }
}
