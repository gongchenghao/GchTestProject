package gcg.testproject.utils;

import android.content.Context;
import android.content.Intent;


/**
 * Created by ${xuchuanting} on 2016/11/24 0024.
 */
public class MoveUtils {

    /**
     * 跳转Activity
     *
     * @param context
     * @param clazz
     */
    public static void go(Context context, Class clazz) {
        context.startActivity(new Intent(context, clazz));
    }
}
