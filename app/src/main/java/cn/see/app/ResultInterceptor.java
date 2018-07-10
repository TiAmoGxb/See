package cn.see.app;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;

import cn.see.base.BaseModel;
import cn.see.util.ToastUtil;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * @日期：2018/6/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： refit拦截器 主要作为检测TOKEN是否过期
 */

public class ResultInterceptor implements Interceptor {

    private Context context;

    public ResultInterceptor(Context context) {
        this.context=context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        return checkResponse(response);
    }

    private Response checkResponse(Response response) {
        try{
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)){
                        String json = body.string();
                        BaseModel baseModel = new BaseModel();
                        Gson gson = new Gson();
                        baseModel = gson.fromJson(json,BaseModel.class);
                        Integer status = baseModel.getRespCode();
                        if(status != null && status.equals(3)){
                         /*   ToastUtil.showToast(baseModel.getRespMsg());
                           *//* Router.newIntent((Activity) context)
                                    .putString("errorlog",baseModel.getRespMsg())
                                    .to(LoginAct.class)
                                    .launch();*/

                        }
                        body = ResponseBody.create(mediaType, json);
                        return response.newBuilder().body(body).build();
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
    private boolean isText(MediaType mediaType) {
        if (mediaType == null) return false;

        return ("text".equals(mediaType.subtype())
                || "json".equals(mediaType.subtype())
                || "xml".equals(mediaType.subtype())
                || "html".equals(mediaType.subtype())
                || "webviewhtml".equals(mediaType.subtype())
                || "x-www-form-urlencoded".equals(mediaType.subtype()));
    }
}
