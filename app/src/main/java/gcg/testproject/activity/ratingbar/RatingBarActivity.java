package gcg.testproject.activity.ratingbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.widget.MyRatingBar;

public class RatingBarActivity extends BaseActivity {

    @Bind(R.id.ratingbar)
    RatingBar ratingbar;
    @Bind(R.id.star)
    MyRatingBar myRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);
        ButterKnife.bind(this);
        initRatingBar();
        initData();
    }

    private void initRatingBar() {
        ratingbar.setRating(3);
    }

//    自定义ratingbar
    private void initData() {
        myRatingBar.setClickable(false);
        myRatingBar.setStar(2.5f);

        myRatingBar.setClickable(true);
        myRatingBar.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                Log.i("test111","ratingCount:"+ratingCount);
                myRatingBar.setStar(ratingCount);
            }
        });
    }
}
