package com.wei.bigshow.presenter.intent;

import java.io.Serializable;

/**
 * describe
 * created by tindle
 * created time 16/3/16 下午2:44
 */
public class GifData implements Serializable{

    public String url;
    public int width;
    public int height;

    public GifData(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }
}
