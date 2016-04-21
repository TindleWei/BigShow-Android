package com.gamebuddy.bigshow.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * describe
 * created by tindle
 * created time 16/4/19 下午12:11
 */
@AVClassName("Plot")
public class Plot extends AVObject {

    public int num;
    public int endType; //0 is end; 1 not end.
    public String dataFrom;
    public DataWrapper dataImg;
    public DataWrapper dataText;
    public List<DataWrapper> dataChoices;
    public Story fromStory;

    class DataWrapper{
        public String type;
        public String data;
        public String json;
    }

    public static final Parcelable.Creator<Plot> CREATOR = new Parcelable.Creator<Plot>() {
        public Plot createFromParcel(Parcel in) {
            return new Plot(in);
        }

        public Plot[] newArray(int size) {
            return new Plot[size];
        }
    };

    public Plot(Parcel in) {
        super(in);
    }

    public Plot(){

    }
}
