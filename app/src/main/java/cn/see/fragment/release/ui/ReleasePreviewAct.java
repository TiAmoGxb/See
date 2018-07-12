package cn.see.fragment.release.ui;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.router.Router;
import cn.see.R;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.base.BaseActivity;
import cn.see.fragment.release.presenter.ReleasePreviewPresenter;
import cn.see.model.TxtModel;
import cn.see.util.BitmapUtils;
import cn.see.util.SpaceItemDecoration;
import cn.see.util.ToastUtil;
import cn.see.util.UserUtils;
import cn.see.util.constant.HttpConstant;
import cn.see.util.constant.IntentConstant;
import cn.see.util.constant.PreferenceConstant;
import cn.see.util.version.PreferenceUtils;
import cn.see.util.widet.CustomGridViewLayoutManager;
import cn.see.util.widet.CustomProgress;
import retrofit2.http.PATCH;

import static cn.see.R.mipmap.setting;

/**
 * 发布预览
 */
public class ReleasePreviewAct extends BaseActivity<ReleasePreviewPresenter> {

    private static final String TAG = "ReleasePreviewAct";
    private ArrayList<String> paths;
    private CustomGridViewLayoutManager gridViewLayoutManager;
    private RecryCommonAdapter<String> adapter;
    //选择的话题ID
    private  String topicID;
    //隐私设置 0：仅自己可见 3：所有人可见（直接发布）
    private String showText = "3";
    //添加文字
    public static final int ADD_TEXT_CODE = 1;
    //编辑图片
    public static final int SET_IMG = 2;
    //添加话题
    public static final int ADD_TOPIC_CODE = 3;
    //添加位置
    public static final int ADD_AREA_CODE = 4;
    //添加标签
    public static final int ADD_TAB_CODE = 5;
    //发布隐私设置
    public static final int ADD_RELEASE_SET = 6;
    //活动还是图片
    private String type;
    //选择的标签ID
    private String tabID ;
    private CustomProgress progress;

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.image_rela)
    RelativeLayout layout;
    @BindView(R.id.right_img_top)
    ImageView titleImg;
    @BindView(R.id.img_recy)
    RecyclerView recyclerView;
    @BindView(R.id.msg_tv)
    TextView msgTv;
    @BindView(R.id.tv_topic)
    TextView topTv;
    @BindView(R.id.tv_tab)
    TextView tv_tab;
    @BindView(R.id.add_topic_rela)
    RelativeLayout addTopicRela;
    @BindView(R.id.topic_title)
    TextView topic_title;
    @BindView(R.id.topic_title_v)
    View topic_title_v;
    @BindView(R.id.clear_topic_tv)
    ImageView clearTopicImg;

    @OnClick(R.id.clear_topic_tv)
    void clearTopic(){
        topTv.setText("选择话题或者活动");
        topTv.setTextColor(getResources().getColor(R.color.text_c9));
        clearTopicImg.setVisibility(View.GONE);
    }


    @OnClick(R.id.image_rela)
    void releaseSet(){
        Router.newIntent(this)
                .requestCode(ADD_RELEASE_SET)
                .to(ReleaseSetAct.class)
                .launch();
    }

    @OnClick(R.id.msg_tv)
    void addText(){
        Router.newIntent(this)
                .requestCode(ADD_TEXT_CODE)
                .putString(IntentConstant.RELEASE_TYPE,type)
                .to(AddTextAct.class)
                .launch();
    }

    @OnClick(R.id.topic_title)
    void addTextTopic(){
        Router.newIntent(this)
                .requestCode(ADD_TEXT_CODE)
                .putString(IntentConstant.RELEASE_TYPE,type)
                .to(AddTextAct.class)
                .launch();
    }

    @OnClick(R.id.tv_topic)
    void selTop(){
        Router.newIntent(this)
                .requestCode(ADD_TOPIC_CODE)
                .to(SelectTopicAct.class)
                .launch();
    }
    @OnClick(R.id.tv_tab)
    void selTab(){
        Router.newIntent(this)
                .requestCode(ADD_TAB_CODE)
                .to(SelectTabAct.class)
                .launch();
    }

    @OnClick(R.id.rel_tv)
    void release(){
        if(paths.size()==1&&paths.get(paths.size()-1).equals("")){
            ToastUtil.showToast("至少添加一张图片");
            return;
        }
        progress = CustomProgress.show(this);
        if(type.equals("topic")){
            releaseUpTopic();
        }else{
            releaseUpText();
        }
    }



    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @Override
    public void initView() {
        title.setText("发布预览");
        //设置RecyclerView
        gridViewLayoutManager = new CustomGridViewLayoutManager(context, 3);
        gridViewLayoutManager.setScrollEnabled(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(3, 25, false));
        recyclerView.setLayoutManager(gridViewLayoutManager);
    }

    @Override
    public void initAfter() {
        type = getIntent().getStringExtra(IntentConstant.RELEASE_TYPE);
        //根据TYPE更新UI
        getP().isTypeUi(type);
        //获取传值 图片路径
        paths = (ArrayList<String>) getIntent().getSerializableExtra(IntentConstant.RELEASE_PATHS);
        if(paths.size()<9){
            paths.add("");
        }
        adapter = getP().initAdapter(paths);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_release_preview;
    }

    @Override
    public ReleasePreviewPresenter bindPresent() {
        return new ReleasePreviewPresenter();
    }

    @Override
    public void setListener() {

    }

    /**
     * 如果是话题
     */
    public void isTopicSetUi(){
        addTopicRela.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        topic_title.setVisibility(View.VISIBLE);
        topic_title_v.setVisibility(View.VISIBLE);
        msgTv.setText("内容");
        msgTv.setTextColor(getResources().getColor(R.color.text_101010));
    }


    /**
     * 如果是图片
     */
    public void isTextSetUi(){
        addTopicRela.setVisibility(View.VISIBLE);
        layout.setVisibility(View.VISIBLE);
        titleImg.setImageResource(R.mipmap.setting);
    }

    /**
     * 删除条目
     * @param position
     */
    public void removeItem(int position){
        paths.remove(position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 图片点击继续编辑
     * @param position
     */
    public void imgeItem(int position){
        if(paths.get(paths.size()-1).equals("")){
            if(position == paths.size()-1){
                Intent intent = new Intent(this, MultiImageSelectorActivity.class);
                intent.putExtra("type","review");
                intent.putExtra(IntentConstant.RELEASE_TYPE,type);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9-(paths.size())+1);
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                startActivityForResult(intent,1);
            }else{
                Router.newIntent(this)
                        .to(BeautifulDetilsAct.class)
                        .putString(IntentConstant.IMAGE_BEAU_PATH,paths.get(position))
                        .putInt(IntentConstant.IMAGE_BEAU_GPU,-1)
                        .putInt(IntentConstant.IMAGE_SEL_POSITION,position)
                        .requestCode(SET_IMG)
                        .launch();
            }
        }else{
            Router.newIntent(this)
                    .to(BeautifulDetilsAct.class)
                    .putString(IntentConstant.IMAGE_BEAU_PATH,paths.get(position))
                    .putInt(IntentConstant.IMAGE_BEAU_GPU,-1)
                    .putInt(IntentConstant.IMAGE_SEL_POSITION,position)
                    .requestCode(SET_IMG)
                    .launch();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String path = PreferenceUtils.getString(this, PreferenceConstant.IMGS_PATHS);
        if(path!=null){
            paths.remove(paths.size()-1);
            String s = path.substring(0,path.length() - 1);
            Log.i(TAG,"s:"+s);
            String[] split = s.split(",");
            for (String sp:split){
                paths.add(sp);
            }
            Log.i(TAG,"size:"+paths.size());
            if(paths.size()<9){
                paths.add("");
            }
            adapter.notifyDataSetChanged();
            PreferenceUtils.remove(this, PreferenceConstant.IMGS_PATHS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            Log.i(TAG,"requestCode:"+requestCode);
            String extra;
            switch (requestCode){
                case ADD_TEXT_CODE: //文字
                    extra = data.getStringExtra(IntentConstant.TEXT_ADD);
                    topic_title.setText(data.getStringExtra(IntentConstant.TEXT_ADD_TITLE));
                    msgTv.setTextColor(getResources().getColor(R.color.text_101010));
                    msgTv.setText(extra);
                    break;
                case ADD_TOPIC_CODE://话题
                    extra = data.getStringExtra(IntentConstant.SEL_TOPIC_NAME);
                    topicID = data.getStringExtra(IntentConstant.SEL_TOPIC_ID);
                    Log.i(TAG,"topicID："+topicID);
                    topTv.setTextColor(getResources().getColor(R.color.text_101010));
                    topTv.setText(extra);
                    clearTopicImg.setVisibility(View.VISIBLE);
                    break;
                case ADD_TAB_CODE://标签
                    StringBuffer bufferText = new StringBuffer();
                    StringBuffer bufferID = new StringBuffer();
                    List<TxtModel.TxtResult.Result> resultList = (List<TxtModel.TxtResult.Result>) data.getSerializableExtra(IntentConstant.SEL_TAB_TEXT);
                    for (TxtModel.TxtResult.Result r:resultList) {
                        bufferText.append(r.getText()+"，");
                        bufferID.append(r.getId()+",");
                    }
                    tv_tab.setTextColor(getResources().getColor(R.color.text_101010));
                    tv_tab.setText(bufferText.toString().substring(0,bufferText.length()-1));
                    tabID =bufferID.toString().substring(0,bufferID.length()-1);
                    break;
                case SET_IMG://编辑图片
                    String sdPath = data.getStringExtra(IntentConstant.IMAGE_BEAU_PATH);
                    int position = data.getIntExtra(IntentConstant.IMAGE_SEL_POSITION, -1);
                    paths.set(position,sdPath);
                    adapter.notifyDataSetChanged();
                    break;
                case ADD_RELEASE_SET://隐私设置
                    showText = data.getStringExtra(IntentConstant.RELEASE_SET_TEXT);
                    Log.i(TAG,"showText："+showText);
                    break;

            }
        }

    }

    /**
     * 发布话题
     */
    private void releaseUpTopic() {
        Map<String,String> map = new HashMap<>();
        String msg = msgTv.getText().toString();
        String topicTitle = topic_title.getText().toString();
        String tab = tv_tab.getText().toString();
        if(!topicTitle.equals("标题")){
            map.put("tname",topicTitle);
        }else{
            ToastUtil.showToast("标题不能为空");
            return;
        }
        if(!msg.equals("内容")){
            map.put("bewrite",msg);
        }else{
            ToastUtil.showToast("内容不能为空");
            return;
        }
        if(!tab.equals("添加标签")){
            map.put("tab",tabID);
        }
        map.put("user_id", UserUtils.getUserID(this));

        for (String key:map.keySet()) {
            Log.i(TAG,"key："+key);
            Log.i(TAG,"value："+map.get(key));
        }
        if(paths.get(paths.size()-1).equals("")){
            paths.remove(paths.size()-1);
        }
        getP().release(map,paths, HttpConstant.RELEASE_TOPIC);
    }

    public void releaseUpText(){
        Map<String,String> map = new HashMap<>();
        String msg = msgTv.getText().toString();
        String tab = tv_tab.getText().toString();
        String topic = topTv.getText().toString();

        if(!msg.equals("说点什么...")){
            map.put("msg",msg);
        }else{
            ToastUtil.showToast("内容不能为空");
            return;
        }
        if(!topic.equals("选择话题或活动")){
            map.put("type","topic");
            map.put("type_id",topicID);
        }
        if(!tab.equals("添加标签")){
            map.put("tab",tabID);
        }
        map.put("user_id", UserUtils.getUserID(this));

        for (String key:map.keySet()) {
            Log.i(TAG,"key："+key);
            Log.i(TAG,"value："+map.get(key));
        }
        if(paths.get(paths.size()-1).equals("")){
            paths.remove(paths.size()-1);
        }
        map.put("show",showText);
        getP().release(map,paths, HttpConstant.RELEASE_TEXT);
    }

    /**
     * 发布成功
     */
    public void releaseResponse(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                deleteFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/see"));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast("发布成功");
                        progress.dismiss();
                        onBack();
                    }
                });

            }
        }).start();
    }


    /**
     * 发布成功之后清除临时文件
     * @param file
     */
    private void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            file.delete();
        } else if (file.exists()) {
            file.delete();
        }
    }
}
