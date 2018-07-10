package cn.see.fragment.release.bean;

/**
 * @日期：2018/7/9
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class BeauBean {

    private  int img;
    private String text;

    public BeauBean(int img, String text) {
        this.img = img;
        this.text = text;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public String getText() {
        return text;
    }
}
