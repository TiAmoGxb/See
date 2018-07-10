package cn.see.adapter;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.model.TxtModel;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;

/**
 * @日期：2018/7/2
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class NesLikeReviewAdapter extends BaseAdapter {

    private static final String TAG =  "NesLikeReviewAdapter";
    private List<TxtModel.TxtResult.Result> list;
    private Activity activity;
    private int type ;

    public NesLikeReviewAdapter(List<TxtModel.TxtResult.Result> list, Activity activity,int type) {
        this.list = list;
        this.activity = activity;
        this.type = type;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(activity, R.layout.layout_news_like_review_item,null);

            holder.userImg =(ImageView) convertView.findViewById(R.id.user_img);
            holder.thubImg =(ImageView) convertView.findViewById(R.id.text_thmb);
            holder.userName = (TextView) convertView.findViewById(R.id.user_name);
            holder.context = (TextView) convertView.findViewById(R.id.cont_txt);
            holder.context.setMovementMethod(LinkMovementMethod.getInstance());
            holder.msgText = (TextView) convertView.findViewById(R.id.text_cont);
            holder.textNickName = (TextView) convertView.findViewById(R.id.text_name);
            holder.timeText = (TextView) convertView.findViewById(R.id.time);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final TxtModel.TxtResult.Result result = list.get(position);
        Log.i(TAG,"cont:"+result.getContent());
        if(type == 0){
            holder.context.setVisibility(View.GONE);
        }else{
            holder.context.setVisibility(View.VISIBLE);
            if(!result.getTo_nickname().equals("")&&!result.getTo_id().equals("0")){
                holder.context.setText(getClickableSpan("回复@"+result.getTo_nickname()+"："+result.getContent(),result.getTo_id(),result.getTo_nickname().length()));
            }else{
                holder.context.setText(result.getContent());
            }
        }
        GlideDownLoadImage.getInstance().loadCircleImage(result.getHead_img_url(),holder.userImg);
        GlideDownLoadImage.getInstance().loadCircleImageRole(result.getText_url(),holder.thubImg,4);
        holder.userName.setText(result.getNickname());
        holder.textNickName.setText(result.getText_nickname());
        holder.msgText.setText(result.getMsg());
        holder.timeText.setText(result.getCreate_time());
        holder.userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newIntent(activity)
                        .putString(IntentConstant.OTHER_USER_ID,result.getFrom_id())
                        .to(OtherMainAct.class)
                        .launch();
            }
        });
        convertView.findViewById(R.id.parent_rela).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newIntent(activity)
                        .putString(IntentConstant.ARTIC_TEXT_ID,result.getText_id())
                        .to(ArticleDetailsAct.class)
                        .launch();
            }
        });

        return convertView;
    }

    public SpannableString getClickableSpan(final String item, final String toid, int length) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Router.newIntent(activity)
                        .putString(IntentConstant.OTHER_USER_ID,toid)
                        .to(OtherMainAct.class)
                        .launch();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(activity.getResources().getColor(R.color.text_3f));
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(span, 2, (length+4), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return string;
    }


    class ViewHolder{

        ImageView userImg;
        ImageView thubImg;
        TextView userName;
        TextView context;
        TextView textNickName;
        TextView msgText;
        TextView timeText;
    }
}
