package cn.see.views;

import android.app.Activity;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.IView;
import cn.see.model.TxtModel;
import cn.see.presenter.homep.FriendRecoPresenter;

/**
 * @日期：2018/6/29
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 该V对应好友推荐 和 附近用户推荐
 */

public interface FriendsAndNearbyV extends IView<FriendRecoPresenter> {

    /**
     * 数据返回
     * @param result
     * @param page
     */
    void responseData(List<TxtModel.TxtResult.Result> result, int page,String num);

    /**
     * 隐藏加载框
     */
    void hidProgress();

    /**
     * 显示加载框
     */
    void showProgerss();

}
