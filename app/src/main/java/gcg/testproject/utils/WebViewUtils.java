package gcg.testproject.utils;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 * @ClassName:WebViewUtils

 * @PackageName:gcg.testproject.utils

 * @Create On 2017/12/25   17:18

 * @Site:http://www.handongkeji.com

 * @author:gongchenghao

 * @Copyrights 2017/12/25 handongkeji All rights reserved.
 */


public class WebViewUtils {
    public void setWebView(WebView webView,String string)
    {
        //设置图文详情
        WebSettings localWebSettings = webView.getSettings();
        localWebSettings.setSupportZoom(false); //是否可以支持缩放
        localWebSettings.setBuiltInZoomControls(true);
        localWebSettings.setBlockNetworkImage(false);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//          localWebSettings.setCacheMode(Ca);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        localWebSettings.setPluginState(WebSettings.PluginState.ON);
        localWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setWebViewClient(new MyWebViewClient(webView));
        webView.loadDataWithBaseURL("about:blank", string, "text/html", "utf-8", null);
    }
}