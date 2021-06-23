package com.saimawzc.shipper.ui.order.creatorder.richtext;



import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.saimawzc.shipper.R;
import com.saimawzc.shipper.base.BaseActivity;
import com.saimawzc.shipper.weight.utils.richtext.MyJavaScripteInterface;
import com.saimawzc.shipper.weight.utils.richtext.utils.RichUtils;
import java.util.ArrayList;
import butterknife.BindView;

/**
 * Created by leo
 * on 2020/9/21.
 */
public class ShowArtActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /****全屏播放视频***/
    @BindView(R.id.video_view)
    FrameLayout videoview;
    private WebChromeClient.CustomViewCallback  xCustomViewCallback;
    private View xCustomView;
    private Boolean islandport = true;//true表示此时是竖屏，false表示此时横屏。
    private MyWebChromeClient xwebchromeclient;

    @Override
    protected int getViewId() {
        return R.layout.activityshow_webview;
    }

    @Override
    protected void init() {
        setToolbar(toolbar,"安全公告");
        initWebView(getIntent().getStringExtra("data"));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onGetBundle(Bundle bundle) {

    }
    public void initWebView(String data) {

        WebSettings settings = webView.getSettings();
        //settings.setUseWideViewPort(true);//调整到适合webview的大小，不过尽量不要用，有些手机有问题
        settings.setLoadWithOverviewMode(true);//设置WebView是否使用预览模式加载界面。
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        webView.setVerticalScrollBarEnabled(false);//不能垂直滑动
        webView.setHorizontalScrollBarEnabled(false);//不能水平滑动
        settings.setTextSize(WebSettings.TextSize.NORMAL);//通过设置WebSettings，改变HTML中文字的大小
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webView.addJavascriptInterface(new MyJavaScripteInterface(this),"imagelistener");
        //设置WebView属性，能够执行Javascript脚本
       webView.getSettings().setJavaScriptEnabled(true);//设置js可用

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
       webView.setWebViewClient(webViewClient);
        xwebchromeclient= new MyWebChromeClient();
        webView.setWebChromeClient(xwebchromeclient);
        data = "</Div><head><style>body{font-size:16px}</style>" +
                "<style>img{ width:100% !important;margin-top:0.4em;margin-bottom:0.4em}</style>" +
                "<style>ul{ padding-left: 1em;margin-top:0em}</style>" +
                "<style>ol{ padding-left: 1.2em;margin-top:0em}</style>" +
                "</head>" + data;

        ArrayList<String> arrayList = RichUtils.returnImageUrlsFromHtml(data);
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (!arrayList.get(i).contains("http")) {
                    //如果不是http,那么就是本地绝对路径，要加上file
                    data = data.replace(arrayList.get(i), "file://" + arrayList.get(i));
                }
            }
        }

       webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            addImageClickListener(view);//待网页加载完全后设置图片点击的监听方法
            //addImageClickListenerVideo(view);
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            return super.shouldOverrideUrlLoading(view, request);
        }


    };
    private void addImageClickListener1(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                " var array=new Array(); " +
                " for(var j=0;j<objs.length;j++){ array[j]=objs[j].src; }"+//这个循环将所图片放入数组，当js调用本地方法时传入。当然如果采用方式一获取图片的话，本地方法可以不需要传入这个数组
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistener.openImage(this.src,array);  " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
                "    }  " +
                "}" +
                "})()");
    }
    private void addImageClickListener(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistener.openImage(this.src);  " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
                "    }  " +
                "}" +
                "})()");
    }

    private void addImageClickListenerVideo(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"video\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistener.openVideo(this.src);  " +//通过js代码找到标签为img的代码块，设置点击的监听方法与本地的openImage方法进行连接
                "    }  " +
                "}" +
                "})()");
    }

    class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            //如果一个视图已经存在，那么立刻终止并新建一个
            webView.setVisibility(View.GONE);
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            videoview.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            videoview.setVisibility(View.VISIBLE);
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            if (xCustomView == null)//不是全屏播放状态
                return;
            // Hide the custom view.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);
            // Remove the custom view from its container.
            videoview.removeView(xCustomView);
            xCustomView = null;
            videoview.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();

            webView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 判断是否是全屏
     * @return
     */
    public boolean inCustomView() {
        return (xCustomView != null);
    }
    /**
     * 全屏时按返加键执行退出全屏方法
     */
    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
    }
}
