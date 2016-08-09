package com.wei.bigshow.model.zeus;

/**
 * describe
 * created by tindle
 * created time 16/8/2 上午10:57
 */
public class PlotSingleItem {

    public String text;
    public String src; //url
    public String srcType; //image , and video
    public String srcFrom; // giphy

    public int pos; // temp adapter position

    public PlotSingleItem(int pos) {
        this.pos = pos;
    }

    public PlotSingleItem() {
    }
}
