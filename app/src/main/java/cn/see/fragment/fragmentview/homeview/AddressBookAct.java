package cn.see.fragment.fragmentview.homeview;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseActivity;
import cn.see.util.ToastUtil;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.permosson.RequestCode;
import cn.see.util.permosson.XPermissionUtils;
import cn.see.util.widet.CustomProgress;

/**
 * 通讯录
 */
public class AddressBookAct extends BaseActivity {

    private static final String TAG = "AddressBookAct" ;
    @BindView(R.id.book_recy)
    RecyclerView recyclerView;
    @BindView(R.id.title_tv_base)
    TextView title;
    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }


    @Override
    public void initView() {
        title.setText("通讯录好友");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }




    @Override
    public void initAfter() {

        XPermissionUtils.requestPermissions(this, RequestCode.BOOK, new String[]{Manifest.permission.READ_CONTACTS}, new XPermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                final CustomProgress progress = CustomProgress.show(AddressBookAct.this);
                //开启子线程查询 防止联系人过多 UI线程阻塞
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<String> query = query();
                        Log.i(TAG,"执行完");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(new RecryCommonAdapter<String>(AddressBookAct.this,R.layout.layout_add_r_item,query) {
                                    @Override
                                    protected void convert(ViewHolder holder, String s, int position) {
                                        holder.setText(R.id.nick_name,s);
                                        holder.setText(R.id.send_tv,"邀请");
                                        ImageView imageView = holder.getView(R.id.img_icon);
                                        GlideDownLoadImage.getInstance().loadCircleImage(AddressBookAct.this,R.drawable.bac_image,imageView);
                                        holder.setOnClickListener(R.id.send_tv, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ToastUtil.showToast("发送成功");
                                            }
                                        });
                                    }
                                });
                                progress.dismiss();
                            }
                        });
                    }
                }).start();


            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions, boolean alwaysDenied) {
            }
        });


    }

    @Override
    public int bindLayout() {
        return R.layout.activity_address_book;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }


    private List<String> query(){
        List<String> strings = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            // 联系人的名字
            String display_name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            // 联系人的唯一id
            String _id = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            // hasPhoneNumber 是否有电话号码
            String has_phone_number = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            // 根据之前查询出来的_id作为判断条件进行查询
            Cursor phoneCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                    new String[] { _id }, null);
            // 根据_id查询到对应的电话号码
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor
                        .getString(phoneCursor
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Log.i(TAG, "display_name" + display_name + "phoneNumber"
                        + phoneNumber);
                strings.add(display_name);
            }
        }
        Log.i(TAG, "查询完毕");
        return strings;
    }




}
