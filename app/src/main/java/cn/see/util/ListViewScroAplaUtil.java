package cn.see.util;

import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

/**
 * @日期：2018/6/26
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： listview滑动监听标题栏透明度
 */

public class ListViewScroAplaUtil implements AbsListView.OnScrollListener {

    private int headerHeight;
    private View layout;
    private TextView title;


    public ListViewScroAplaUtil(int headerHeight, View layout, TextView title) {
        this.headerHeight = headerHeight;
        this.layout = layout;
        this.title = title;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView listview, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0) {
            View view = listview.getChildAt(0);
            if (view != null) {
                int top = -view.getTop();
                headerHeight = view.getHeight();
                if (top <= headerHeight && top >= 0) {
                    title.setVisibility(View.GONE);
                    float f = (float) top / (float) headerHeight;
                    layout.getBackground().mutate().setAlpha((int) (f * 255));
                    // 通知标题栏刷新显示
                    layout.invalidate();
                }else{

                }
            }
        } else if (firstVisibleItem > 0) {
            layout.getBackground().mutate().setAlpha(255);
            title.setVisibility(View.VISIBLE);
        } else {
            layout.getBackground().mutate().setAlpha(0);
            title.setVisibility(View.GONE);
        }
    }
}
