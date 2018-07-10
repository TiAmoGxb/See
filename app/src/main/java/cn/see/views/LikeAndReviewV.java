package cn.see.views;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;
import cn.see.model.TxtModel;
import cn.see.presenter.homep.FriendRecoPresenter;
import cn.see.presenter.newsp.NewsLikeAndPresenter;

/**
 * @日期：2018/6/29
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 该V对应
 */

public interface LikeAndReviewV extends IView<NewsLikeAndPresenter> {


    /**
     * 数据返回
     * @param result
     */
    void responseData(List<TxtModel.TxtResult.Result> result, int total);

    /**
     * 隐藏加载框
     */
    void hidProgress();

    /**
     * 显示加载框
     */
    void showProgerss();

}
