package com.wei.bigshow.rx.event;

/**
 * describe
 * created by tindle
 * created time 16/8/5 下午7:01
 */
public class EditItemEvent {

    public int position;
    public String content;


    public EditItemEvent(int position, String content) {
        this.position = position;
        this.content = content;
    }
}
