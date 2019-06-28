package gcg.testproject.activity.shanxingmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rance.library.ButtonData;
import com.rance.library.ButtonEventListener;
import com.rance.library.SectorMenuButton;

import java.util.ArrayList;
import java.util.List;

import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.LogUtils;

//注意：要在app的build.gradle中加入以下两句代码，否则会报错
//RenderScript向下兼容
// renderscriptTargetApi 18
// renderscriptSupportModeEnabled true
public class ShanXingMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shan_xing_menu);
        initBottomSectorMenuButton();
        initTopSectorMenuButton();
        initRightSectorMenuButton();
        initCenterSectorMenuButton();
    }

    //左上角
    private void initTopSectorMenuButton() {
        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.top_sector_menu);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.mipmap.like, R.mipmap.mark,
                R.mipmap.search, R.mipmap.copy};
        for (int i = 0; i < 4; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonData.setBackgroundColorId(this, R.color.colorAccent);
            buttonDatas.add(buttonData);
        }
        sectorMenuButton.setButtonDatas(buttonDatas);
        setListener(sectorMenuButton);
    }

    //右上角
    private void initRightSectorMenuButton() {
        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.right_sector_menu);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.mipmap.like, R.mipmap.mark,
                R.mipmap.search, R.mipmap.copy};
        for (int i = 0; i < 4; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonData.setBackgroundColorId(this, R.color.colorAccent);
            buttonDatas.add(buttonData);
        }
        sectorMenuButton.setButtonDatas(buttonDatas);
        setListener(sectorMenuButton);
    }

    //中间
    private void initCenterSectorMenuButton() {
        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.center_sector_menu);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.mipmap.like, R.mipmap.mark,
                R.mipmap.search, R.mipmap.copy, R.mipmap.settings,
                R.mipmap.heart, R.mipmap.info, R.mipmap.record,
                R.mipmap.refresh};
        for (int i = 0; i < 9; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonData.setBackgroundColorId(this, R.color.colorAccent);
            buttonDatas.add(buttonData);
        }
        sectorMenuButton.setButtonDatas(buttonDatas);
        setListener(sectorMenuButton);
    }

    //底部
    private void initBottomSectorMenuButton() {
        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.bottom_sector_menu);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.mipmap.like, R.mipmap.mark,
                R.mipmap.search, R.mipmap.copy};
        for (int i = 0; i < 4; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonData.setBackgroundColorId(this, R.color.colorAccent);
            buttonDatas.add(buttonData);
        }
        sectorMenuButton.setButtonDatas(buttonDatas);
        setListener(sectorMenuButton);
    }

    private void setListener(final SectorMenuButton button) {
        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                LogUtils.i("点击的位置："+index);
                showToast("button" + index);
            }

            @Override
            public void onExpand() {
                LogUtils.i("onExpand");
                showToast("onExpand");
            }

            @Override
            public void onCollapse() {
                LogUtils.i("onCollapse");
                showToast("onCollapse");
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(ShanXingMenuActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
