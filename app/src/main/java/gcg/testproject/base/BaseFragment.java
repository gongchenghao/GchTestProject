package gcg.testproject.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import gcg.testproject.common.MyApp;
import gcg.testproject.dialog.ProgressDialog;
import gcg.testproject.utils.ToastUtils;


/**
 * Created by Administrator on 2017/3/2 0002.
 */

public class BaseFragment extends Fragment {

    public Context mContext;
    public MyApp myApp;
    public ProgressDialog progressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        myApp = MyApp.getInstance();
    }

    public void dialogShow(String message) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMsg(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dialogShow() {
        dialogShow("加载中...");
    }

    public void dialogDismiss() {
        progressDialog.dismiss();
    }
    public void dialogComplete(ProgressDialog.OnCompleteListener listener, String complMessage) {
        progressDialog.complete(listener, complMessage);
    }
}
