package gcg.testproject.activity.selectphoto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.common.Word;
import gcg.testproject.utils.LogUtils;

//掌眼结果中的大图
public class DaTuActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.head_title)
    TextView headTitle;
    @Bind(R.id.fanhui)
    LinearLayout fanhui;
    @Bind(R.id.tuikuan)
    LinearLayout tuikuan;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.ib_more)
    ImageButton ibMore;
    @Bind(R.id.title)
    RelativeLayout title;
    @Bind(R.id.v_pic)
    ViewPager vPic;
    private ArrayList<String> JieGuoUrlsss = new ArrayList<>();
    private ArrayList<String> JieGuoUrlsss2 = new ArrayList<>();
    public MyFragmentPagerAdapter pagerAdapter;
    private int position; //当前应该显示第几张图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_tu);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ibMore.setOnClickListener(this);
        fanhui.setOnClickListener(this);

        Intent intent = getIntent();

        //传过来的图片地址的集合
        JieGuoUrlsss = intent.getStringArrayListExtra("JieGuoUrlsss");
        position = intent.getIntExtra("position", 0);
        LogUtils.i("传递过来的："+JieGuoUrlsss.size());
        String fromWhere = intent.getStringExtra("fromWhere"); //判断是否显示删除按钮
        if (!TextUtils.isEmpty(fromWhere) && fromWhere.equals("datu"))
        {
            ibMore.setVisibility(View.VISIBLE);
        }

        if (JieGuoUrlsss != null && JieGuoUrlsss.size() > 0) {
//            headTitle.setText(1 + "/" + JieGuoUrlsss.size());

            for (int i = 0; i < JieGuoUrlsss.size(); i++) {
                String s = JieGuoUrlsss.get(i);
                String a = s.replace("_mid", "");
                JieGuoUrlsss2.add(a);
            }
        }
        JieGuoUrlsss.clear();
        JieGuoUrlsss.addAll(JieGuoUrlsss2);
        LogUtils.i("换成大图后的："+JieGuoUrlsss.size());


//        Log.i("test111", "JieGuoUrlsss:" + JieGuoUrlsss.toString());

        String flag = intent.getStringExtra("flag");
        if (flag != null && flag.equals("jieguo")) {
            ibMore.setVisibility(View.GONE);
        }

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        vPic.setAdapter(pagerAdapter); //给ViewPager设置Adapter
        vPic.setCurrentItem(position); //设置显示点击进来的那个图片

        headTitle.setText((position + 1) + "/" + JieGuoUrlsss.size());

        //设置标题
        vPic.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                headTitle.setText((position + 1) + "/" + JieGuoUrlsss.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            PicFragment picFragment = new PicFragment();
            Bundle bundle = new Bundle();
            bundle.putString("pisUrl", JieGuoUrlsss.get(position).trim()); //注意：集合的每个条目之间的逗号后面会有一个空格
            picFragment.setArguments(bundle);
            return picFragment;
        }

        @Override
        public int getCount() {
            if (JieGuoUrlsss == null || JieGuoUrlsss.size() == 0) {
                return 0;
            } else {
                return JieGuoUrlsss.size();
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.fanhui:
                Intent mIntent = new Intent();
                LogUtils.i("点击返回按钮时的集合："+JieGuoUrlsss.size());
                mIntent.putStringArrayListExtra("jieGuoUrl", JieGuoUrlsss);
                // 设置结果，并进行传送
                this.setResult(Word.REQUEST_CODE2, mIntent);
                finish();
                break;
            case R.id.ib_more:
                final DeleteDialog dialog = new DeleteDialog(DaTuActivity.this,"确定要删除这张照片吗？");
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (dialog.hasDelete == true) {
                            int aItem = vPic.getCurrentItem();
                            JieGuoUrlsss.remove(vPic.getCurrentItem());

                            if (JieGuoUrlsss.size() == 0) {
                                Intent mIntent = new Intent();
                                mIntent.putStringArrayListExtra("jieGuoUrl", JieGuoUrlsss);
                                // 设置结果，并进行传送
                                DaTuActivity.this.setResult(Word.REQUEST_CODE2, mIntent);
                                finish();
                            }
                            if (aItem == 0 && JieGuoUrlsss.size() > 0) //如果删除的是第一张
                            {
                                headTitle.setText(1 + "/" + JieGuoUrlsss.size());
                            } else {
                                headTitle.setText((vPic.getCurrentItem() + 1) + "/" + JieGuoUrlsss.size());
                            }
                            pagerAdapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
        }
    }

}
