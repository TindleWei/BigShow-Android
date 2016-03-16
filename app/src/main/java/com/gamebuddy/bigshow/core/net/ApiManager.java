package com.gamebuddy.bigshow.core.net;

import com.gamebuddy.bigshow.model.GiphyResponse;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * describe
 * created by tindle
 * created time 16/3/14 下午3:24
 */
public class ApiManager {

    private static final String SERVER_URL = "http://api.giphy.com/v1/";
    private static final String API_KEY = "dc6zaTOxFJmzC";

    public static ApiManager instance;

    public static ApiManager getInstance() {
        if (null == instance) {
            synchronized (ApiManager.class) {
                if (null == instance) {
                    instance = new ApiManager();
                }
            }
        }
        return instance;
    }

    public ApiService apiService = null;
    private Retrofit retrofit = null;

    public ApiManager() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        apiService = retrofit.create(ApiService.class);
    }

    public interface ApiService {

        @GET("gifs/search?api_key=" + API_KEY)
        Observable<GiphyResponse> getSearchData(@QueryMap Map<String, String> options); //q, limit, offset, rating, fmt

        @GET("gifs/translate?api_key=" + API_KEY)
        Observable<GiphyResponse> getTranslateData(@QueryMap Map<String, String> options); //s, rating, fmt

        @GET("gifs/random?api_key=" + API_KEY)
        Observable<GiphyResponse> getRandomData(@QueryMap Map<String, String> options); //tag, rating, fmt

        @GET("gifs/trending?api_key=" + API_KEY)
        Observable<GiphyResponse> getTrendingData(@QueryMap Map<String, String> options); //limit, rating, fmt
    }


}
