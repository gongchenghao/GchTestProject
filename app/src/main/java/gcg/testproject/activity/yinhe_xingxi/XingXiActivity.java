package gcg.testproject.activity.yinhe_xingxi;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.common.Word;
import gcg.testproject.utils.DensityUtils;
import gcg.testproject.utils.LogUtils;
import gcg.testproject.utils.ScreenUtils;
import gcg.testproject.utils.ToastUtils;

public class XingXiActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.iv_xing_xi)
    ImageView mIvXingXi;
    @Bind(R.id.rv_suo_fang_1)
    RelativeLayout mRvSuoFang1;
    @Bind(R.id.cs_view)
    CustomScrollView mCsView;
    @Bind(R.id.iv_heng_xing)
    ImageView mIvHengXing;
    @Bind(R.id.rv_xing_xi)
    RelativeLayout mRvXingXi;
    @Bind(R.id.ll_dadian)
    LinearLayout mLlDadian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xing_xi);
        ButterKnife.bind(this);
        setZhuangTaiLan();
        initXingXi();
        initDaDian();
        setIcon();
    }

    private void initDaDian() {
        mLlDadian.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_dadian:
                ToastUtils.showShort(XingXiActivity.this,"打点");
                break;
        }
    }

    private void setZhuangTaiLan() {
        //设置本页面为侵入式状态栏的核心代码
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void initXingXi() {
        LogUtils.i("xingxifragment_initView");
        //加载星系图
        //加载星系图
        Bitmap bitmap = LoadBigImageUtils.readBitMap(this, R.mipmap.xingxiditu2);
        mIvXingXi.setImageBitmap(bitmap);

        //设置显示星空图中心位置
        mCsView.post(new Runnable() {
            @Override
            public void run() {
                LogUtils.i("星系移动到中心点");
                int x = DensityUtils.dp2px(XingXiActivity.this, Word.XING_XI_DP) / 2 - ScreenUtils.getScreenWidth(XingXiActivity.this) / 2;
                int y = DensityUtils.dp2px(XingXiActivity.this, Word.XING_XI_DP) / 2 - ScreenUtils.getScreenHeight(XingXiActivity.this) / 2;

                mCsView.scrollTo(x, y);
                Word.FIRST_SCROLLX = mCsView.getScrollX(); //保存X轴初始移动距离
                Word.FIRST_SCROLLY = mCsView.getScrollY();//保存Y轴初始移动距离
            }
        });
        mCsView.setFlexible(false); //设置不需要弹性效果
    }

    private void setIcon() {
        String shangHuJson = "{\n" +
                "\t\"status\": 1,\n" +
                "\t\"data\": [{\n" +
                "\t\t\"shid\": \"447\",\n" +
                "\t\t\"sh_name\": \"国鹏二手车行\",\n" +
                "\t\t\"sh_jc\": \"国鹏\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"3010\",\n" +
                "\t\t\"hb_y\": \"3010\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"454\",\n" +
                "\t\t\"sh_name\": \"顺鑫二手车经济公司\",\n" +
                "\t\t\"sh_jc\": \"顺鑫\",\n" +
                "\t\t\"cws\": \"8\",\n" +
                "\t\t\"star_id\": \"8\",\n" +
                "\t\t\"hb_x\": \"2670\",\n" +
                "\t\t\"hb_y\": \"3010\",\n" +
                "\t\t\"user\": [{\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:55\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:55\"\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"457\",\n" +
                "\t\t\"sh_name\": \"聚龙二手车\",\n" +
                "\t\t\"sh_jc\": \"聚龙二手车\",\n" +
                "\t\t\"cws\": \"8\",\n" +
                "\t\t\"star_id\": \"3\",\n" +
                "\t\t\"hb_x\": \"2670\",\n" +
                "\t\t\"hb_y\": \"2670\",\n" +
                "\t\t\"user\": [{\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:52\"\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"459\",\n" +
                "\t\t\"sh_name\": \"义龙二手车公司\",\n" +
                "\t\t\"sh_jc\": \"义龙\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"3010\",\n" +
                "\t\t\"hb_y\": \"2670\",\n" +
                "\t\t\"user\": [{\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:54\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:55\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:55\"\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"462\",\n" +
                "\t\t\"sh_name\": \"盛鑫二手车经纪有限公司\",\n" +
                "\t\t\"sh_jc\": \"盛鑫\",\n" +
                "\t\t\"cws\": \"8\",\n" +
                "\t\t\"star_id\": \"8\",\n" +
                "\t\t\"hb_x\": \"3283\",\n" +
                "\t\t\"hb_y\": \"3024\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"465\",\n" +
                "\t\t\"sh_name\": \"长江二手车聚鑫二手车\",\n" +
                "\t\t\"sh_jc\": \"聚鑫\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"3\",\n" +
                "\t\t\"hb_x\": \"2840\",\n" +
                "\t\t\"hb_y\": \"3320\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"466\",\n" +
                "\t\t\"sh_name\": \"长江二手车聚鑫二手车\",\n" +
                "\t\t\"sh_jc\": \"聚鑫\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"7\",\n" +
                "\t\t\"hb_x\": \"2397\",\n" +
                "\t\t\"hb_y\": \"3024\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"469\",\n" +
                "\t\t\"sh_name\": \"鸿兴二手车\",\n" +
                "\t\t\"sh_jc\": \"鸿兴\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"6\",\n" +
                "\t\t\"hb_x\": \"2397\",\n" +
                "\t\t\"hb_y\": \"2656\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"471\",\n" +
                "\t\t\"sh_name\": \"新晋商二手车名车晋城旗舰店\",\n" +
                "\t\t\"sh_jc\": \"新晋商\",\n" +
                "\t\t\"cws\": \"8\",\n" +
                "\t\t\"star_id\": \"8\",\n" +
                "\t\t\"hb_x\": \"2840\",\n" +
                "\t\t\"hb_y\": \"2360\",\n" +
                "\t\t\"user\": [{\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:53\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:54\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:54\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:54\"\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"472\",\n" +
                "\t\t\"sh_name\": \"飞驰二手车\",\n" +
                "\t\t\"sh_jc\": \"飞驰\",\n" +
                "\t\t\"cws\": \"5\",\n" +
                "\t\t\"star_id\": \"3\",\n" +
                "\t\t\"hb_x\": \"3283\",\n" +
                "\t\t\"hb_y\": \"2656\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"476\",\n" +
                "\t\t\"sh_name\": \"长江飞驰二部\",\n" +
                "\t\t\"sh_jc\": \"飞驰二部\",\n" +
                "\t\t\"cws\": \"5\",\n" +
                "\t\t\"star_id\": \"9\",\n" +
                "\t\t\"hb_x\": \"3560\",\n" +
                "\t\t\"hb_y\": \"2840\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"481\",\n" +
                "\t\t\"sh_name\": \"志清二手车\",\n" +
                "\t\t\"sh_jc\": \"志清\",\n" +
                "\t\t\"cws\": \"12\",\n" +
                "\t\t\"star_id\": \"8\",\n" +
                "\t\t\"hb_x\": \"3263\",\n" +
                "\t\t\"hb_y\": \"3422\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"484\",\n" +
                "\t\t\"sh_name\": \"中兴二手车经济公司\",\n" +
                "\t\t\"sh_jc\": \"中兴\",\n" +
                "\t\t\"cws\": \"12\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"2840\",\n" +
                "\t\t\"hb_y\": \"3560\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"485\",\n" +
                "\t\t\"sh_name\": \"腾达二手车\",\n" +
                "\t\t\"sh_jc\": \"腾达\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"9\",\n" +
                "\t\t\"hb_x\": \"2258\",\n" +
                "\t\t\"hb_y\": \"3263\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"488\",\n" +
                "\t\t\"sh_name\": \"快易捷汽车服务有限公司\",\n" +
                "\t\t\"sh_jc\": \"快易捷\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"9\",\n" +
                "\t\t\"hb_x\": \"2120\",\n" +
                "\t\t\"hb_y\": \"2840\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"495\",\n" +
                "\t\t\"sh_name\": \"晋城市方程久久名车广场有限公司\",\n" +
                "\t\t\"sh_jc\": \"方程久久\",\n" +
                "\t\t\"cws\": \"13\",\n" +
                "\t\t\"star_id\": \"9\",\n" +
                "\t\t\"hb_x\": \"2417\",\n" +
                "\t\t\"hb_y\": \"2258\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"500\",\n" +
                "\t\t\"sh_name\": \"鼎通2手车\",\n" +
                "\t\t\"sh_jc\": \"鼎通\",\n" +
                "\t\t\"cws\": \"15\",\n" +
                "\t\t\"star_id\": \"7\",\n" +
                "\t\t\"hb_x\": \"2840\",\n" +
                "\t\t\"hb_y\": \"2120\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"502\",\n" +
                "\t\t\"sh_name\": \"海湾名车\",\n" +
                "\t\t\"sh_jc\": \"海湾\",\n" +
                "\t\t\"cws\": \"25\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"3422\",\n" +
                "\t\t\"hb_y\": \"2417\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"505\",\n" +
                "\t\t\"sh_name\": \"晋城诚新二手车\",\n" +
                "\t\t\"sh_jc\": \"诚新\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"3800\",\n" +
                "\t\t\"hb_y\": \"2840\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"509\",\n" +
                "\t\t\"sh_name\": \"新晋商二手车\",\n" +
                "\t\t\"sh_jc\": \"新晋商\",\n" +
                "\t\t\"cws\": \"45\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"3671\",\n" +
                "\t\t\"hb_y\": \"3320\",\n" +
                "\t\t\"user\": [{\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:53\"\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"516\",\n" +
                "\t\t\"sh_name\": \"恒大精品二手车\",\n" +
                "\t\t\"sh_jc\": \"恒大\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"5\",\n" +
                "\t\t\"hb_x\": \"3088\",\n" +
                "\t\t\"hb_y\": \"3767\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"522\",\n" +
                "\t\t\"sh_name\": \"众航车业\",\n" +
                "\t\t\"sh_jc\": \"众航\",\n" +
                "\t\t\"cws\": \"15\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"2592\",\n" +
                "\t\t\"hb_y\": \"3767\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"527\",\n" +
                "\t\t\"sh_name\": \"168车行\",\n" +
                "\t\t\"sh_jc\": \"168\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"2009\",\n" +
                "\t\t\"hb_y\": \"3320\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"530\",\n" +
                "\t\t\"sh_name\": \"长河三鑫二手车\",\n" +
                "\t\t\"sh_jc\": \"长河三鑫\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"8\",\n" +
                "\t\t\"hb_x\": \"1913\",\n" +
                "\t\t\"hb_y\": \"2592\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"532\",\n" +
                "\t\t\"sh_name\": \"诚瑜二手车\",\n" +
                "\t\t\"sh_jc\": \"诚瑜\",\n" +
                "\t\t\"cws\": \"5\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"2360\",\n" +
                "\t\t\"hb_y\": \"2009\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"543\",\n" +
                "\t\t\"sh_name\": \"长河13部霞光二手车\",\n" +
                "\t\t\"sh_jc\": \"13部霞光\",\n" +
                "\t\t\"cws\": \"5\",\n" +
                "\t\t\"star_id\": \"7\",\n" +
                "\t\t\"hb_x\": \"2840\",\n" +
                "\t\t\"hb_y\": \"1880\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"545\",\n" +
                "\t\t\"sh_name\": \"长河12部西关二手车\",\n" +
                "\t\t\"sh_jc\": \"12部西关\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"9\",\n" +
                "\t\t\"hb_x\": \"3320\",\n" +
                "\t\t\"hb_y\": \"2009\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"547\",\n" +
                "\t\t\"sh_name\": \"岩宏二手车\",\n" +
                "\t\t\"sh_jc\": \"岩宏\",\n" +
                "\t\t\"cws\": \"5\",\n" +
                "\t\t\"star_id\": \"9\",\n" +
                "\t\t\"hb_x\": \"3767\",\n" +
                "\t\t\"hb_y\": \"2592\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"548\",\n" +
                "\t\t\"sh_name\": \"岩宏二手车\",\n" +
                "\t\t\"sh_jc\": \"岩宏\",\n" +
                "\t\t\"cws\": \"5\",\n" +
                "\t\t\"star_id\": \"6\",\n" +
                "\t\t\"hb_x\": \"4040\",\n" +
                "\t\t\"hb_y\": \"2840\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"550\",\n" +
                "\t\t\"sh_name\": \"天宇二手车\",\n" +
                "\t\t\"sh_jc\": \"天宇\",\n" +
                "\t\t\"cws\": \"15\",\n" +
                "\t\t\"star_id\": \"5\",\n" +
                "\t\t\"hb_x\": \"3879\",\n" +
                "\t\t\"hb_y\": \"3440\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"552\",\n" +
                "\t\t\"sh_name\": \"汇通二手车行\",\n" +
                "\t\t\"sh_jc\": \"汇通\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"8\",\n" +
                "\t\t\"hb_x\": \"3151\",\n" +
                "\t\t\"hb_y\": \"3999\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"554\",\n" +
                "\t\t\"sh_name\": \"晋德宝二手名车广场\",\n" +
                "\t\t\"sh_jc\": \"晋德宝\",\n" +
                "\t\t\"cws\": \"35\",\n" +
                "\t\t\"star_id\": \"6\",\n" +
                "\t\t\"hb_x\": \"2240\",\n" +
                "\t\t\"hb_y\": \"3879\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"556\",\n" +
                "\t\t\"sh_name\": \"蓝泽4部茄子二手车\",\n" +
                "\t\t\"sh_jc\": \"蓝泽4部茄子\",\n" +
                "\t\t\"cws\": \"20\",\n" +
                "\t\t\"star_id\": \"6\",\n" +
                "\t\t\"hb_x\": \"1801\",\n" +
                "\t\t\"hb_y\": \"3440\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"558\",\n" +
                "\t\t\"sh_name\": \"星驰二手车\",\n" +
                "\t\t\"sh_jc\": \"星驰\",\n" +
                "\t\t\"cws\": \"30\",\n" +
                "\t\t\"star_id\": \"7\",\n" +
                "\t\t\"hb_x\": \"1640\",\n" +
                "\t\t\"hb_y\": \"2840\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"560\",\n" +
                "\t\t\"sh_name\": \"恒鑫二手车\",\n" +
                "\t\t\"sh_jc\": \"恒鑫\",\n" +
                "\t\t\"cws\": \"10\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"1991\",\n" +
                "\t\t\"hb_y\": \"1991\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"9631\",\n" +
                "\t\t\"sh_name\": \"车王精品二手车\",\n" +
                "\t\t\"sh_jc\": \"车王精品\",\n" +
                "\t\t\"cws\": \"19\",\n" +
                "\t\t\"star_id\": \"3\",\n" +
                "\t\t\"hb_x\": \"2840\",\n" +
                "\t\t\"hb_y\": \"1640\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": \"9930\",\n" +
                "\t\t\"sh_name\": \"蓝泽11部良轩二手车行\",\n" +
                "\t\t\"sh_jc\": \"蓝泽11\",\n" +
                "\t\t\"cws\": \"5\",\n" +
                "\t\t\"star_id\": \"4\",\n" +
                "\t\t\"hb_x\": \"3440\",\n" +
                "\t\t\"hb_y\": \"1801\",\n" +
                "\t\t\"user\": []\n" +
                "\t}, {\n" +
                "\t\t\"shid\": 999999,\n" +
                "\t\t\"sqid\": \"15\",\n" +
                "\t\t\"sh_name\": \"\",\n" +
                "\t\t\"sh_jc\": \"\",\n" +
                "\t\t\"cws\": 0,\n" +
                "\t\t\"star_id\": -1,\n" +
                "\t\t\"hb_x\": 0,\n" +
                "\t\t\"hb_y\": 0,\n" +
                "\t\t\"user\": [{\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:51\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:51\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:51\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:52\"\n" +
                "\t\t}, {\n" +
                "\t\t\t\"uid\": \"26\",\n" +
                "\t\t\t\"name\": \"孔凯云\",\n" +
                "\t\t\t\"headaddress\": \"\\/resources\\/mrtx\\/1.jpg\",\n" +
                "\t\t\t\"time\": \"14:52\"\n" +
                "\t\t}]\n" +
                "\t}],\n" +
                "\t\"errMsg\": \"成功\"\n" +
                "}";

        Gson gson = new Gson();
        ShangHuBean shangHuBean = gson.fromJson(shangHuJson, ShangHuBean.class);
        SetIconLoactionUtils.setIconLoaction(XingXiActivity.this, shangHuBean, mRvXingXi);
    }
}
