package cn.see.app;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @日期：2018/6/4
 * @作者： GuoXinBo
 * @邮箱： guoxinbo@banling.com
 * @说明： refit拦截器 判断是否统一加入TOKEN
 */

public class TokenHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
      /*  if (!originalRequest.url().toString().contains("user/login")
                && !originalRequest.url().toString().contains("authCode")
                && !originalRequest.url().toString().contains("feorget/password")
                && !originalRequest.url().toString().contains("enterpriseConflict/add")){
            String token = SharedPref.getInstance(context).getString("TOKEN",null);
            Request updateRequest = originalRequest.newBuilder()
                    .header("token", token)
                    .build();


            return chain.proceed(updateRequest);
        }else{
            return chain.proceed(originalRequest);
        }*/
        return chain.proceed(originalRequest);
    }
}
