package com.wei.bigshow.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * describe
 * created by tindle
 * created time 16/4/19 下午1:27
 */
@AVClassName("Story")
public class Story extends AVObject{

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    public Story(Parcel in) {
        super(in);
    }

    public Story(){

    }
}
