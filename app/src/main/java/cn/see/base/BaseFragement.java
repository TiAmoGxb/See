package cn.see.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * @日期：2018/6/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 基类Act XFragment 实现MVP模式绑定
 */

public abstract class BaseFragement<M extends XPresent> extends XFragment<M> implements View.OnClickListener {

    protected final String TAG = this.getClass().getSimpleName();
    private View mContextView = null;
    protected Context mContext = null;

    @Override
    public void initData(Bundle savedInstanceState) {
        mContext = getContext();
        initView();
        initAfter();
        setListener();
    }

    @Override
    public int getLayoutId() {
        return bindLayout();
    }

    @Override
    public M newP() {
        return bindPresent();
    }

    /**
     * [找控件]
     *
     * @return
     */
    public abstract void initView();

    /**
     * [找完控件后执行]
     *
     * @return
     */
    public abstract void initAfter();

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract M bindPresent();

    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View view) {
        if (fastClick())
            widgetClick(view);
    }

    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * [打开新的Act]
     *
     * @return
     */
    public void openActivity(Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(context, targetActivityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> targetActivityClass) {
        openActivity(targetActivityClass, null);
    }

}
