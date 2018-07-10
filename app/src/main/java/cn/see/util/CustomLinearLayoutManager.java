package cn.see.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @日期：2018/6/22
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
