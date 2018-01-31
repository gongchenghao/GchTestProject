package gcg.testproject.activity.sanji;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lljjcoder.citypickerview.widget.CityPicker;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.LogUtils;

/**
 * 省市区三级联动
 *
 * @ClassName:SanJiActivity
 * @PackageName:gcg.testproject.activity.sanji
 * @Create On 2018/1/23   9:19
 * @Site:http://www.handongkeji.com
 * @author:gongchenghao
 * @Copyrights 2018/1/23 handongkeji All rights reserved.
 *
 *
 * 注意：这两种三级联动均依赖于：compile 'liji.library.dev:citypickerview:0.7.0'
 * 如要修改UI，可以修改tv_sanji2中的
 */

public class SanJiActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.tv_sanji1)
    TextView tvSanji1;
    @Bind(R.id.tv_sanji2)
    TextView tvSanji2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_ji);
        ButterKnife.bind(this);
        initClick(); //初始化点击事件
    }

    private void initClick() {
        tvSanji1.setOnClickListener(this);
        tvSanji2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_sanji1:
                selectAddress();
                break;
            case R.id.tv_sanji2:
                mySelectAddress();
                break;
        }
    }

    private void mySelectAddress() {
        MyCityPicker myCityPicker = new MyCityPicker.Builder(SanJiActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#4caf65") //设置标题颜色
                .confirTextColor("#696969") //“确定”的字体颜色
                .cancelTextColor("#696969") //“取消”的字体颜色
                .province("江苏省") //初始化显示的省份
                .city("常州市") //初始化显示的市
                .district("天宁区") //初始化显示的区
                .textColor(Color.parseColor("#000000")) //省市区的字的颜色
                .provinceCyclic(true) //省份可以循环
                .cityCyclic(false) //市不能循环
                .districtCyclic(false) //区不能循环
                .visibleItemsCount(7) //可见的条目数量
                .itemPadding(10) //条目间距
                .onlyShowProvinceAndCity(false) //是否只显示省和市
                .build();
        myCityPicker.show();
        //监听方法，获取选择结果
        myCityPicker.setOnCityItemClickListener(new MyCityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                LogUtils.i("MyCityPicker::::"+province.trim() + "-" + city.trim() + "-" + district.trim()+"-"+code);
                //为TextView赋值
                tvSanji2.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(SanJiActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
//                .titleTextColor("#696969")
                .confirTextColor("#696969") //“确定”的字体颜色
                .cancelTextColor("#696969") //“取消”的字体颜色
                .province("江苏省") //初始化显示的省份
                .city("常州市") //初始化显示的市
                .district("天宁区") //初始化显示的区
                .textColor(Color.parseColor("#000000")) //省市区的字的颜色
                .provinceCyclic(true) //省份可以循环
                .cityCyclic(false) //市不能循环
                .districtCyclic(false) //区不能循环
                .visibleItemsCount(7) //可见的条目数量
                .itemPadding(10) //条目间距
                .onlyShowProvinceAndCity(false) //是否只显示省和市
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                LogUtils.i(province.trim() + "-" + city.trim() + "-" + district.trim()+"-"+code);
                //为TextView赋值
                tvSanji1.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }
}
