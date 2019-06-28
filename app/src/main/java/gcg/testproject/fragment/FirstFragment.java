package gcg.testproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.activity.ListCountDown.ListCountDownActivity;
import gcg.testproject.activity.ListViewDelete.ListViewDelete2Activity;
import gcg.testproject.activity.ListViewDelete.ListViewDeleteActivity;
import gcg.testproject.activity.RefreshAndLoadMore.RefreshAndLoadMoreActivity;
import gcg.testproject.activity.RightTopPopWindow.RightTopPopActivity;
import gcg.testproject.activity.SelectVideo.SelectVideoActivity;
import gcg.testproject.activity.alipay.PayActivity;
import gcg.testproject.activity.banner.BannerActivity;
import gcg.testproject.activity.chongDianDongHua.ChongDianActivity;
import gcg.testproject.activity.contactlist.ContactListActivity;
import gcg.testproject.activity.dongTaiDaiLi.MyProxyActivity;
import gcg.testproject.activity.erweima.ErWeiMaActivity;
import gcg.testproject.activity.location.GetLocationActivity;
import gcg.testproject.activity.notifycation.Notifycation2Activity;
import gcg.testproject.activity.posui.PoSuiActivity;
import gcg.testproject.activity.progressbar.ProgressBarActivity;
import gcg.testproject.activity.ratingbar.RatingBarActivity;
import gcg.testproject.activity.sanji.SanJiActivity;
import gcg.testproject.activity.selectdate.SelectDateActivity;
import gcg.testproject.activity.selectdate2.SelectDate2Activity;
import gcg.testproject.activity.selectdate3.SelectDate3Activity;
import gcg.testproject.activity.selectphoto.SelectPhotoActivity;
import gcg.testproject.activity.shanxingmenu.ShanXingMenuActivity;
import gcg.testproject.activity.update.UpdateActivity;
import gcg.testproject.activity.xiaohongshu_login.XiaoHongShuLoginActivity;
import gcg.testproject.activity.xuliehua.XuLieHuaActivity;
import gcg.testproject.activity.yinhe_xingxi.XingXiActivity;
import gcg.testproject.activity.yyz3.YyzDongHuaActivity;
import gcg.testproject.activity.zhezhaoceng.ZheZhaoCengActivity;
import gcg.testproject.base.BaseFragment;
import gcg.testproject.dialog.MySimpleDialog;
import gcg.testproject.utils.MoveUtils;
import gcg.testproject.utils.SystemDialogUtils;
import gcg.testproject.utils.ToastUtils;

public class FirstFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.tv_select_photo)
    TextView tv_select_photo;
    @Bind(R.id.tv_select_video)
    TextView tvSelectVideo;
    @Bind(R.id.tv_banner)
    TextView tvBanner;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_date2)
    TextView tvDate2;
    @Bind(R.id.tv_contect)
    TextView tvContect;
    @Bind(R.id.tv_progress_dialog)
    TextView tvProgressDialog;
    @Bind(R.id.tv_select_dialog)
    TextView tvSelectDialog;
    @Bind(R.id.tv_popwindow)
    TextView tvPopwindow;
    @Bind(R.id.tv_round_progressbar)
    TextView tvRoundProgressbar;
    @Bind(R.id.tv_ratingbar)
    TextView tvRatingbar;
    @Bind(R.id.tv_update)
    TextView tvUpdate;
    @Bind(R.id.tv_sanji)
    TextView tvSanji;
    @Bind(R.id.tv_erweima)
    TextView tvErweima;
    @Bind(R.id.tv_pay)
    TextView tv_pay;
    @Bind(R.id.tv_refresh_loadmore)
    TextView tv_refresh_loadmore;
    @Bind(R.id.tv_listview_delete)
    TextView tv_listview_delete;
    @Bind(R.id.tv_listview_delete2)
    TextView tv_listview_delete2;
    @Bind(R.id.tv_list_count_down)
    TextView tv_list_count_down;
    @Bind(R.id.tv_relogin_dialog)
    TextView tv_relogin_dialog;
    @Bind(R.id.tv_location)
    TextView tv_location;
    @Bind(R.id.tv_shanxing_menu)
    TextView mTvShanxingMenu;
    @Bind(R.id.tv_xingxi)
    TextView mTvXingxi;
    @Bind(R.id.tv_xiaohongshu)
    TextView mTvXiaohongshu;
    @Bind(R.id.tv_tongzhi)
    TextView mTvTongzhi;
    @Bind(R.id.tv_xuliehua)
    TextView mTvXuliehua;
    @Bind(R.id.tv_date3)
    TextView mTvDate3;
    @Bind(R.id.tv_yinyingzhuan)
    TextView mTvYinyingzhuan;
    @Bind(R.id.tv_zhezhaoceng)
    TextView mTvZhezhaoceng;
    @Bind(R.id.tv_posui)
    TextView mTvPosui;
    @Bind(R.id.tv_daili)
    TextView tv_daili;
    @Bind(R.id.tv_chong_dian)
    TextView tv_chong_dian;


    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        ButterKnife.bind(this, view);
        initClickEvent();
        return view;
    }

    private void initClickEvent() {
        tv_select_photo.setOnClickListener(this);
        tvSelectVideo.setOnClickListener(this);
        tvBanner.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        tvDate2.setOnClickListener(this);
        tvContect.setOnClickListener(this);
        tvProgressDialog.setOnClickListener(this);
        tvSelectDialog.setOnClickListener(this);
        tvPopwindow.setOnClickListener(this);
        tvRoundProgressbar.setOnClickListener(this);
        tvRatingbar.setOnClickListener(this);
        tvUpdate.setOnClickListener(this);
        tvSanji.setOnClickListener(this);
        tvErweima.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
        tv_refresh_loadmore.setOnClickListener(this);
        tv_listview_delete.setOnClickListener(this);
        tv_listview_delete2.setOnClickListener(this);
        tv_list_count_down.setOnClickListener(this);
        tv_relogin_dialog.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        mTvShanxingMenu.setOnClickListener(this);
        mTvXingxi.setOnClickListener(this);
        mTvXiaohongshu.setOnClickListener(this);
        mTvTongzhi.setOnClickListener(this);
        mTvXuliehua.setOnClickListener(this);
        mTvDate3.setOnClickListener(this);
        mTvYinyingzhuan.setOnClickListener(this);
        mTvZhezhaoceng.setOnClickListener(this);
        mTvPosui.setOnClickListener(this);
        tv_daili.setOnClickListener(this);
        tv_chong_dian.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_photo: //选择照片
                MoveUtils.go(getActivity(), SelectPhotoActivity.class);
                break;
            case R.id.tv_select_video: //选择视频
                MoveUtils.go(getActivity(), SelectVideoActivity.class);
                break;
            case R.id.tv_banner: //轮播图
                MoveUtils.go(getActivity(), BannerActivity.class);
                break;
            case R.id.tv_date: //日期选择器
                MoveUtils.go(getActivity(), SelectDateActivity.class);
                break;
            case R.id.tv_date2: //日期选择器2
                MoveUtils.go(getActivity(), SelectDate2Activity.class);
                break;
            case R.id.tv_date3: //日期选择器3
                MoveUtils.go(getActivity(), SelectDate3Activity.class);
                break;
            case R.id.tv_contect: //联系人列表
                MoveUtils.go(getActivity(), ContactListActivity.class);
                break;
            case R.id.tv_progress_dialog: //加载框样例
                dialogShow();
//                dialogShow("正在获取数据...");
                progressDialog.setCancelable(true);
                break;
            case R.id.tv_select_dialog: //功能框样例
                new MySimpleDialog(getActivity()).show();
                break;
            case R.id.tv_popwindow: //仿微信右上角弹框
                MoveUtils.go(getActivity(), RightTopPopActivity.class);
                break;
            case R.id.tv_round_progressbar: //进度条：掌一眼圆形进度条、系统自带进度条
                MoveUtils.go(getActivity(), ProgressBarActivity.class);
                break;
            case R.id.tv_ratingbar: //ratingbar
                MoveUtils.go(getActivity(), RatingBarActivity.class);
                break;
            case R.id.tv_update: //版本更新
                MoveUtils.go(getActivity(), UpdateActivity.class);
                break;
            case R.id.tv_sanji: //省市区三级联动
                MoveUtils.go(getActivity(), SanJiActivity.class);
                break;
            case R.id.tv_erweima: //扫描二维码
                MoveUtils.go(getActivity(), ErWeiMaActivity.class);
                break;
            case R.id.tv_pay: //微信支付、支付宝支付
                MoveUtils.go(getActivity(), PayActivity.class);
                break;
            case R.id.tv_refresh_loadmore: //下拉刷新、上拉加载
                MoveUtils.go(getActivity(), RefreshAndLoadMoreActivity.class);
                break;
            case R.id.tv_listview_delete: //listview所有条目整体侧滑删除
                MoveUtils.go(getActivity(), ListViewDeleteActivity.class);
                break;
            case R.id.tv_listview_delete2: //listview单个条目侧滑删除
                MoveUtils.go(getActivity(), ListViewDelete2Activity.class);
                break;
            case R.id.tv_list_count_down: //listview条目倒计时
                MoveUtils.go(getActivity(), ListCountDownActivity.class);
                break;
            case R.id.tv_relogin_dialog: //重新登录提示框
                reLogin();
                break;
            case R.id.tv_location: //定位
                MoveUtils.go(getActivity(), GetLocationActivity.class);
                break;
            case R.id.tv_shanxing_menu: //扇形菜单
                MoveUtils.go(getActivity(), ShanXingMenuActivity.class);
                break;
            case R.id.tv_xingxi: //星系图
                MoveUtils.go(getActivity(), XingXiActivity.class);
                break;
            case R.id.tv_xiaohongshu: //仿小红书的视频背景登录页
                MoveUtils.go(getActivity(), XiaoHongShuLoginActivity.class);
                break;
            case R.id.tv_tongzhi: //跳转到通知Activity
                MoveUtils.go(getActivity(), Notifycation2Activity.class);
                break;
            case R.id.tv_xuliehua: //跳转到序列化界面
                MoveUtils.go(getActivity(), XuLieHuaActivity.class);
                break;
            case R.id.tv_yinyingzhuan: //银英传3.0动画效果
                MoveUtils.go(getActivity(), YyzDongHuaActivity.class);
                break;
            case R.id.tv_zhezhaoceng: //自定义遮罩层
                MoveUtils.go(getActivity(), ZheZhaoCengActivity.class);
                break;
            case R.id.tv_posui: //破碎效果
                MoveUtils.go(getActivity(), PoSuiActivity.class);
                break;
            case R.id.tv_daili: //动态代理
                MoveUtils.go(getActivity(), MyProxyActivity.class);
                break;
            case R.id.tv_chong_dian: //仿华为手机充电
                MoveUtils.go(getActivity(), ChongDianActivity.class);
                break;
        }
    }

    private void reLogin() {
        new SystemDialogUtils().showMissingPermissionDialog(
                getActivity(),
                "提示",
                "账号已在其它地方登录！是否重新登录？",
                "确定",
                "取消", new SystemDialogUtils.AfterClick() {
                    @Override
                    public void confirm() {
                        ToastUtils.showShort(getActivity(), "点击了重新登录");
                    }

                    @Override
                    public void cancle() {
                        ToastUtils.showShort(getActivity(), "点击了取消");
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
