package cn.see.main;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.fragment.fragmentview.findview.ActApplyAct;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.fragment.release.ui.BeautifulPictureAct;
import cn.see.util.ShareUtils;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.constant.PreferenceConstant;
import cn.see.util.permosson.CamerUtils;
import cn.see.util.version.PreferenceUtils;
import cn.see.fragment.release.ui.MultiImageSelectorActivity;

/**
 * 通用Web页面
 */
public class WebAct extends BaseActivity {

    private static final String TAG = "WebAct";
    private String actID;

    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.back_rela)
    RelativeLayout bacRela;
    @BindView(R.id.image_rela)
    RelativeLayout layout;
    @BindView(R.id.right_img_top)
    ImageView imageView;
    private String actUrl;
    private String actName;
    private String actCont;

    @OnClick(R.id.image_rela)
    void share(){
        ShareUtils.shareWeb(this, HttpConstant.SHAR_ACTIVITY+actID+"&uid="+ UserUtils.getUserID(this),actName,actCont,actUrl);
    }

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
        String stringExtra = getIntent().getStringExtra(IntentConstant.WEB_ACTIVITY_TYPE);
        if(stringExtra!=null&&stringExtra.equals("act")){
            layout.setVisibility(View.VISIBLE);
            actID = getIntent().getStringExtra(IntentConstant.WEB_ACT_ID);
            actUrl= getIntent().getStringExtra(IntentConstant.WEB_ACT_IMG);
            actName= getIntent().getStringExtra(IntentConstant.WEB_ACT_TITLE);
            actCont = getIntent().getStringExtra(IntentConstant.WEB_ACT_OONT);
            imageView.setImageResource(R.drawable.top_share);
        }
        Log.i(TAG,"URL:"+url);
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
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.addJavascriptInterface(new JsInterface(), HttpConstant.JS_TYPE_FLAG);
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

    /**
     * JS回调
     * */
    public class JsInterface {
        /**
         * 注册
         * @param msg
         */
        @JavascriptInterface
        public void regiSuccess(final String msg) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(msg.equals("success")){
                        onBack();
                    }
                }
            });
        }

        /**
         * 找回密码
         */
        @JavascriptInterface
        public void retrievePassword() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG,"执行找回密码成功");
                   onBack();
                }
            });
        }

        /**
         * 立即参与
         */
        @JavascriptInterface
        public void nowParticipate () {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG,"执行立即参与");
                    PreferenceUtils.setString(WebAct.this, PreferenceConstant.APPLY_ACT,actName+","+actID);
                    CamerUtils.doOpenCamera(WebAct.this, 0, "", IntentConstant.RELEASE_PHOTO_TYPE);
                }
            });
        }
        /**
         * 参与用户
         */
        @JavascriptInterface
        public void particiPatingUser() {
            Router.newIntent(WebAct.this)
                    .to(ActApplyAct.class)
                    .putString(IntentConstant.WEB_ACT_ID,actID)
                    .launch();
        }
        /**
         * 文章
         */
        @JavascriptInterface
        public void applyArticle(final String text_id) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG,"执行文章："+text_id);
                    Router.newIntent(WebAct.this)
                            .putString(IntentConstant.ARTIC_TEXT_ID,text_id)
                            .to(ArticleDetailsAct.class)
                            .launch();
                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode == 0){
                ArrayList<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Router.newIntent(this)
                        .to(BeautifulPictureAct.class)
                        .putString(IntentConstant.RELEASE_TYPE,"text")
                        .putSerializable(IntentConstant.RELEASE_PATHS,pathList)
                        .launch();
            }
        }
    }
}
