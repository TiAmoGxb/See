package cn.see.chat.utils.keyboard.interfaces;

import android.view.ViewGroup;

import cn.see.chat.utils.keyboard.adpater.EmoticonsAdapter;

public interface EmoticonDisplayListener<T> {

    void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, T t, boolean isDelBtn);
}
