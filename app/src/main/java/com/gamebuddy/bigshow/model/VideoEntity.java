package com.gamebuddy.bigshow.model;


import java.io.Serializable;
import java.util.Date;

/**
 * describe
 * created by tindle
 * created time 16/1/22 下午4:39
 */
public class VideoEntity implements Serializable{

    public String videoId;

    public Object snippet; //HashMap

    public Object contentDetails;  //HashMap

    public Date refreshedAt;

    public String url; //webview加载所用的url



}
