package cn.see.fragment.fragmentview.findview;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.lookbig.MyImageAdapter;
import cn.see.util.glide.lookbig.PhotoViewPager;

/**
 * 查看大图页
 *
 * PhotoView+ Vp
 */
public class PhotoViewActivity extends BaseActivity {
    int lastIndex;
    private List<String> urls;
    private int index;

    @BindView(R.id.linearLayout)
    AutoLinearLayout linearLayout;

    @BindView(R.id.view_pager_photo)
    PhotoViewPager mViewPager;
    private ArrayList<ImageView> list_image;

    @Override
    public void initView() {
        BaseActivity.setStatusBarTo(getResources().getColor(R.color.black),getWindow());
        list_image  = new ArrayList<>();
    }

    @Override
    public void initAfter() {
        index = getIntent().getIntExtra(IntentConstant.LOOK_BIG_IMG_INDEX, 0);
        urls = (List<String>) getIntent().getSerializableExtra(IntentConstant.LOOK_BIG_IMG_URLS);
        mViewPager.setAdapter(new MyImageAdapter(urls, this));
        mViewPager.setCurrentItem(index, false);
        lastIndex = index;
        //创建指示器
        for (int i = 0; i < urls.size(); i++) {
            ImageView imageView = new ImageView(context);
            list_image.add(imageView);
            ImageView image = new ImageView(context);
            image.setBackgroundResource(R.drawable.shap_photo_bg_sel);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    25, 25);
            layoutParams.leftMargin = 25;
            image.setLayoutParams(layoutParams);
            linearLayout.addView(image);
            if (i == index) {
                image.setEnabled(true);
            } else {
                image.setEnabled(false);
            }
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_photo_view;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int index = position % list_image.size();
                linearLayout.getChildAt(index).setEnabled(true);
                linearLayout.getChildAt(lastIndex).setEnabled(false);
                lastIndex = index;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
