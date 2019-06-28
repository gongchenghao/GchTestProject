package gcg.testproject.activity.dongTaiDaiLi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Proxy;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.utils.LogUtils;

//动态代理demo演示
public class MyProxyActivity extends AppCompatActivity {

    @Bind(R.id.tv_start)
    TextView mTvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_proxy);
        ButterKnife.bind(this);

        mTvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProxy();
            }
        });
    }

    private void startProxy() {
        Subject man = new Man();
        MyProxy p = new MyProxy(man);

        //获取真是对象的代理对象
        Subject subject = (Subject) Proxy.newProxyInstance(man.getClass().getClassLoader(),
                man.getClass().getInterfaces(), p);

        //通过代理对象调用真是对象相关接口中实现的方法，这个时候就会跳转到这个代理对象相关联的handler的
        //invoke()方法中
        LogUtils.i("调用代理对象的shopping方法");
        subject.shopping(); //虽然表面上是调用真实对象的shopping方法，但其实是调用的myproxy类中的invoke方法
        //获取真实对象的代理对象所对应的class对象的名称
        LogUtils.i("真实对象的class对象的名称："+ subject.getClass().getName().toString());
    }
}
