package cn.see.util.widet;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * @日期：2018/7/11
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class CustomGridViewLayoutManager extends GridLayoutManager {

    private boolean isScrollEnabled = true;

    public CustomGridViewLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomGridViewLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CustomGridViewLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {

        return isScrollEnabled && super.canScrollVertically();
    }
}
