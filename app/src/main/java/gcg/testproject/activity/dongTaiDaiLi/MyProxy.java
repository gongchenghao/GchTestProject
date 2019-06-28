package gcg.testproject.activity.dongTaiDaiLi;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import gcg.testproject.utils.LogUtils;

/**
 * discription:
 * Created by 宫成浩 on 2019/1/17.
 */

public class MyProxy implements InvocationHandler {
    //创建要代理的真实对象
    private Object target;

    public MyProxy(Object target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        LogUtils.i("before.....");
        method.invoke(target,args);
        LogUtils.i("after.....");

        return null;
    }
}
