package cn.see.chat.utils.keyboard.interfaces;

public interface EmoticonClickListener<T> {

    void onEmoticonClick(T t, int actionType, boolean isDelBtn);
}
