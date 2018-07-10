package cn.see.util.http;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.see.service.FindService;
import cn.see.service.HomeService;
import cn.see.service.MineService;
import cn.see.service.NewsService;


public class Api {
    //192.168.2.208   172.16.160.67 172.16.26.86 192.168.2.205  123.127.2.206
    public static final String BASE_PATH = "http://www.xintusee.com/IOS/";
    private static HomeService homeService;
    private static MineService mineService;
    private static FindService findService;
    private static NewsService newsService;
    /**
     * 首页模块
     * @return
     */
    public static HomeService homeService(){
        if(homeService == null){
            synchronized (Api.class){
                if(homeService == null){
                    homeService = XApi.getInstance().getRetrofit(BASE_PATH,true).create(HomeService.class);
                }
            }
        }
        return homeService;
    }
    /**
     * 发现模块
     * @return
     */
    public static  FindService findService(){
        if(findService == null){
            synchronized (Api.class){
                if(findService == null){
                    findService = XApi.getInstance().getRetrofit(BASE_PATH,true).create(FindService.class);
                }
            }
        }
        return findService;
    }

    /**
     * 我的模块
     * @return
     */
    public static MineService mineService(){
        if(mineService == null){
            synchronized (Api.class){
                if(mineService == null){
                    mineService = XApi.getInstance().getRetrofit(BASE_PATH,true).create(MineService.class);
                }
            }
        }
        return mineService;
    }

    /**
     * 消息模块
     * @return
     */
    public static NewsService newsService(){
        if(newsService == null){
            synchronized (Api.class){
                if(newsService == null){
                    newsService = XApi.getInstance().getRetrofit(BASE_PATH,true).create(NewsService.class);
                }
            }
        }
        return newsService;
    }

}
