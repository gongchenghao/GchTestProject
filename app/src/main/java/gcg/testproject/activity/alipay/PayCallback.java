package gcg.testproject.activity.alipay;

import android.database.DataSetObservable;
import android.database.DataSetObserver;

/**
 * Created by Administrator on 2017/4/24 0024.
 */

public class PayCallback {
    private static final PayCallback ourInstance = new PayCallback();
    private DataSetObservable observable = new DataSetObservable();


    public static PayCallback getInstance() {
        return ourInstance;
    }

    private PayCallback() {
    }


    public void registerPayCallback(DataSetObserver observer){
        observable.registerObserver(observer);
    }

    public void unregisterPayCallback(DataSetObserver observer){
        observable.unregisterObserver(observer);
    }

    public void notifyPaySuccess(){
        observable.notifyChanged();
    }

    public void notifyPayFailed(){
        observable.notifyInvalidated();
    }
}
