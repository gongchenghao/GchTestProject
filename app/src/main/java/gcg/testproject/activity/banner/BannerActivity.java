package gcg.testproject.activity.banner;

import android.os.Bundle;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;

public class BannerActivity extends BaseActivity {

    @Bind(R.id.my_banner)
    BannerViewPager myBanner;


    private String[] imageUrls = {"http://app.biodog.cn/wlyyuploadmgr/common/2017-02-07/FF1486445677555_mid.jpg",
                                   "http://app.biodog.cn/wlyyuploadmgr/common/2017-02-07/MV1486445681914_mid.jpg"};
    private ArrayList<String> infos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);

        initData();

        LunBoUtils lunBoUtils = new LunBoUtils(myBanner,BannerActivity.this,infos);
        lunBoUtils.setLunBo();
    }

    private void initData() {
        for (int i = 0; i < imageUrls.length; i++) {
            infos.add(imageUrls[i]);
        }
    }
}
