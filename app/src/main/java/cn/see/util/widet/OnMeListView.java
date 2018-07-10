package cn.see.util.widet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * @日期：2018/6/7
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 解决滑动冲突 item显示不全
 */

public class OnMeListView extends ListView {

    public OnMeListView(Context context) {
        super(context);
    }

    public OnMeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnMeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
