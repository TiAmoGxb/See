package cn.see.chat.activity.receiptmessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import cn.jpush.im.android.api.model.UserInfo;
import cn.see.R;
import cn.see.app.App;
import cn.see.chat.activity.FriendInfoActivity;
import cn.see.chat.activity.GroupNotFriendActivity;
import cn.see.chat.activity.fragment.BaseFragment;

/**
 * Created by ${chenyn} on 2017/9/5.
 */

public class MessageAlreadyReadFragment extends BaseFragment {
    private Activity mContext;
    private View mRootView;
    private ListView mReceipt_alreadyRead;
    private AlreadyReadAdapter mAdapter;
    private long mGroupId;

    public MessageAlreadyReadFragment(long groupIdForReceipt) {
        this.mGroupId = groupIdForReceipt;
    }
    public MessageAlreadyReadFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();

        initView();
        initListViewClick();
    }

    private void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.fragment_receipt_already_read,
                (ViewGroup) mContext.findViewById(R.id.main_view), false);
        mReceipt_alreadyRead = (ListView) mRootView.findViewById(R.id.receipt_alreadyRead);
        mAdapter = new AlreadyReadAdapter(this);
        mReceipt_alreadyRead.setAdapter(mAdapter);
    }

    private void initListViewClick() {
        mReceipt_alreadyRead.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserInfo userInfo = (UserInfo) parent.getItemAtPosition(position);
                Intent intent = new Intent();
                if (userInfo.isFriend()) {
                    intent.setClass(mContext, FriendInfoActivity.class);
                }else {
                    intent.setClass(mContext, GroupNotFriendActivity.class);
                }
                intent.putExtra(App.TARGET_ID, userInfo.getUserName());
                intent.putExtra(App.TARGET_APP_KEY, userInfo.getAppKey());
                intent.putExtra(App.GROUP_ID, mGroupId);
                startActivity(intent);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mRootView;
    }
}
