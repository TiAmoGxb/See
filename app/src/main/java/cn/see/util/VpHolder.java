package cn.see.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.ImageAdapter;
import cn.see.fragment.fragmentview.findview.PhotoViewActivity;
import cn.see.main.WebAct;
import cn.see.model.TabBannerModel;
import cn.see.util.constant.BannerConstant;
import cn.see.util.constant.IntentConstant;

/**
 * @日期：2018/6/19
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： banner轮播Holder
 */

public class VpHolder {
    private String url = "http://www.xintusee.com/IOS/Activity/actshare/html?activity_id=";

    //请求更新显示的View
    protected static final int MSG_UPDATE_IMAGE = 1;
    //请求暂停轮播
    protected static final int MSG_KEEP_SILENT = 2;
    //请求恢复轮播
    protected static final int MSG_BREAK_SILENT = 3;
    protected static final int MSG_PAGE_CHANGED = 4;
    //轮播间隔时间
    protected static final long MSG_DELAY =4000;
    private static final String TAG = "imgAdapter";
    private  TextView pTv;
    private ArrayList<ImageView> list_image;
    private LinearLayout linearLayout;
    int lastIndex;
    Context context;
    int currentItem = 0;
    ViewPager mViewPager;
    ImageAdapter imgAdapter;
    String flag;
    private List<TabBannerModel.BannerResult.BannerList> tabBannerList;
    private List<String> urls ;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (handler.hasMessages(MSG_UPDATE_IMAGE)) {
                handler.removeMessages(MSG_UPDATE_IMAGE);
                handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    if (flag.equals(BannerConstant.FIND_TAB_BANNER_TYPE)) {
                        if (tabBannerList.size() > 1) {
                            currentItem++;
                            mViewPager.setCurrentItem(currentItem);
                            handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                        }
                    }else if(flag.equals(BannerConstant.TXT_ARTICL_BANNER_TYPE)){
                        if (urls.size() > 1) {
                            currentItem++;
                            mViewPager.setCurrentItem(currentItem);
                            handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                        }
                    }
                    break;
                case MSG_KEEP_SILENT:
                    //handler.removeMessages(MSG_UPDATE_IMAGE);
                    break;
                case MSG_BREAK_SILENT:
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    };

    public VpHolder(Context context, View convertView, String flag) {
        this.context = context;
        this.flag = flag;
        if (convertView != null) {
            mViewPager = (ViewPager) convertView.findViewById(R.id.viewpager_ad);
            FixedSpeedScroller scroller = new FixedSpeedScroller(context);
            scroller.setScrollDuration(1000);
            scroller.initViewPagerScroll(mViewPager);
            linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout);
            list_image = new ArrayList<ImageView>();
            if(flag.equals(BannerConstant.TXT_ARTICL_BANNER_TYPE)){
                pTv =(TextView) convertView.findViewById(R.id.position_tv);
            }
        }
    }
    public void setViewPager(final List list) {

        if(flag.equals(BannerConstant.FIND_TAB_BANNER_TYPE)){
            this.tabBannerList=list;
        }else if(flag.equals(BannerConstant.TXT_ARTICL_BANNER_TYPE)){
            this.urls = list;
            pTv.setText("1/"+urls.size());
        }
        if (imgAdapter == null) {
            if(list.size()>1){
                for (int i = 0; i < list.size(); i++) {
                    ImageView imageView = new ImageView(context);
                    list_image.add(imageView);
                    ImageView image = new ImageView(context);
                    image.setBackgroundResource(R.drawable.shap_banner_sel);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            25, 25);
                    layoutParams.leftMargin = 25;
                    image.setLayoutParams(layoutParams);
                    linearLayout.addView(image);
                    if (i == 0) {
                        image.setEnabled(true);
                    } else {
                        image.setEnabled(false);
                    }
                }
            }

            if(list.size() == 2){
                list.addAll(list);
            }
            imgAdapter = new ImageAdapter(context, list,flag);
            imgAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if(flag.equals(BannerConstant.FIND_TAB_BANNER_TYPE)){
                        if(UserUtils.userIsLogin(context)){
                            if(tabBannerList.get(position).getType().equals("activity")){
                                Router.newIntent((Activity) context)
                                        .to(WebAct.class)
                                        .putString(IntentConstant.WEB_LOAD_URL,url+tabBannerList.get(position).getType_id()+"&uid="+UserUtils.getUserID(context))
                                        .launch();
                            }
                        }
                    }else if(flag.equals(BannerConstant.TXT_ARTICL_BANNER_TYPE)){
                        Router.newIntent((Activity) context)
                                .to(PhotoViewActivity.class)
                                .putInt(IntentConstant.LOOK_BIG_IMG_INDEX,position)
                                .putSerializable(IntentConstant.LOOK_BIG_IMG_URLS, (Serializable) urls)
                                .launch();
                    }
                }
            });

            mViewPager.setAdapter(imgAdapter);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
                public void onPageSelected(int position) {
                    handler.sendMessage(Message.obtain(handler, MSG_PAGE_CHANGED, position, 0));
                    int index = position % list_image.size();
                    linearLayout.getChildAt(index).setEnabled(true);
                    linearLayout.getChildAt(lastIndex).setEnabled(false);
                    lastIndex = index;
                    if(flag.equals(BannerConstant.TXT_ARTICL_BANNER_TYPE)){
                        pTv.setText((index+1)+"/"+list.size());
                    }
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                    switch (state) {
                        case ViewPager.SCROLL_STATE_DRAGGING:
                            handler.sendEmptyMessage(MSG_KEEP_SILENT);
                            break;
                        case ViewPager.SCROLL_STATE_IDLE:
                            handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                            break;
                        default:
                            break;
                    }
                }
            });
            mViewPager.setCurrentItem(0);
            if(list.size()>1){
                startVp();
            }

        }
    }

    public void stopVp(){
        handler.removeMessages(MSG_UPDATE_IMAGE);
    }

    public void startVp(){
        handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
    }
}
