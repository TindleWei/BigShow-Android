package com.wei.bigshow.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
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
import com.wei.bigshow.R;
import com.wei.bigshow.common.base.BaseFragment;
import com.wei.bigshow.common.event.TransitionEvent;
import com.wei.bigshow.core.net.ApiManager;
import com.wei.bigshow.model.network.GiphyEntity;
import com.wei.bigshow.model.network.GiphyResponse;
import com.wei.bigshow.presenter.intent.GifData;
import com.wei.bigshow.ui.activity.PlotMakerActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

    private static final String TAG = "MyStoryFragment";

    @Bind(R.id.layout_create)
    RelativeLayout layout_create;

    @Bind(R.id.iv_cover)
    ImageView iv_cover;

    @Bind(R.id.tv_content)
    EditText tv_content;

    @Bind(R.id.tv_change)
    TextView tv_change;

    @Bind(R.id.layout_cardview)
    CardView layout_cardview;

    GifData intentData = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_desk, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layout_create = $(R.id.layout_create);
        iv_cover = $(R.id.iv_cover);
        tv_change = $(R.id.tv_change);
        layout_cardview= $(R.id.layout_cardview);

        iv_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), PlotMakerActivity.class);
                intent.putExtra("data", intentData);
//                ActivityTransitionLauncher
//                        .with(getActivity())
//                        .from(v)
//                        .launch(intent);
                startActivity(intent);
            }
        });

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRandomData();
            }
        });
        requestRandomData();
    }

    public void requestRandomData() {

        Map<String, String> map = new HashMap<>();
        map.put("rating", "g");
        map.put("limit", "20");

        ApiManager.getInstance().apiService.getTrendingData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                })
                .subscribe(new Subscriber<GiphyResponse<List<GiphyEntity>>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "on Completed !");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "on Error !");
                    }

                    @Override
                    public void onNext(GiphyResponse<List<GiphyEntity>> giphyResponse) {
                        Log.e(TAG, "on Next !");
                        try {
                            if (giphyResponse.data.size() == 0) {
                                Log.e(TAG, "on size 0 !");
                                return;
                            }

                            int num = new Random().nextInt(giphyResponse.data.size());
                            List<GiphyEntity> list = giphyResponse.data;
                            GiphyEntity entity = list.get(num);

                            String url = entity.images.fixed_width.url;
                            int width = Integer.valueOf(entity.images.fixed_height.width);
                            int height = Integer.valueOf(entity.images.fixed_height.height);
                            Log.e(TAG, "url: " + url);
                            Log.e(TAG, "size w: " + width + " h: " + height);
                            intentData = new GifData(url, width, height);

                            Glide.with(getContext())
                                    .load(url)
                                    .asGif()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .placeholder(R.drawable.image_bg)
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


    public String requestStartSegment() {
        String url = "http://api.giphy.com/v1/gifs/search?q=funny+cat&api_key=dc6zaTOxFJmzC";

        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return "";
        }
    }

    public void onEvent(TransitionEvent object) {
        Log.e("TAG", "fuck");
        layout_cardview.setAlpha(0);
        layout_cardview.animate().setDuration(500).setStartDelay(600).alpha(1).start();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
