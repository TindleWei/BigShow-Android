package com.wei.bigshow.model;

/**
 * describe
 * created by tindle
 * created time 16/7/4 下午3:16
 */
public class BadgeItem {

    /**
     * icon : 2.jpg
     * name : 马队
     * key : MKT=
     */

    private String icon;
    private String name;
    private String key;

    public BadgeItem() {
    }

    public BadgeItem(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
