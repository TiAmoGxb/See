package cn.see.util.widet;

import android.content.Context;
import android.util.AttributeSet;

import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * @日期：2018/6/22
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明：
 */

public class AutoXRadioGroup extends XRadioGroup {

    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);

    public AutoXRadioGroup(Context context)
    {
        super(context);
    }

    public AutoXRadioGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AutoXRadioGroup(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (!isInEditMode())
        {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
