package cn.see.fragment.fragmentview.newsview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseFragement;

/**
 * @日期：2018/7/2
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 私信Frg
 */

public class PrivateLetterFragment extends BaseFragement {

    private List<Conversation> conversationLists = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void initAfter() {
        List<Conversation> conversationList = JMessageClient.getConversationList();
        Log.i(TAG,"conversationList:"+conversationList);
        if(conversationList!=null){
            conversationLists.addAll(conversationList);
            RecryCommonAdapter<Conversation> adapter = new RecryCommonAdapter<Conversation>(getActivity(),R.layout.layout_private_msg_item,conversationLists) {
                @Override
                protected void convert(ViewHolder holder, Conversation conversation, int position) {
                    UserInfo targetInfo = (UserInfo) conversation.getTargetInfo();
                    Log.i(TAG,"targetInfol:"+targetInfo);
                    holder.setText(R.id.name,targetInfo.getNickname());
                }
            };
            recyclerView.setAdapter(adapter);
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
