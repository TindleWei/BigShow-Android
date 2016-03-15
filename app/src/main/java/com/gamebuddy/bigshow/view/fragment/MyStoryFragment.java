package com.gamebuddy.bigshow.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gamebuddy.bigshow.R;
import com.gamebuddy.bigshow.common.base.BaseFragment;
import com.gamebuddy.bigshow.core.net.ApiManager;
import com.gamebuddy.bigshow.model.GiphyResponse;
import com.gamebuddy.bigshow.view.activity.PlotMakerActivity;
import com.kogitune.activity_transition.ActivityTransitionLauncher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * describe
 * created by tindle
 * created time 16/2/23 下午4:33
 */
public class MyStoryFragment extends BaseFragment {

    @Bind(R.id.layout_create)
    RelativeLayout layout_create;

    @Bind(R.id.iv_cover)
    ImageView iv_cover;

    @Bind(R.id.tv_content)
    EditText tv_content;

    @Bind(R.id.tv_logout)
    TextView tv_logout;

    String imageUrl = "";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_desk, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layout_create = (RelativeLayout)getView().findViewById(R.id.layout_create);
        iv_cover = (ImageView)getView().findViewById(R.id.iv_cover);
        tv_logout = (TextView)getView().findViewById(R.id.tv_logout);

        layout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), PlotMakerActivity.class);
                intent.putExtra("image_url", imageUrl);
                ActivityTransitionLauncher
                        .with(getActivity())
                        .from(v)
                        .launch(intent);
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startRequest();
            }
        });
    }

//    @OnClick(R.id.layout_create)
//    public void jumpRecordActivity() {

//        AVUser user = AVUser.getCurrentUser();
//        JSONObject jsonStream = user.getJSONObject("stream");
//        String res = jsonStream.toString();
//        Intent intent = new Intent(getActivity(), PlotMakerActivity.class);
//        startActivity(intent);
//        startLeanSegment();
//    }

    public void startRequest() {

        Map<String,String> map = new HashMap<>();
        map.put("q","superman");
        map.put("rating","g");
        map.put("limit","20");

        ApiManager.getInstance().apiService.getSearchData2(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .subscribe(new Subscriber<GiphyResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG","on Completed !");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","on Error !");
                    }

                    @Override
                    public void onNext(GiphyResponse giphyResponse) {
                        Log.e("TAG","on Next !");
                        try {
                            if(giphyResponse.data.size()==0) return;

                            int num = new Random().nextInt(20);

                            GiphyResponse.Data data = giphyResponse.data.get(num);
                            String url = data.images.fixed_width.url;
                            imageUrl = url;

                            Glide.with(getContext())
                                    .load(url)
                                    .asGif()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .placeholder(R.mipmap.default_load_holder)
                                    .centerCrop()
                                    .crossFade()
                                    .into(iv_cover);
                        } catch (Exception e) {
                        }
                    }
                });
    }

    public String httpRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

//    @OnClick(R.id.tv_logout)
//    public void logout() {
//        AVUser.logOut();             //清除缓存用户对象
//        AVUser currentUser = AVUser.getCurrentUser(); // 现在的currentUser是null了
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivity(intent);
//        getActivity().finish();
//    }

    public String requestStartSegment() {
        String url = "http://api.giphy.com/v1/gifs/search?q=funny+cat&api_key=dc6zaTOxFJmzC";

        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient();

        try{
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            return "";
        }
    }




}
