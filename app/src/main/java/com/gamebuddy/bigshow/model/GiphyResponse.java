package com.gamebuddy.bigshow.model;

import java.util.List;

/**
 * describe
 * created by tindle
 * created time 16/3/14 下午3:29
 */
public class GiphyResponse {

    public List<GiphyEntity> data;
    public Meta meta;
    public Pagination pagination;

    public class Meta{
        /**
         * 对应HTTP状态码
         * 200 OK
         * 403 FORBIDDEN
         * 404 NOT FOUND
         * 500 SERVER ERROR
         */
        int status;
        /**
         * 对应HTTP状态描述
         */
        String msg;
    }

    /**
     * 页码
     */
    public class Pagination{
        int total_count;
        int count;
        int offset;
    }


}
