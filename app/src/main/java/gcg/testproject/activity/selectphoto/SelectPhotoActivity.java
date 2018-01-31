package gcg.testproject.activity.selectphoto;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import gcg.testproject.R;
import gcg.testproject.activity.MainActivity;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.common.Word;
import gcg.testproject.utils.LogUtils;

public class SelectPhotoActivity extends BaseActivity{

    @Bind(R.id.gv_pics)
    GridView gvPics;
    private int size = 9; //最多能选择的照片数量
    private ArrayList<String> pics = new ArrayList<>(); //已经选择的照片的路径集合
    private GridViewAdapter adapter;


    //权限数组
    private String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_phone);
        ButterKnife.bind(this);
        checkPromission();
    }

    public void initData() {
        adapter = new GridViewAdapter();
        gvPics.setAdapter(adapter);

        gvPics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if ((i + 1) > pics.size()) //表示这个框没有图片
                {
                    size = 9 - pics.size(); //
                    showSelectPhotoDialog();
                } else { //表示这个框有图片，有图片就跳转到大图界面
                    Intent intent = new Intent(SelectPhotoActivity.this, DaTuActivity.class);
                    intent.putStringArrayListExtra("JieGuoUrlsss",pics);
                    intent.putExtra("position",i); //传过去的是点击的位置
                    intent.putExtra("fromWhere","datu"); //用来判断是否在大图界面显示删除按钮
                    startActivityForResult(intent, Word.REQUEST_CODE2);
                }
            }
        });
    }

    //检查权限
    private void checkPromission() {
        if (Build.VERSION.SDK_INT >= 23) { //判断是否是Android6.0以上的系统，如果是，就申请权限
            if (verifyPermissions(mPermissionList)) //有权限
            {
                initData();
            } else {
                ActivityCompat.requestPermissions(this, mPermissionList, 123); //申请权限
            }
        } else {
            initData();
        }
    }

    private void showSelectPhotoDialog() {
        SelectPhotoDialog dialog = new SelectPhotoDialog(size, SelectPhotoActivity.this, Word.REQUEST_CODE1, new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                //拍照的回调
                Log.i("test1", "onHanlderSuccess");

                Log.i("test1", "pics:" + pics.toString());
                pics.add(resultList.get(0).getPhotoPath());
                Log.i("test1", "pics:" + pics.toString());

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {
                Log.i("test1", "onHanlderFailure");
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!verifyPermissions(permissions)) { //未申请到权限
            showMissingPermissionDialog();
        } else {
            initData();
        }
    }

    //多选时的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //本地相册选择图片的回调
        if (requestCode == Word.REQUEST_CODE1 && data != null) {
            Log.i("test1", "多选回调111");

            ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);

            pics.addAll(images);
            Log.i("test1", "pics：" + pics.toString());

            adapter.notifyDataSetChanged();
        }
        //删除图片的回调
        if (requestCode == Word.REQUEST_CODE2 && data != null) {
            Log.i("test", "删除回调111");

            ArrayList<String> jieGuoUrl = data.getStringArrayListExtra("jieGuoUrl");
            pics.clear();
            pics.addAll(jieGuoUrl);
            adapter.notifyDataSetChanged();
        }
    }

    public class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (pics == null || pics.size() == 0) {
                return 1;
            } else if (pics.size() < 9) {
                return pics.size() + 1; //最多九张图片
            } else {
                return pics.size();
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                //加载GridView的条目布局
                convertView = LayoutInflater.from(SelectPhotoActivity.this).inflate(R.layout.item_pic, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();

            LogUtils.i("============== position:"+position);
            if ((position + 1) <= pics.size()) {
                holder.iv_pic_1.setVisibility(View.VISIBLE);
                holder.iv_pic_2.setVisibility(View.GONE);
                Glide.with(SelectPhotoActivity.this).load(pics.get(position)).into(holder.iv_pic_1);
                LogUtils.i("if");
            }else {
                holder.iv_pic_1.setVisibility(View.GONE);
                holder.iv_pic_2.setVisibility(View.VISIBLE);
            }
            return convertView;
        }
    }

    static class ViewHolder {
        @Bind(R.id.iv_pic_1)
        ImageView iv_pic_1;
        @Bind(R.id.iv_pic_2)
        ImageView iv_pic_2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
