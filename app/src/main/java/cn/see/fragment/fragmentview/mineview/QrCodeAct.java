package cn.see.fragment.fragmentview.mineview;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.model.QrModel;
import cn.see.presenter.minep.QrCodePresenter;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 二维码
 */
public class QrCodeAct extends BaseActivity<QrCodePresenter> {

    private GlideDownLoadImage instance;
    private String qrUrl;

    @BindView(R.id.user_img)
    ImageView userImg;
    @BindView(R.id.qr_img)
    ImageView qrImg;
    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.title_tv_op)
    TextView opTv;
    @BindView(R.id.tv_name)
    TextView nameTv;
    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }
    @OnClick(R.id.title_tv_op)
    void saveImg(){
        saveQrImg();
    }

    @Override
    public void initView() {
        title.setText("二维码");
        opTv.setText("保存到相册");
        opTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void initAfter() {
        instance = GlideDownLoadImage.getInstance();
        String user_id = getIntent().getStringExtra(IntentConstant.USER_ID_QRCODE);
        getP().getUserQrCode(user_id);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_qr_code;
    }

    @Override
    public QrCodePresenter bindPresent() {
        return new QrCodePresenter();
    }

    @Override
    public void setListener() {

    }

    /**
     * 二维码获取
     * @param qrResult
     */
    public void codeResponse(QrModel.QrResult qrResult){
        qrUrl = qrResult.getHead_img_url();
        nameTv.setText(qrResult.getNickname()+"·扫一扫关注我");
        instance.loadCircleImage(qrResult.getHead_img_url(),userImg);
        instance.loadImage(qrResult.getQR_url(),qrImg);
    }

    /**
     * 未用！
     */
    private void saveQrImg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imagePath = instance.getImagePath(qrUrl);
                instance.copyFile(imagePath);
            }
        }).start();
    }
}
