package cn.see.fragment.fragmentview.mineview;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.base.BaseActivity;
import cn.see.base.BaseModel;
import cn.see.model.UserInfoModel;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.glide.GlideDownLoadImage;
import cn.see.util.http.Api;
import cn.see.util.permosson.CamerUtils;
import cn.see.util.widet.AlertView.AlertView;
import cn.see.util.widet.AlertView.OnDismissListener;
import cn.see.util.widet.AlertView.OnItemClickListener;
import cn.see.util.widet.CircleImageView;
import cn.see.util.widet.CustomProgress;
import cn.see.util.widet.SelectAddressDialog;
import cn.see.util.widet.SelectAddressInterface;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @日期：2018/6/5
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： 修改用户信息Act
 */
public class SetUserDataAct extends BaseActivity implements OnItemClickListener, OnDismissListener,SelectAddressInterface {

    private static final String TAG = "SetUserDataAct" ;
    private Router router;
    private AlertView photoAlertView;
    public static final int NICK_NAME_TYPE = 0;
    public static final int SEX_TEXT_TYPE = 1;
    public static final int SIN_TEXT_TYPE = 2;
    public static final int BRO_TEXT_TYPE = 3;
    public static final int PHOOT_IMG_CARMAR = 4;//相机
    public static final int PHOOT_IMG_PICTER = 5;//相册
    private UserInfoModel.UserInfoResult result;
    private String path;
    private SelectAddressDialog dialog;
    private File file;


    @BindView(R.id.title_tv_base)
    TextView titles;
    @BindView(R.id.user_img)
    CircleImageView userImg;
    @BindView(R.id.nick_name_tv)
    TextView nick_name_tv;
    @BindView(R.id.user_phone)
    TextView user_phone;
    @BindView(R.id.sex_tv)
    TextView sex_tv;
    @BindView(R.id.area_tv)
    TextView area_tv;
    @BindView(R.id.school_tv)
    TextView school_tv;
    @BindView(R.id.sin_tv)
    TextView sin_tv;
    @BindView(R.id.bro_tv)
    TextView bro_tv;
    @BindView(R.id.xz_tv)
    TextView xz_tv;
    @BindView(R.id.tab_tv)
    TextView tab_tv;

    @OnClick(R.id.back_rela)
    void bacAct(){
        onBack();
    }

    @OnClick(R.id.nick_rela)
    void nickRela(){
        router.to(UserOtherBasicDataAct.class)
                .requestCode(NICK_NAME_TYPE)
                .putSerializable(IntentConstant.SET_USER_RESULT,result)
                .putInt(IntentConstant.USER_SET_DATA_BASIC_TYPE,NICK_NAME_TYPE)
                .launch();
    }

    @OnClick(R.id.sex_rela)
    void sexRela(){
        router.to(UserOtherBasicDataAct.class)
                .requestCode(SEX_TEXT_TYPE)
                .putSerializable(IntentConstant.SET_USER_RESULT,result)
                .putInt(IntentConstant.USER_SET_DATA_BASIC_TYPE,SEX_TEXT_TYPE)
                .launch();
    }

    @OnClick(R.id.sin_rela)
    void sinRela(){
        router.to(UserOtherBasicDataAct.class)
                .requestCode(SIN_TEXT_TYPE)
                .putSerializable(IntentConstant.SET_USER_RESULT,result)
                .putInt(IntentConstant.USER_SET_DATA_BASIC_TYPE,SIN_TEXT_TYPE)
                .launch();
    }

    @OnClick(R.id.bro_rela)
    void broRela(){
        router.to(UserOtherBasicDataAct.class)
                .requestCode(BRO_TEXT_TYPE)
                .putSerializable(IntentConstant.SET_USER_RESULT,result)
                .putInt(IntentConstant.USER_SET_DATA_BASIC_TYPE,BRO_TEXT_TYPE)
                .launch();
    }

    @OnClick(R.id.xz_rela)
    void xzRela(){
        router.to(UserOtherBasicDataAct.class)
                .requestCode(BRO_TEXT_TYPE)
                .putSerializable(IntentConstant.SET_USER_RESULT,result)
                .putInt(IntentConstant.USER_SET_DATA_BASIC_TYPE,BRO_TEXT_TYPE)
                .launch();
    }


    @OnClick(R.id.qrcode_rela)
    void qrCode(){
        router.to(QrCodeAct.class)
                .putString(IntentConstant.USER_ID_QRCODE,UserUtils.getUserID(this))
                .launch();
    }

    @OnClick(R.id.area_rela)
    void area(){
        if (dialog == null) {
            dialog = new SelectAddressDialog(this,
                    this, SelectAddressDialog.STYLE_TWO, null);

        }
        dialog.showDialog();
    }

    @OnClick(R.id.tab_rela)
    void tabrela(){
        openActivity(MineTabAct.class);
    }

    @OnClick(R.id.img_rela)
    void img_rela(){
        photoAlertView = new AlertView(null, null, "取消", null, new String[]{"拍摄照片","选择相册中的图片"}, this, AlertView.Style.ActionSheet, this);
        photoAlertView.setCancelable(true);
        photoAlertView.show();
    }
    @OnClick(R.id.exit_login)
    void exitLogin(){
        AlertView alertView = new AlertView(null, null, "取消", null, new String[]{"退出登录"}, this, AlertView.Style.ActionSheet, this);
        alertView.setCancelable(true);
        alertView.show();
    }

    @OnClick(R.id.school_rela)
    void selSchool(){
        openActivity(MineSchoolAct.class);
    }

    @Override
    public void initView() {
        titles.setText("编辑个人资料");
    }

    @Override
    public void initAfter() {
        router = Router.newIntent(this);
        getUserInfo();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_set_user_data;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onDismiss(Object o) {

    }

    @Override
    public void onItemClick(Object o, int position) {
        if(position == 0){
            if(o == photoAlertView){
                file = CamerUtils.doOpenCamera(this, PHOOT_IMG_CARMAR, "temp.jpg", IntentConstant.CARMER_PHOTO_TYPE);
                Log.i(TAG,"file:"+file);
            }else{
                //解绑用户cid
                setCid();
                UserUtils.removeUserLogin(this);
                UserUtils.removeUserID(this);
                UserUtils.removeUserPhone(this);
                onBack();
            }
        }else if(position ==1){
            Intent intent1 = new Intent(Intent.ACTION_PICK);
            intent1.setType("image/*");
            startActivityForResult(intent1,PHOOT_IMG_PICTER);
        }
    }


    public void getUserInfo(){
        Api.mineService().getUserInfoSetDate(UserUtils.getUserID(this))
                .compose(XApi.<UserInfoModel>getApiTransformer())
                .compose(XApi.<UserInfoModel>getScheduler())
                .subscribe(new ApiSubscriber<UserInfoModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        Log.i("MineFragment","err："+error.getMessage());
                    }

                    @Override
                    public void onNext(UserInfoModel infoModel) {
                        if(!infoModel.isError()){
                            setUserDat(infoModel.getResult());
                        }else{
                            ToastUtil.showToast(infoModel.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 设置当前用户信息
     * @param userResult
     */
    public  void setUserDat(UserInfoModel.UserInfoResult userResult){
        result = userResult;
        nick_name_tv.setText(userResult.getNickname());
        user_phone.setText(userResult.getAccount());
        area_tv.setText(userResult.getArea());
        school_tv.setText(userResult.getSchool_name());
        sin_tv.setText(userResult.getSignature());
        tab_tv.setText("已选标签"+userResult.getTag_count()+"个");
        bro_tv.setText(userResult.getBirthday_info());
        xz_tv.setText(userResult.getConstellation());
        if(userResult.getSex().equals("1")){
            sex_tv.setText("男");
        }else{
            sex_tv.setText("女");
        }
        GlideDownLoadImage.getInstance().loadCircleImageToCust( userResult.getUserUrl(),userImg);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getUserInfo();
    }

    /**
     * 选择的城市回调
     * @param area
     */
    @Override
    public void setAreaString(String area) {
        if(area.contains("北京市")){
            setArea("直辖市 北京");
        }else if(area.contains("上海市")){
            setArea("直辖市 上海");
        }else if(area.contains("天津市")){
            setArea("直辖市 天津");
        }else if(area.contains("重庆市")){
            setArea("直辖市 重庆");
        }else{
            setArea(area);
        }
    }


    /**
     * 修改地址
     * @param text
     */
    private void setArea(String text){
        Api.mineService().setUserArea(UserUtils.getUserID(this),text)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }
                    @Override
                    public void onNext(BaseModel txtResult) {
                        if(!txtResult.isError()){
                           getUserInfo();
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!= null){
            Log.i(TAG,"PATH:"+requestCode);
            if(requestCode == PHOOT_IMG_CARMAR){
                if(file!= null){
                    path = file.getAbsolutePath();
                }
                Uri uri = Uri.fromFile(file);
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if (cursor != null && cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                }

            }else if(requestCode == PHOOT_IMG_PICTER){
                Log.i(TAG,"相册返回");
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if (cursor != null && cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                }
            }
            Log.i(TAG,"PATH:"+path);
            release(path);
        }
    }


    public void release( String path) {
        final CustomProgress progress = CustomProgress.show(this);
        OkHttpClient client = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("uid",UserUtils.getUserID(this));
        File f = new File(path);
        builder.addFormDataPart("Filedata",f.getName(), okhttp3.RequestBody.create(MediaType.parse("image/jpg"), f));
        Request request = new Request.Builder()
                .url(HttpConstant.UPDATE_IMG).post(builder.build()).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,"失败："+e.toString());
                progress.dismiss();
            }
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Log.i(TAG,"成功："+response.body().string());
                progress.dismiss();
                getUserInfo();
//                ToastUtil.showToast("发布成功");
//                progress.dismiss();
                try {
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 解绑
     */
    public void setCid(){
        Api.mineService().setCid(UserUtils.getUserID(this),"")
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        ToastUtil.showToast(HttpConstant.NET_ERROR_MSG);
                        Log.i("FindChildFragment","error:"+error.getMessage());
                    }

                    @Override
                    public void onNext(BaseModel txtResult) {
                        if(!txtResult.isError()){
                            //ToastUtil.showToast(txtResult.getErrorMsg());
                        }else{
                            ToastUtil.showToast(txtResult.getErrorMsg());
                        }
                    }
                });
    }


}
