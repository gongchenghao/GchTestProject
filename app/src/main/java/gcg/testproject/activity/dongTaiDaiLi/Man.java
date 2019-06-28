package gcg.testproject.activity.dongTaiDaiLi;

import gcg.testproject.utils.LogUtils;

/**
 * discription:
 * Created by 宫成浩 on 2019/1/17.
 */

public class Man implements Subject{

    @Override
    public void shopping() {
        LogUtils.i("man 类执行shopping()方法");
    }
}
