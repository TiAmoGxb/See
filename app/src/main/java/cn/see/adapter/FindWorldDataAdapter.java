package cn.see.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.fragment.fragmentview.mineview.ArticleDetailsAct;
import cn.see.model.FindWorldTextModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;

/**
 * @日期：2018/6/20
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 发现页 世界书局列表 加载4种不同布局
 */

public class FindWorldDataAdapter extends BaseAdapter {

    //小图拼接的后缀
    private String lafter = "_min";
    private Activity context;
    private List<FindWorldTextModel> result;
    private GlideDownLoadImage instance;
    private LayoutInflater inflater;
    private Router router;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;
    final int TYPE_4 = 3;


    public FindWorldDataAdapter(Activity context, List<FindWorldTextModel> result) {
        this.context = context;
        this.result = result;
        inflater = LayoutInflater.from(context);
        this.instance = GlideDownLoadImage.getInstance();
        this.router = Router.newIntent(context)
                .to(ArticleDetailsAct.class);
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        Holder1 holder1 = null;
        Holder2 holder2 = null;
        Holder3 holder3=null;
        Holder4 holder4=null;
        //优化
        if (convertView == null) {
            //选择某一个样式。。
            switch (type) {
                case TYPE_1:
                    convertView = inflater.inflate(R.layout.layout_find_world_item_one, parent,false);
                    holder1 = new Holder1();
                    holder1.img_one_big = (ImageView) convertView.findViewById(R.id.img_one_big);
                    holder1.img_one_small_1=(ImageView)convertView.findViewById(R.id.img_one_small_1);
                    holder1.img_one_small_2=(ImageView)convertView.findViewById(R.id.img_one_small_2);
                    holder1.img_one_big.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem1().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder1.img_one_small_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem2().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder1.img_one_small_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem3().getText_id())
                                        .launch();
                            }

                        }
                    });
                    instance.loadCircleImageRole(result.get(position).getItem1().getUrl()+lafter,holder1.img_one_big,6);
                    instance.loadCircleImageRole(result.get(position).getItem2().getUrl()+lafter,holder1.img_one_small_1,6);
                    instance.loadCircleImageRole(result.get(position).getItem3().getUrl()+lafter,holder1.img_one_small_2,6);
                    convertView.setTag(holder1);
                    AutoUtils.autoSize(convertView);
                    break;
                case TYPE_2:
                    convertView = inflater.inflate(R.layout.layout_find_world_item_two, parent,false);
                    holder2 = new Holder2();
                    holder2.img_two_1 = (ImageView) convertView.findViewById(R.id.img_two_1);
                    holder2.img_two_2=(ImageView)convertView.findViewById(R.id.img_two_2);
                    holder2.img_two_3=(ImageView)convertView.findViewById(R.id.img_two_3);
                    holder2.img_two_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem1().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder2.img_two_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem2().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder2.img_two_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem3().getText_id())
                                        .launch();
                            }

                        }
                    });
                    instance.loadCircleImageRole(result.get(position).getItem1().getUrl()+lafter,holder2.img_two_1,6);
                    instance.loadCircleImageRole(result.get(position).getItem2().getUrl()+lafter,holder2.img_two_2,6);
                    instance.loadCircleImageRole(result.get(position).getItem3().getUrl()+lafter,holder2.img_two_3,6);
                    convertView.setTag(holder2);
                    AutoUtils.autoSize(convertView);
                    break;
                case TYPE_3:
                    convertView = inflater.inflate(R.layout.layout_find_world_item_three,parent,false);
                    holder3 = new Holder3();
                    holder3.img_three_1 = (ImageView) convertView.findViewById(R.id.img_three_1);
                    holder3.img_three_2=(ImageView)convertView.findViewById(R.id.img_three_2);
                    holder3.img_three_3=(ImageView)convertView.findViewById(R.id.img_three_3);
                    holder3.img_three_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem1().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder3.img_three_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem2().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder3.img_three_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem3().getText_id())
                                        .launch();
                            }

                        }
                    });
                    instance.loadCircleImageRole(result.get(position).getItem1().getUrl()+lafter,holder3.img_three_1,6);
                    instance.loadCircleImageRole(result.get(position).getItem2().getUrl()+lafter,holder3.img_three_2,6);
                    instance.loadCircleImageRole(result.get(position).getItem3().getUrl()+lafter,holder3.img_three_3,6);
                    convertView.setTag(holder3);
                    AutoUtils.autoSize(convertView);
                    break;
                case TYPE_4:
                    convertView = inflater.inflate(R.layout.layout_find_world_item_four, parent,false);
                    holder4 = new Holder4();
                    holder4.img_four_big = (ImageView) convertView.findViewById(R.id.img_four_big);
                    holder4.img_four_small_1=(ImageView)convertView.findViewById(R.id.img_four_small_1);
                    holder4.img_four_small_2=(ImageView)convertView.findViewById(R.id.img_four_small_2);
                    holder4.img_four_big.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem3().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder4.img_four_small_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem1().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder4.img_four_small_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem2().getText_id())
                                        .launch();
                            }

                        }
                    });
                    instance.loadCircleImageRole(result.get(position).getItem3().getUrl()+lafter,holder4.img_four_big,6);
                    instance.loadCircleImageRole(result.get(position).getItem1().getUrl()+lafter,holder4.img_four_small_1,6);
                    instance.loadCircleImageRole(result.get(position).getItem2().getUrl()+lafter,holder4.img_four_small_2,6);
                    convertView.setTag(holder4);
                    AutoUtils.autoSize(convertView);
                    break;

            }
        } else {
            switch (type) {
                case TYPE_1:
                    holder1 = (Holder1) convertView.getTag();
                    instance.loadCircleImageRole(result.get(position).getItem1().getUrl()+lafter,holder1.img_one_big,6);
                    instance.loadCircleImageRole(result.get(position).getItem2().getUrl()+lafter,holder1.img_one_small_1,6);
                    instance.loadCircleImageRole(result.get(position).getItem3().getUrl()+lafter,holder1.img_one_small_2,6);
                    holder1.img_one_big.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem1().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder1.img_one_small_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem2().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder1.img_one_small_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem3().getText_id())
                                        .launch();
                            }

                        }
                    });
                    break;
                case TYPE_2:
                    holder2 = (Holder2) convertView.getTag();
                    instance.loadCircleImageRole(result.get(position).getItem1().getUrl()+lafter,holder2.img_two_1,6);
                    instance.loadCircleImageRole(result.get(position).getItem2().getUrl()+lafter,holder2.img_two_2,6);
                    instance.loadCircleImageRole(result.get(position).getItem3().getUrl()+lafter,holder2.img_two_3,6);
                    holder2.img_two_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem1().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder2.img_two_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem2().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder2.img_two_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem3().getText_id())
                                        .launch();
                            }

                        }
                    });
                    break;
                case TYPE_3:
                    holder3 = (Holder3) convertView.getTag();
                    instance.loadCircleImageRole(result.get(position).getItem1().getUrl()+lafter,holder3.img_three_1,6);
                    instance.loadCircleImageRole(result.get(position).getItem2().getUrl()+lafter,holder3.img_three_2,6);
                    instance.loadCircleImageRole(result.get(position).getItem3().getUrl()+lafter,holder3.img_three_3,6);
                    holder3.img_three_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem1().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder3.img_three_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem2().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder3.img_three_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem3().getText_id())
                                        .launch();
                            }

                        }
                    });
                    break;
                case TYPE_4:
                    holder4 = (Holder4) convertView.getTag();
                    instance.loadCircleImageRole(result.get(position).getItem3().getUrl()+lafter,holder4.img_four_big,6);
                    instance.loadCircleImageRole(result.get(position).getItem1().getUrl()+lafter,holder4.img_four_small_1,6);
                    instance.loadCircleImageRole(result.get(position).getItem2().getUrl()+lafter,holder4.img_four_small_2,6);
                    holder4.img_four_big.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem3().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder4.img_four_small_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem1().getText_id())
                                        .launch();
                            }

                        }
                    });
                    holder4.img_four_small_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(UserUtils.getLogin(context)){
                                router.putString(IntentConstant.ARTIC_TEXT_ID,result.get(position).getItem2().getText_id())
                                        .launch();
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return result.get(position).getType();
    }

    class Holder1{
        ImageView img_one_big,img_one_small_1,img_one_small_2;
    }
    class Holder2{
        ImageView img_two_1,img_two_2,img_two_3;
    }
    class Holder3{
        ImageView img_three_1,img_three_2,img_three_3;
    }
    class Holder4{
        ImageView img_four_big,img_four_small_1,img_four_small_2;
    }
}
