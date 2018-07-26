package cn.see.presenter.minep;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import java.util.List;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.CommonListViewAdapter;
import cn.see.adapter.CommonViewHolder;
import cn.see.fragment.fragmentview.mineview.ChangeBacAct;
import cn.see.util.glide.GlideDownLoadImage;
import me.nereo.multi_image_selector.bean.Image;

/**
 * @日期：2018/7/26
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 更改个人背景P
 */

public class ChangeBacPresenter extends XPresent<ChangeBacAct> {
    public CommonListViewAdapter<Integer> initAdapter(List<Integer> bitmapList){
        CommonListViewAdapter<Integer> adapter = new CommonListViewAdapter<Integer>(getV(),bitmapList, R.layout.layout_change_bac_item) {
            @Override
            protected void convertView(View item, Integer bitmap, int position) {
                ImageView imageView = CommonViewHolder.get(item, R.id.img);
                RelativeLayout layout = CommonViewHolder.get(item, R.id.up_rela);
                if(position == 0){
                    layout.setVisibility(View.VISIBLE);
                }else{
                    layout.setVisibility(View.GONE);
                }
                GlideDownLoadImage.getInstance().loadCircleImageRole(bitmap,imageView,10);

            }
        };

        return adapter;
    }


}
