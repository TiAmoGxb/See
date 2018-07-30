package cn.see.fragment.release.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.see.R;
import cn.see.adapter.MultiItemTypeAdapter;
import cn.see.adapter.RecryCommonAdapter;
import cn.see.adapter.ViewHolder;
import cn.see.base.BaseActivity;
import cn.see.util.ToastUtil;
import cn.see.util.constant.IntentConstant;

/**
 * 选择地址
 */
public class SelectAreaAct extends BaseActivity implements  OnGetSuggestionResultListener,BDLocationListener {
    public LocationClient mLocationClient = null;
    private SuggestionSearch poiSearch;
    private static final String TAG = "SelectAreaAct" ;
    private String city;
    private List<String> areaList = new ArrayList<>();
    private boolean isFrist = true;
    private RecryCommonAdapter<String> adapter;

    @BindView(R.id.title_tv_base)
    TextView title;
    @BindView(R.id.school_recy)
    RecyclerView recyclerView;
    @BindView(R.id.et_school)
    EditText editText;

    @OnClick(R.id.canal)
    void canal(){
        onBack();
    }

    @OnClick(R.id.back_rela)
    void bacView(){
        onBack();
    }

    @OnClick(R.id.no_area)
    void noArea(){
        Intent intent = new Intent();
        intent.putExtra(IntentConstant.RELEASE_AREA_TEXT,"");
        setResult(ReleasePreviewAct.ADD_AREA_TEXT,intent);
        onBack();
    }


    @Override
    public void initView() {
        title.setText("添加位置");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    @Override
    public void initAfter() {
        initLocation();
        /**
         * 初始化检索
         */
        poiSearch = SuggestionSearch.newInstance();
        poiSearch.setOnGetSuggestionResultListener(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_select_area;
    }

    @Override
    public XPresent bindPresent() {
        return null;
    }


    /**
     * 定位配置
     */
    private void initLocation(){
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        int span=1000;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public void setListener() {
        /**
         * 输入监听
         */
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG,"s:"+s);
                String text = s.toString();
                if(!text.equals("")){
                    poiSearch.requestSuggestion((new SuggestionSearchOption())
                            .keyword(text)
                            .city(city));
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * 注销检索
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        poiSearch.destroy();
    }

    /**
     * 搜索结果返回
     * @param suggestionResult
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {
        if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
            return;
        }
        if(suggestionResult.getAllSuggestions().size() == 0){
            ToastUtil.showToast("请在应用设置-权限中开启位置权限");
        }
        areaList.clear();
        for (SuggestionResult.SuggestionInfo s:suggestionResult.getAllSuggestions()  ) {
            Log.i(TAG,"address:"+s.key);
            areaList.add(s.key);
        }
        if(isFrist){
            adapter = new RecryCommonAdapter<String>(this, R.layout.layout_select_area_item,areaList) {
                @Override
                protected void convert(ViewHolder holder, String s, int position) {
                        holder.setText(R.id.name,s);

                    setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            Intent intent = new Intent();
                            intent.putExtra(IntentConstant.RELEASE_AREA_TEXT,areaList.get(position));
                            setResult(ReleasePreviewAct.ADD_AREA_TEXT,intent);
                            onBack();
                        }

                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            return false;
                        }
                    });
                }
            };
            isFrist = false;
            recyclerView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
    /**
     * 定位结果返回
     * @param bdLocation
     */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        city = bdLocation.getCity();
        poiSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword( bdLocation.getAddrStr())
                .city(bdLocation.getCity()));

    }
}
