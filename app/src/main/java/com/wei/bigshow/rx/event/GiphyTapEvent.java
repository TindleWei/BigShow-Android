package com.wei.bigshow.rx.event;

/**
 * describe
 * created by tindle
 * created time 16/7/28 下午2:23
 */
public class GiphyTapEvent {
    public String url;
    public String slug;

    public GiphyTapEvent(String url, String slug) {
        this.url = url;
        this.slug = slug;
    }
}
