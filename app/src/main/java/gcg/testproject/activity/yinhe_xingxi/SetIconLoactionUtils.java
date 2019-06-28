package gcg.testproject.activity.yinhe_xingxi;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import gcg.testproject.R;
import gcg.testproject.common.Constants;
import gcg.testproject.common.Word;
import gcg.testproject.utils.DensityUtils;
import gcg.testproject.utils.LogUtils;
import gcg.testproject.utils.ScreenUtils;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * 设置星球周边头像的位置
 *
 * @ClassName:SetIconLoactionUtils
 * @PackageName:eiyudensetsu.ginga.youxin.com.yinheyingxiong.utils2
 * @Create On 2018/3/26   11:34
 * @author:gongchenghao
 * @Copyrights 2018/3/26 宫成浩 All rights reserved.
 */

public class SetIconLoactionUtils {
    //注意：获取到头像的坐标点后，将其转化成这个头像距离原点的x轴的百分比和y轴的百分比，然后分别乘以1800dp，以获取其距离x轴和y轴的dp值

    //注意：星球及头像的位置以在xml中设置的星系图的宽高（1800dp）为基准
    //添加商户对应的星球及位置
    public static void setIconLoaction(Context context, ShangHuBean shangHuBean, RelativeLayout mRvXingXi) {
		List<ShangHuBean.DataBean> data = shangHuBean.getData();
        if (data == null) {
			LogUtils.i("data == null,return了");
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            String star_id = data.get(i).getStar_id();
            double x = data.get(i).getHb_x();

			if (TextUtils.isEmpty(data.get(i).getHb_y()))
			{
				LogUtils.i("hb_y:"+data.get(i).getHb_y()+"return了");
				return;
			}
			double y = Double.parseDouble(data.get(i).getHb_y());
            int x1 = (int) (x * Word.XING_XI_DP / Word.XING_XI_PX); //根据比例转化成dp
            int y1 = (int) (y * Word.XING_XI_DP / Word.XING_XI_PX); //根据比例转化成dp


            //设置星球及商户简称
            String sh_jc = data.get(i).getSh_jc(); //商户简称
            String cws = data.get(i).getCws(); //商户车位数
            switch (star_id) {
                case "-1"://表示是太阳
					setTaiYangIcon(context,mRvXingXi,data.get(i).getUser().size(),data.get(i).getUser());
					break;
                case "2":
                    setXingQiu(context, R.mipmap.t2, mRvXingXi, x1, y1, sh_jc,cws);
                    break;
                case "3":
                    setXingQiu(context, R.mipmap.t3, mRvXingXi, x1, y1, sh_jc,cws);
                    break;
                case "4":
                    setXingQiu(context, R.mipmap.t4, mRvXingXi, x1, y1, sh_jc,cws);
                    break;
                case "5":
                    setXingQiu(context, R.mipmap.t5, mRvXingXi, x1, y1, sh_jc,cws);
                    break;
                case "6":
                    setXingQiu(context, R.mipmap.t6, mRvXingXi, x1, y1, sh_jc,cws);
                    break;
                case "7":
                    setXingQiu(context, R.mipmap.t7, mRvXingXi, x1, y1, sh_jc,cws);
                    break;
                case "8":
                    setXingQiu(context, R.mipmap.t8, mRvXingXi, x1, y1, sh_jc,cws);
                    break;
                case "9":
                    setXingQiu(context, R.mipmap.t9, mRvXingXi, x1, y1, sh_jc,cws);
                    break;
            }
            if (data.get(i).getUser() == null) {
                return;
            }
            if (star_id.equals("-1"))
			{
				LogUtils.i("star_id == -1,return 了");
				return;
			}

			int size = data.get(i).getUser().size(); //获取到的用户数量
            switch (size)//设置头像
            {
                case 1:
                    SetIconLoactionUtils.setIcon1(context, x1, y1, mRvXingXi, data.get(i).getUser());
					break;
                case 2:
                    SetIconLoactionUtils.setIcon2(context, x1, y1, mRvXingXi, data.get(i).getUser());
                    break;
                case 3:
                    SetIconLoactionUtils.setIcon3(context, x1, y1, mRvXingXi, data.get(i).getUser());
                    break;
                case 4:
                    SetIconLoactionUtils.setIcon4(context, x1, y1, mRvXingXi, data.get(i).getUser());
                    break;
                case 5:
                    SetIconLoactionUtils.setIcon5(context, x1, y1, mRvXingXi, data.get(i).getUser());
                    break;
            }
        }
    }


    //只有一个头像  x、y均为dp
    //传入星球周围的头像集合，然后拿到头像的地址和打点时间
    public static void setIcon1(Context context, int x, int y, RelativeLayout mRvXingXi, List<ShangHuBean.DataBean.UserBean> user) {
        ShangHuBean.DataBean.UserBean userBean = user.get(0);
        LinearLayout linerLayout = getLinerLayout(context, userBean);
        RelativeLayout.LayoutParams rvRvyoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams.topMargin = DensityUtils.dp2px(context, y - 15);
        rvRvyoutParams.leftMargin = DensityUtils.dp2px(context, x );
        mRvXingXi.addView(linerLayout, rvRvyoutParams);
    }

    //x、y均为dp
    //传入星球周围的头像集合，然后拿到头像的地址和打点时间
    public static void setIcon2(Context context, int x, int y, RelativeLayout mRvXingXi, List<ShangHuBean.DataBean.UserBean> userList) {
        //头像位置1
        LinearLayout linerLayout1 = getLinerLayout(context, userList.get(0));
        RelativeLayout.LayoutParams rvRvyoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams.topMargin = DensityUtils.dp2px(context, y - 15);
        rvRvyoutParams.leftMargin = DensityUtils.dp2px(context, x - 25);
        mRvXingXi.addView(linerLayout1, rvRvyoutParams);

        //头像位置2
        LinearLayout linerLayout2 = getLinerLayout(context, userList.get(1));
        RelativeLayout.LayoutParams rvRvyoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams2.topMargin = DensityUtils.dp2px(context, y - 15);
        rvRvyoutParams2.leftMargin = DensityUtils.dp2px(context, x + 30);
        mRvXingXi.addView(linerLayout2, rvRvyoutParams2);
    }

    //	 x、y均为dp
//   传入星球周围的头像集合，然后拿到头像的地址和打点时间
    public static void setIcon3(Context context, int x, int y, RelativeLayout mRvXingXi, List<ShangHuBean.DataBean.UserBean> userList) {
        //头像位置1（最上面）
        LinearLayout linerLayout3 = getLinerLayout(context, userList.get(0));
        RelativeLayout.LayoutParams rvRvyoutParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        rvRvyoutParams3.topMargin = DensityUtils.dp2px(context, y - 60);
        rvRvyoutParams3.topMargin = DensityUtils.dp2px(context, y - 69);
        rvRvyoutParams3.leftMargin = DensityUtils.dp2px(context, x + 2);
        mRvXingXi.addView(linerLayout3, rvRvyoutParams3);

        //头像位置2（右面）
        LinearLayout linerLayout2 = getLinerLayout(context, userList.get(1));
        RelativeLayout.LayoutParams rvRvyoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams2.topMargin = DensityUtils.dp2px(context, y - 15);
        rvRvyoutParams2.leftMargin = DensityUtils.dp2px(context, x + 42);
        mRvXingXi.addView(linerLayout2, rvRvyoutParams2);

        //头像位置3（左面）
        LinearLayout linerLayout1 = getLinerLayout(context, userList.get(2));
        RelativeLayout.LayoutParams rvRvyoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams.topMargin = DensityUtils.dp2px(context, y - 15);
        rvRvyoutParams.leftMargin = DensityUtils.dp2px(context, x - 33);
        mRvXingXi.addView(linerLayout1, rvRvyoutParams);

    }

    //	 x、y均为dp
//   传入星球周围的头像集合，然后拿到头像的地址和打点时间
    public static void setIcon4(Context context, int x, int y, RelativeLayout mRvXingXi, List<ShangHuBean.DataBean.UserBean> userList) {
        //头像位置1（左上）
        LinearLayout linerLayout1 = getLinerLayout(context, userList.get(0));
        RelativeLayout.LayoutParams rvRvyoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams.topMargin = DensityUtils.dp2px(context, y - 86);
        rvRvyoutParams.leftMargin = DensityUtils.dp2px(context, x - 25);
        mRvXingXi.addView(linerLayout1, rvRvyoutParams);

        //头像位置2（右上）
        LinearLayout linerLayout2 = getLinerLayout(context, userList.get(1));
        RelativeLayout.LayoutParams rvRvyoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams2.topMargin = DensityUtils.dp2px(context, y - 86);
        rvRvyoutParams2.leftMargin = DensityUtils.dp2px(context, x + 30);
        mRvXingXi.addView(linerLayout2, rvRvyoutParams2);

        //头像位置3（左下）
        LinearLayout linerLayout3 = getLinerLayout(context, userList.get(2));
        RelativeLayout.LayoutParams rvRvyoutParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams3.topMargin = DensityUtils.dp2px(context, y-15);
        rvRvyoutParams3.leftMargin = DensityUtils.dp2px(context, x - 25);
        mRvXingXi.addView(linerLayout3, rvRvyoutParams3);

        //头像位置4（右下）
        LinearLayout linerLayout4 = getLinerLayout(context, userList.get(3));
        RelativeLayout.LayoutParams rvRvyoutParams4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams4.topMargin = DensityUtils.dp2px(context, y-15);
        rvRvyoutParams4.leftMargin = DensityUtils.dp2px(context, x + 30);
        mRvXingXi.addView(linerLayout4, rvRvyoutParams4);
    }

    //	 x、y均为dp
//   传入星球周围的头像集合，然后拿到头像的地址和打点时间
    public static void setIcon5(Context context, int x, int y, RelativeLayout mRvXingXi, List<ShangHuBean.DataBean.UserBean> userList) {

        //头像位置1（左上）
        LinearLayout linerLayout1 = getLinerLayout(context, userList.get(0));
        RelativeLayout.LayoutParams rvRvyoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams.topMargin = DensityUtils.dp2px(context, y - 64);
        rvRvyoutParams.leftMargin = DensityUtils.dp2px(context, x - 50);
        mRvXingXi.addView(linerLayout1, rvRvyoutParams);

        //头像位置2（上面中间）
        LinearLayout linerLayout5 = getLinerLayout(context, userList.get(1));
        RelativeLayout.LayoutParams rvRvyoutParams5 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams5.topMargin = DensityUtils.dp2px(context, y - 64);
        rvRvyoutParams5.leftMargin = DensityUtils.dp2px(context, x + 2);
        mRvXingXi.addView(linerLayout5, rvRvyoutParams5);

        //头像位置3（右上）
        LinearLayout linerLayout2 = getLinerLayout(context, userList.get(2));
        RelativeLayout.LayoutParams rvRvyoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams2.topMargin = DensityUtils.dp2px(context, y - 64);
        rvRvyoutParams2.leftMargin = DensityUtils.dp2px(context, x + 56);
        mRvXingXi.addView(linerLayout2, rvRvyoutParams2);

        //头像位置4（左下）
        LinearLayout linerLayout3 = getLinerLayout(context, userList.get(3));
        RelativeLayout.LayoutParams rvRvyoutParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams3.topMargin = DensityUtils.dp2px(context, y+7);
        rvRvyoutParams3.leftMargin = DensityUtils.dp2px(context, x - 50);
        mRvXingXi.addView(linerLayout3, rvRvyoutParams3);

        //头像位置5（右下）
        LinearLayout linerLayout4 = getLinerLayout(context, userList.get(4));
        RelativeLayout.LayoutParams rvRvyoutParams4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rvRvyoutParams4.topMargin = DensityUtils.dp2px(context, y+7);
        rvRvyoutParams4.leftMargin = DensityUtils.dp2px(context, x + 56);
        mRvXingXi.addView(linerLayout4, rvRvyoutParams4);

    }

//	//设置太阳的icon  x:星球的x坐标 y:星球的y坐标  count:当前太阳周围有几个头像  x、y均为dp
	public static void setTaiYangIcon(Context context,RelativeLayout mRvXingXi,int count,List<ShangHuBean.DataBean.UserBean> userList)
	{
//		getTaiYangTextView(context,mRvXingXi);
		if (count == 0)
		{
			return;
		}
		LinearLayout linerLayout1 = getLinerLayout(context,userList.get(0));
		RelativeLayout.LayoutParams rvRvyoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

		//xml中已经将图片宽高设置成了2610dp
		rvRvyoutParams1.topMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2-90);
		rvRvyoutParams1.leftMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2 - 25);
		mRvXingXi.addView(linerLayout1,rvRvyoutParams1);

		if (count == 1)
		{
			return;
		}

		LinearLayout linerLayout2 = getLinerLayout(context,userList.get(1));
		RelativeLayout.LayoutParams rvRvyoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rvRvyoutParams2.topMargin =  DensityUtils.dp2px(context,Word.XING_XI_DP/2 - 60);
		rvRvyoutParams2.leftMargin =  DensityUtils.dp2px(context,Word.XING_XI_DP/2+22) ;
		mRvXingXi.addView(linerLayout2,rvRvyoutParams2);

//		if (count == 2)
//		{
//			return;
//		}

//		LinearLayout linerLayout3 = getLinerLayout(context,userList.get(2));
//		RelativeLayout.LayoutParams rvRvyoutParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		rvRvyoutParams3.topMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2 - 60);
//		rvRvyoutParams3.leftMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2+58);
//		mRvXingXi.addView(linerLayout3,rvRvyoutParams3);

//		if (count == 3)
//		{
//			return;
//		}

//		LinearLayout linerLayout4 = getLinerLayout(context,userList.get(3));
//		RelativeLayout.LayoutParams rvRvyoutParams4 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		rvRvyoutParams4.topMargin =DensityUtils.dp2px(context,Word.XING_XI_DP/2 );
//		rvRvyoutParams4.leftMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2+58) ;
//		mRvXingXi.addView(linerLayout4,rvRvyoutParams4);

		if (count == 2)
		{
			return;
		}

		LinearLayout linerLayout5 = getLinerLayout(context,userList.get(2));
		RelativeLayout.LayoutParams rvRvyoutParams5 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rvRvyoutParams5.topMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2+7);
		rvRvyoutParams5.leftMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2+20) ;
		mRvXingXi.addView(linerLayout5,rvRvyoutParams5);

//		if (count == 3)
//		{
//			return;
//		}
//
//		LinearLayout linerLayout6 = getLinerLayout(context,userList.get(5));
//		RelativeLayout.LayoutParams rvRvyoutParams6 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		rvRvyoutParams6.topMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2 +10);
//		rvRvyoutParams6.leftMargin =DensityUtils.dp2px(context,Word.XING_XI_DP/2 - 25) ;
//		mRvXingXi.addView(linerLayout6,rvRvyoutParams6);

		if (count == 3)
		{
			return;
		}

		LinearLayout linerLayout7 = getLinerLayout(context,userList.get(3));
		RelativeLayout.LayoutParams rvRvyoutParams7 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rvRvyoutParams7.topMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2+7) ;
		rvRvyoutParams7.leftMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2 - 70) ;
		mRvXingXi.addView(linerLayout7,rvRvyoutParams7);

//		if (count == 6)
//		{
//			return;
//		}

//		LinearLayout linerLayout8 = getLinerLayout(context,userList.get(7));
//		RelativeLayout.LayoutParams rvRvyoutParams8 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		rvRvyoutParams8.topMargin =DensityUtils.dp2px(context,Word.XING_XI_DP/2 ) ;
//		rvRvyoutParams8.leftMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2 - 110) ;
//		mRvXingXi.addView(linerLayout8,rvRvyoutParams8);

//		if (count == 8)
//		{
//			return;
//		}

//		LinearLayout linerLayout9 = getLinerLayout(context,userList.get(8));
//		RelativeLayout.LayoutParams rvRvyoutParams9 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		rvRvyoutParams9.topMargin =DensityUtils.dp2px(context,Word.XING_XI_DP/2 - 60) ;
//		rvRvyoutParams9.leftMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2-105) ;
//		mRvXingXi.addView(linerLayout9,rvRvyoutParams9);

		if (count == 4)
		{
			return;
		}

		LinearLayout linerLayout10 = getLinerLayout(context,userList.get(4));
		RelativeLayout.LayoutParams rvRvyoutParams10 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rvRvyoutParams10.topMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2 - 60) ;
		rvRvyoutParams10.leftMargin = DensityUtils.dp2px(context,Word.XING_XI_DP/2 - 72);
		mRvXingXi.addView(linerLayout10,rvRvyoutParams10);
	}
//	public static void getTaiYangTextView(Context context, RelativeLayout mRvXingXi) {
//		//创建一个TextView，用来放商圈名称，将TextView放到LinearLayout中
//		TextView tv_View = new TextView(context);
//		//设置TextView宽度为200px
//		RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context, 100), ViewGroup.LayoutParams.WRAP_CONTENT);
//		tv_View.setText("优信门店");
//		tv_View.setTextSize(DensityUtils.dp2px(context, 3));
//		tv_View.setTextColor(Color.parseColor("#ffffff"));
//		tv_View.setGravity(Gravity.CENTER);
//		tvParams.topMargin = DensityUtils.dp2px(context, Word.XING_XI_DP/2+50);
//		tvParams.leftMargin = DensityUtils.dp2px(context, Word.XING_XI_DP/2-52);
//		mRvXingXi.addView(tv_View, tvParams);
//	}

    private static LinearLayout getLinerLayout(Context context, ShangHuBean.DataBean.UserBean userBean) {
        //先创建一个线性布局,将线性布局放到RelativeLayout中
        LinearLayout ll_view = new LinearLayout(context);
        ll_view.setOrientation(LinearLayout.VERTICAL); //设置子布局竖直排列
        ll_view.setGravity(Gravity.CENTER_HORIZONTAL); //设置子布局水平居中

        //创建一个TextView，用来放打卡时间，将TextView放到LinearLayout中
        TextView tv_View = new TextView(context);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll_view.addView(tv_View, tvParams);
        tv_View.setText(userBean.getTime());
        tv_View.setTextSize(DensityUtils.dp2px(context, 3));
        tv_View.setTextColor(Color.parseColor("#ffffff"));
		//将设置时间的字体格式为：DS  Digital Bold
		Typeface typeFace = Typeface.createFromAsset(context.getAssets(),"fonts/DSDIGIB.TTF");
		tv_View.setTypeface(typeFace);


		RelativeLayout relativeLayout = getIconGroup(context, userBean);
		LinearLayout.LayoutParams relativeLayoutIvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		relativeLayoutIvParams.setMargins(0,DensityUtils.dp2px(context,3),0,0);
		ll_view.addView(relativeLayout,relativeLayoutIvParams); //将头像及头像框整体添加上

        //创建一个TextView，用来放打卡人姓名，设置宽度为100dp,将TextView放到LinearLayout中
        TextView tv_name = new TextView(context);
        LinearLayout.LayoutParams tvNameP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvNameP.topMargin = DensityUtils.dp2px(context, -15);
        int padding = DensityUtils.dp2px(context, 2);
        tv_name.setPadding(padding, padding, padding, padding); //设置padding
        ll_view.addView(tv_name, tvNameP);
        tv_name.setText(userBean.getName());
        tv_name.setTextSize(DensityUtils.dp2px(context, 2));
        tv_name.setTextColor(Color.parseColor("#ffffff"));
        tv_name.setBackgroundResource(R.drawable.rect_name);
        return ll_view;
    }

    //获取头像和头像框
	private static RelativeLayout getIconGroup(Context context, ShangHuBean.DataBean.UserBean userBean) {
		RelativeLayout relativeLayout = new RelativeLayout(context);

		//将ImageView放到线性布局里面，打卡人头像
		ImageView iv_View = new ImageView(context);
		//设置头像宽高为45dp
		RelativeLayout.LayoutParams ivParams = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context, 35), DensityUtils.dp2px(context, 35));
		ivParams.setMargins(DensityUtils.dp2px(context,9.5f),DensityUtils.dp2px(context,11),0,0);

		//获取圆形图片
		Glide.with(context)
				.load(Constants.URL_IMAGE + userBean.getHeadaddress())
				.bitmapTransform(new CropCircleTransformation(context))
				.error(R.mipmap.zanwushuju)
				.into(iv_View);

		//头像框
		ImageView kuangView = new ImageView(context);
		RelativeLayout.LayoutParams kuangPama = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context, 55), DensityUtils.dp2px(context, 55));
        kuangView.setBackgroundResource(R.mipmap.touxiangkuang);

		relativeLayout.addView(kuangView,kuangPama); //添加头像框
		relativeLayout.addView(iv_View,ivParams); //添加头像
		return relativeLayout;
	}


	public static void setXingQiu(Context context, int sourceID, RelativeLayout mRvXingXi, int x, int y, String sh_jc,String cws) {
        getImageView(context, sourceID, mRvXingXi, x, y); //添加ImageView
        getTextView(context, sh_jc, mRvXingXi, x, y,cws); //添加TextView
    }

    public static void getImageView(Context context, int sourceID, RelativeLayout mRvXingXi, final int x, final int y) {
        //将ImageView放到线性布局里面
        ImageView iv_View = new ImageView(context);
        iv_View.setBackgroundResource(sourceID);
        RelativeLayout.LayoutParams ivParams = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context,60), DensityUtils.dp2px(context,60));
        ivParams.leftMargin = DensityUtils.dp2px(context, x);
        ivParams.topMargin = DensityUtils.dp2px(context, y);
        mRvXingXi.addView(iv_View, ivParams);

		iv_View.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogUtils.i("点击星球："+x+"__"+y);
			}
		});
    }

    public static void getTextView(Context context, String sh_jc, RelativeLayout mRvXingXi, int x, int y,String cws) {
        //创建一个TextView，用来放商圈名称，将TextView放到LinearLayout中
        TextView tv_View = new TextView(context);
        //设置TextView宽度为100dp
        RelativeLayout.LayoutParams tvParams = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context, 100), ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_View.setText(sh_jc+"-"+cws);
        tv_View.setTextSize(DensityUtils.dp2px(context, 3));
        tv_View.setTextColor(Color.parseColor("#ffffff"));
        tv_View.setGravity(Gravity.CENTER);
        tvParams.leftMargin = DensityUtils.dp2px(context, x - 22);
        tvParams.topMargin = DensityUtils.dp2px(context, y + 58);
        mRvXingXi.addView(tv_View, tvParams);
    }

    //打点后要移动到的位置是星系图中的位置，而实际上scrollBy()/scrollTo()方法移动到的位置是屏幕上的位置，因此需要
	//根据星系图和屏幕的大小比例来计算出具体的scrollBy()/scrollTo()的坐标点
    //设置打点后移动的距离
    //参数：currentX——当前星球所在x坐标   currentY——当前星球所在y坐标
    public static void setDaDianLoaction(final Context context, final CustomScrollView mCsView, final ImageView iv_xing_xi, final int currentX, final int currentY) {
        LogUtils.i("========= setDaDianLoaction =========");
		final int currentX2 = currentX * iv_xing_xi.getWidth() / Word.XING_XI_PX; //根据图片在手机中的宽高获取当前星球在图片中的x轴位置
        final int currentY2 = currentY * iv_xing_xi.getHeight() / Word.XING_XI_PX; //根据图片在手机中的宽高获取当前星球在图片中的y轴位置
//		LogUtils.i("currentX2:"+currentX2);
//		LogUtils.i("currentY2:"+currentY2);

        //设置显示星空图中心位置
        mCsView.post(new Runnable() {
            @Override
            public void run() {
                int scrollX = mCsView.getScrollX();//当前x轴偏移量
                int scrollY = mCsView.getScrollY();//当前y轴偏移量

//				LogUtils.i("当前x轴偏移量:scrollX:"+scrollX);
//				LogUtils.i("当前y轴偏移量:scrollY:"+scrollY);

                //当星系中心位于屏幕中心时的x轴偏移量、y轴偏移量
                int oriX = iv_xing_xi.getWidth() / 2 - ScreenUtils.getScreenWidth(context) / 2;
                int oriY = iv_xing_xi.getHeight() / 2 - ScreenUtils.getScreenHeight(context) / 2;

                LogUtils.i("中心时的x轴偏移量:oriX:" + oriX);
                LogUtils.i("中心时的y轴偏移量:oriY:" + oriY);

                //当星系中心位于屏幕中心时的屏幕中心点坐标
                int screenX = iv_xing_xi.getWidth() / 2;
                int screenY = iv_xing_xi.getHeight() / 2;

                //获取移动星系时屏幕中心的坐标点 (scrollX - oriX + screenX,scrollY - oriY + screenY)
                //星球坐标点距离屏幕中心坐标点的距离： x轴：星球x坐标 - 屏幕中心点x坐标
                //星球坐标点距离屏幕中心坐标点的距离： y轴：星球y坐标 - 屏幕中心点y坐标
                LogUtils.i("屏幕中心坐标点x:" + (scrollX - oriX + screenX));
                LogUtils.i("屏幕中心坐标点y:" + (scrollY - oriY + screenY));

                int moveX = currentX2 - (scrollX - oriX + screenX);
                int moveY = currentY2 - (scrollY - oriY + screenY);

                LogUtils.i("moveX:" + moveX);
                LogUtils.i("moveY:" + moveY);

                mCsView.scrollBy(moveX, moveY);
            }
        });
    }
}
