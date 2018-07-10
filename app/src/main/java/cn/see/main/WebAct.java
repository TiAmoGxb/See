package cn.see.main;


import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.util.constant.IntentConstant;

/**
 * 通用Web页面
 */
public class WebAct extends BaseActivity {


    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.back_rela)
    RelativeLayout bacRela;
    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @Override
    public void initView() {
        setWebView();
    }

    @Override
    public void initAfter() {
        String url = getIntent().getStringExtra(IntentConstant.WEB_LOAD_URL);
        webView.loadUrl(url);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String t) {
                super.onReceivedTitle(view, t);
                titles.setText(t);
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }

        });
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(!view.getTitle().equals("")){
                    titles.setText(view.getTitle());
                }
                if(webView.canGoBack()) {
                    bacRela.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * web基本设置
     * */
    private void setWebView(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String userAgentString = webSettings.getUserAgentString();
//        webSettings.setUserAgentString(userAgentString+ ConstantsUtils.TYPE);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        //加载图片相关
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        // 使页面支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        //支持自动加载图片
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);// 排版适应屏幕
        // 缩放按钮
        webSettings.setDisplayZoomControls(false);

//        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        webView.addJavascriptInterface(new JsInterface(), ConstantsUtils.JS_TYPE_FLAG);
    }

    /**
     * 对返回键进行处理
     * 如果该网页上层还有网页 则返回上一层
     * 否则退出程序
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if(webView.canGoBack()) {
                webView.goBack();
                bacRela.setVisibility(View.VISIBLE);
                return true;
            } else {
                bacRela.setVisibility(View.GONE);
                onBack();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
