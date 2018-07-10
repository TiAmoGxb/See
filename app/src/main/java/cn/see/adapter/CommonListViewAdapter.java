package cn.see.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public abstract  class CommonListViewAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> datas;
    private int layoutId;

    public CommonListViewAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
            AutoUtils.autoSize(convertView);
        }
        T t = getItem(position);
        convertView(convertView, t,position);
        return convertView;
    }

    /**
     * 需要去实现的对item中的view的设置操作
     * @param item
     * @param t
     */
    protected abstract void convertView(View item, T t,int position);

}
