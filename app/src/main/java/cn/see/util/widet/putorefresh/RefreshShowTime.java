package cn.see.util.widet.putorefresh;

import android.text.format.DateUtils;

import cn.see.app.App;

/**
 * @日期：2018/6/6
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class RefreshShowTime {

    public static void showTime(PullToRefreshBase pullToRefreshBase){

        String label = DateUtils.formatDateTime(
                App.getInstance(),
                System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);

        ILoadingLayout loadingLayoutProxy = pullToRefreshBase.getLoadingLayoutProxy(true,false);
        loadingLayoutProxy.setPullLabel("向下拉刷新");// 刚下拉时，显示的提示
        loadingLayoutProxy.setRefreshingLabel("正在刷新数据中...");// 刷新时
        loadingLayoutProxy.setReleaseLabel("松开立即刷新");// 下来达到一定距离时，显示的提示
        // 显示最后更新的时间
        loadingLayoutProxy.setLastUpdatedLabel("最后更新："+label);


        ILoadingLayout upLayoutProxy = pullToRefreshBase.getLoadingLayoutProxy(false,true);
        upLayoutProxy.setPullLabel("向上拉加载更多");// 刚下拉时，显示的提示
        upLayoutProxy.setRefreshingLabel("正在加载更多的数据...");// 刷新时
        upLayoutProxy.setReleaseLabel("松开立即加载更多");// 下来达到一定距离时，显示的提示
    }

}
