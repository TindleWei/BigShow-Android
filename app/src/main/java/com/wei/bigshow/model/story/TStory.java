package com.wei.bigshow.model.story;

import java.util.Date;
import java.util.List;

/**
 * Tree Story for saving.
 */
public class TStory {

    public MetaData metaData;
    public List<TPlot> plotList;

    public class MetaData {

        public String authorId;
        public String title;
        public String cover;
        public String treeMode; //"2" 二叉树
        public int storyType; // 战争,冒险,恐怖...
        public int hotLevel;
        public int likeNum;

        public Date updatedAt;
        public Date createdAt;
    }

}
