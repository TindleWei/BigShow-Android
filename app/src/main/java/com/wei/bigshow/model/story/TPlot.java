package com.wei.bigshow.model.story;

import java.util.List;

/**
 * Tree Plot for saving.
 */
public class TPlot {

    public MetaData metaData;
    List<PlotMeta> plotMetaList;
    List<Option> optionList;

    public class MetaData {
        public int treePos;
        public boolean isEnd;
    }

    public class PlotMeta {
        public String text;
        public String src; //url
        public String srcType; //image , and video
        public String srcFrom; // giphy
    }

    public class Option {
        public String text;
        public String nextTreePos;
    }

}
