package cn.see.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseModel;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.fragment.fragmentview.mineview.OtherMainAct;
import cn.see.fragment.fragmentview.mineview.ReviewAct;
import cn.see.model.TextReviewModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.http.OkHttpUtils;

/**
 * @日期：2018/6/21
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 评论适配器
 */

public class TextReviewAdapter extends BaseAdapter {

    private Activity context;
    private List<TextReviewModel.ReviewResult.ReviewList> reviewListList;
    private int type;//该type区分是要查看全部评论1 还是最多三条 0：三条 1：全部
    //所属文章ID 评论点赞用到
    private String text_id;

    public TextReviewAdapter(Activity context, List<TextReviewModel.ReviewResult.ReviewList> reviewListList,int type,String text_id) {
        this.context = context;
        this.type = type;
        this.reviewListList = reviewListList;
        this.text_id = text_id;
    }

    @Override
    public int getCount() {
        if(type == 0){
            return reviewListList.size()>=3?3:reviewListList.size();
        }else{
            return reviewListList.size();
        }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ReviewHoler holer = null;
        if(convertView == null){
            holer = new ReviewHoler();
            convertView = View.inflate(context, R.layout.layout_text_review_item,null);
            holer.userImg =(ImageView) convertView.findViewById(R.id.r_user_img);
            holer.likeImg =(ImageView) convertView.findViewById(R.id.like_img);
            holer.userName =(TextView) convertView.findViewById(R.id.r_user_name);
            holer.likeNum =(TextView) convertView.findViewById(R.id.like_num);
            holer.content =(TextView) convertView.findViewById(R.id.r_content);
            holer.content.setMovementMethod(LinkMovementMethod.getInstance());
            holer.usertime =(TextView) convertView.findViewById(R.id.r_user_time);
            convertView.setTag(holer);
        }else{
            holer = (ReviewHoler) convertView.getTag();
        }
        Log.i("TextReviewAdapter","like:"+reviewListList.get(position).getLike_status());

        if(reviewListList.get(position).getLike_status().equals("1")||reviewListList.get(position).getLike_status().equals("2")){
            holer.likeImg.setImageResource(R.mipmap.zan_yes);
        }else{
            holer.likeImg.setImageResource(R.mipmap.zan_no);
        }

        //如果是回复他人
        if(reviewListList.get(position).getTo_nickname()!=null&&!reviewListList.get(position).getTo_id().equals("0")){
            holer.content.setText(getClickableSpan("回复@"+reviewListList.get(position).getTo_nickname()+"："+reviewListList.get(position).getContent(),reviewListList.get(position).getTo_id(),reviewListList.get(position).getTo_nickname().length()));
        }else{
            holer.content.setText(reviewListList.get(position).getContent());
        }
        holer.userName.setText(reviewListList.get(position).getNickname());
        holer.likeNum.setText(reviewListList.get(position).getLike_count());
        holer.usertime.setText(reviewListList.get(position).getCreate_time_info());
        GlideDownLoadImage.getInstance().loadCircleImage(reviewListList.get(position).getUrl(),holer.userImg);
        holer.userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.newIntent(context)
                        .to(OtherMainAct.class)
                        .putString(IntentConstant.OTHER_USER_ID,reviewListList.get(position).getUser_id())
                        .launch();
            }
        });


        convertView.findViewById(R.id.com_rela).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 1){
                    ReviewAct act = (ReviewAct)context;
                    act.reviewItem(position);
                }else{
                    ArticleDetailsAct act = (ArticleDetailsAct)context;
                    act.reviewItem(position);
                }

            }
        });

        convertView.findViewById(R.id.like_rela).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<OkHttpUtils.Param> paramList = new ArrayList<>();
                paramList.add(new OkHttpUtils.Param("from_id",UserUtils.getUserID(context)));
                paramList.add(new OkHttpUtils.Param("review_id",reviewListList.get(position).getReview_id()));
                paramList.add(new OkHttpUtils.Param("text_id",text_id));
                OkHttpUtils.ResultCallback<BaseModel> loadNewsCallback = new OkHttpUtils.ResultCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel response) {
                        ToastUtil.showToast(response.getErrorMsg());
                        if(!response.isError()){
                            TextReviewModel.ReviewResult.ReviewList reviewList = reviewListList.get(position);
                            String like_count =reviewList .getLike_count();
                            int i = Integer.parseInt(like_count);
                            reviewList.setLike_count((i+1)+"");
                            reviewList.setLike_status("1");
                            notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.i("TextReviewAdapter","e:"+e.getMessage());
                    }
                };
                OkHttpUtils.post(Api.BASE_PATH+"Text/like.html",loadNewsCallback,paramList);
            }
        });

        return convertView;
    }

    class ReviewHoler{
        ImageView userImg;
        ImageView likeImg;
        TextView userName;
        TextView content;
        TextView likeNum;
        TextView usertime;
    }


    /**
     * 富文本变化 点击回复的用户名跳转到对应主页
     * @param item
     * @param toid
     * @param length
     * @return
     */
    public SpannableString getClickableSpan(final String item, final String toid,int length) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Router.newIntent(context)
                        .putString(IntentConstant.OTHER_USER_ID,toid)
                        .to(OtherMainAct.class)
                        .launch();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(R.color.text_3f));
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(span, 2, (length+4), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return string;
    }

}
