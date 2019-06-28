package gcg.testproject.activity.posui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import gcg.testproject.R;
import gcg.testproject.activity.posui.explosion.ExplosionField;
import gcg.testproject.base.BaseActivity;

public class PoSuiActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po_sui);

        ExplosionField explosionField = new ExplosionField(this);

        explosionField.addListener(findViewById(R.id.root));
    }
}
