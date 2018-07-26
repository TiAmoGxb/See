package cn.see.fragment.fragmentview.newsview;

import android.util.Log;
import android.view.View;

import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.see.R;
import cn.see.base.BaseFragement;

/**
 * @日期：2018/7/2
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 私信Frg
 */

public class PrivateLetterFragment extends BaseFragement {
    @Override
    public void initView() {

    }

    @Override
    public void initAfter() {
        Conversation gxb = Conversation.createSingleConversation("gxb", null);
        List<Conversation> conversationList = JMessageClient.getConversationList();
        Log.i(TAG,"conversationList:"+conversationList);
        if(conversationList!=null){
            for (Conversation c:conversationList) {
                Log.i(TAG,"c:"+c.toString());
            }
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_news_private_fragment;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }
}
