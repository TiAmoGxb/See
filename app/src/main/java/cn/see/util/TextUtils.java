package cn.see.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @日期：2018/6/8
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class TextUtils {

    /**
     * 弹框文字含有英文排版问题
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    /**
     * 九大行星 随机地址
     * @return
     */
    public static String getRomArea(){
        List<String> strings = new ArrayList<>();
        strings.add("水星");
        strings.add("金星");
        strings.add("地球村");
        strings.add("火星");
        strings.add("木星");
        strings.add("土星");
        strings.add("天王星");
        strings.add("海王星");
        strings.add("冥王星");
        return strings.get(new Random().nextInt(strings.size()));

    }
}
