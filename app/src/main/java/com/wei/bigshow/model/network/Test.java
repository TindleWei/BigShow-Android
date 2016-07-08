package com.wei.bigshow.model.network;

import java.util.List;

/**
 * describe
 * created by tindle
 * created time 16/7/7 下午8:03
 */
public class Test {


    /**
     * status : 200
     * msg : OK
     */

    private MetaBean meta;
    /**
     * total_count : 3901
     * count : 25
     * offset : 0
     */

    private PaginationBean pagination;
    /**
     * type : gif
     * id : 64zSh1uTE7xxm
     * slug : cat-funny-64zSh1uTE7xxm
     * url : http://giphy.com/gifs/cat-funny-64zSh1uTE7xxm
     * bitly_gif_url : http://gph.is/1sGq99P
     * bitly_url : http://gph.is/1sGq99P
     * embed_url : http://giphy.com/embed/64zSh1uTE7xxm
     * username :
     * source : http://wifflegif.com
     * rating : g
     * content_url :
     * source_tld : wifflegif.com
     * source_post_url : http://wifflegif.com
     * is_indexable : 0
     * import_datetime : 2016-06-15 15:11:49
     * trending_datetime : 1970-01-01 00:00:00
     * images : {"fixed_height":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200.gif","width":"356","height":"200","size":"2251285","mp4":"https://media2.giphy.com/media/64zSh1uTE7xxm/200.mp4","mp4_size":"155782","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/200.webp","webp_size":"1053250"},"fixed_height_still":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200_s.gif","width":"356","height":"200"},"fixed_height_downsampled":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200_d.gif","width":"356","height":"200","size":"223111","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/200_d.webp","webp_size":"102944"},"fixed_width":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200w.gif","width":"200","height":"113","size":"785703","mp4":"https://media2.giphy.com/media/64zSh1uTE7xxm/200w.mp4","mp4_size":"68626","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/200w.webp","webp_size":"438106"},"fixed_width_still":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200w_s.gif","width":"200","height":"113"},"fixed_width_downsampled":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200w_d.gif","width":"200","height":"113","size":"79208","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/200w_d.webp","webp_size":"42520"},"fixed_height_small":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/100.gif","width":"178","height":"100","size":"638133","mp4":"http://media2.giphy.com/media/64zSh1uTE7xxm/100.mp4","mp4_size":"64103","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/100.webp","webp_size":"372016"},"fixed_height_small_still":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/100_s.gif","width":"178","height":"100"},"fixed_width_small":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/100w.gif","width":"100","height":"56","size":"227617","mp4":"http://media2.giphy.com/media/64zSh1uTE7xxm/100w.mp4","mp4_size":"32032","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/100w.webp","webp_size":"153452"},"fixed_width_small_still":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/100w_s.gif","width":"100","height":"56"},"downsized":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif","width":"320","height":"180","size":"1957423"},"downsized_still":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy_s.gif","width":"320","height":"180"},"downsized_large":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif","width":"320","height":"180","size":"1957423"},"downsized_medium":{"url":"http://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif","width":"320","height":"180","size":"1957423"},"original":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif","width":"320","height":"180","size":"1957423","frames":"62","mp4":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.mp4","mp4_size":"289048","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/giphy.webp","webp_size":"990738"},"original_still":{"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy_s.gif","width":"320","height":"180"},"looping":{"mp4":"https://media.giphy.com/media/64zSh1uTE7xxm/giphy-loop.mp4"}}
     */

    private List<DataBean> data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MetaBean {
        private int status;
        private String msg;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    public static class PaginationBean {
        private int total_count;
        private int count;
        private int offset;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }
    }

    public static class DataBean {
        private String type;
        private String id;
        private String slug;
        private String url;
        private String bitly_gif_url;
        private String bitly_url;
        private String embed_url;
        private String username;
        private String source;
        private String rating;
        private String content_url;
        private String source_tld;
        private String source_post_url;
        private int is_indexable;
        private String import_datetime;
        private String trending_datetime;
        /**
         * fixed_height : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200.gif","width":"356","height":"200","size":"2251285","mp4":"https://media2.giphy.com/media/64zSh1uTE7xxm/200.mp4","mp4_size":"155782","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/200.webp","webp_size":"1053250"}
         * fixed_height_still : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200_s.gif","width":"356","height":"200"}
         * fixed_height_downsampled : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200_d.gif","width":"356","height":"200","size":"223111","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/200_d.webp","webp_size":"102944"}
         * fixed_width : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200w.gif","width":"200","height":"113","size":"785703","mp4":"https://media2.giphy.com/media/64zSh1uTE7xxm/200w.mp4","mp4_size":"68626","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/200w.webp","webp_size":"438106"}
         * fixed_width_still : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200w_s.gif","width":"200","height":"113"}
         * fixed_width_downsampled : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/200w_d.gif","width":"200","height":"113","size":"79208","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/200w_d.webp","webp_size":"42520"}
         * fixed_height_small : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/100.gif","width":"178","height":"100","size":"638133","mp4":"http://media2.giphy.com/media/64zSh1uTE7xxm/100.mp4","mp4_size":"64103","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/100.webp","webp_size":"372016"}
         * fixed_height_small_still : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/100_s.gif","width":"178","height":"100"}
         * fixed_width_small : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/100w.gif","width":"100","height":"56","size":"227617","mp4":"http://media2.giphy.com/media/64zSh1uTE7xxm/100w.mp4","mp4_size":"32032","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/100w.webp","webp_size":"153452"}
         * fixed_width_small_still : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/100w_s.gif","width":"100","height":"56"}
         * downsized : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif","width":"320","height":"180","size":"1957423"}
         * downsized_still : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy_s.gif","width":"320","height":"180"}
         * downsized_large : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif","width":"320","height":"180","size":"1957423"}
         * downsized_medium : {"url":"http://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif","width":"320","height":"180","size":"1957423"}
         * original : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif","width":"320","height":"180","size":"1957423","frames":"62","mp4":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.mp4","mp4_size":"289048","webp":"http://media2.giphy.com/media/64zSh1uTE7xxm/giphy.webp","webp_size":"990738"}
         * original_still : {"url":"https://media2.giphy.com/media/64zSh1uTE7xxm/giphy_s.gif","width":"320","height":"180"}
         * looping : {"mp4":"https://media.giphy.com/media/64zSh1uTE7xxm/giphy-loop.mp4"}
         */

        private ImagesBean images;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBitly_gif_url() {
            return bitly_gif_url;
        }

        public void setBitly_gif_url(String bitly_gif_url) {
            this.bitly_gif_url = bitly_gif_url;
        }

        public String getBitly_url() {
            return bitly_url;
        }

        public void setBitly_url(String bitly_url) {
            this.bitly_url = bitly_url;
        }

        public String getEmbed_url() {
            return embed_url;
        }

        public void setEmbed_url(String embed_url) {
            this.embed_url = embed_url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getContent_url() {
            return content_url;
        }

        public void setContent_url(String content_url) {
            this.content_url = content_url;
        }

        public String getSource_tld() {
            return source_tld;
        }

        public void setSource_tld(String source_tld) {
            this.source_tld = source_tld;
        }

        public String getSource_post_url() {
            return source_post_url;
        }

        public void setSource_post_url(String source_post_url) {
            this.source_post_url = source_post_url;
        }

        public int getIs_indexable() {
            return is_indexable;
        }

        public void setIs_indexable(int is_indexable) {
            this.is_indexable = is_indexable;
        }

        public String getImport_datetime() {
            return import_datetime;
        }

        public void setImport_datetime(String import_datetime) {
            this.import_datetime = import_datetime;
        }

        public String getTrending_datetime() {
            return trending_datetime;
        }

        public void setTrending_datetime(String trending_datetime) {
            this.trending_datetime = trending_datetime;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/200.gif
             * width : 356
             * height : 200
             * size : 2251285
             * mp4 : https://media2.giphy.com/media/64zSh1uTE7xxm/200.mp4
             * mp4_size : 155782
             * webp : http://media2.giphy.com/media/64zSh1uTE7xxm/200.webp
             * webp_size : 1053250
             */

            private FixedHeightBean fixed_height;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/200_s.gif
             * width : 356
             * height : 200
             */

            private FixedHeightStillBean fixed_height_still;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/200_d.gif
             * width : 356
             * height : 200
             * size : 223111
             * webp : http://media2.giphy.com/media/64zSh1uTE7xxm/200_d.webp
             * webp_size : 102944
             */

            private FixedHeightDownsampledBean fixed_height_downsampled;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/200w.gif
             * width : 200
             * height : 113
             * size : 785703
             * mp4 : https://media2.giphy.com/media/64zSh1uTE7xxm/200w.mp4
             * mp4_size : 68626
             * webp : http://media2.giphy.com/media/64zSh1uTE7xxm/200w.webp
             * webp_size : 438106
             */

            private FixedWidthBean fixed_width;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/200w_s.gif
             * width : 200
             * height : 113
             */

            private FixedWidthStillBean fixed_width_still;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/200w_d.gif
             * width : 200
             * height : 113
             * size : 79208
             * webp : http://media2.giphy.com/media/64zSh1uTE7xxm/200w_d.webp
             * webp_size : 42520
             */

            private FixedWidthDownsampledBean fixed_width_downsampled;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/100.gif
             * width : 178
             * height : 100
             * size : 638133
             * mp4 : http://media2.giphy.com/media/64zSh1uTE7xxm/100.mp4
             * mp4_size : 64103
             * webp : http://media2.giphy.com/media/64zSh1uTE7xxm/100.webp
             * webp_size : 372016
             */

            private FixedHeightSmallBean fixed_height_small;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/100_s.gif
             * width : 178
             * height : 100
             */

            private FixedHeightSmallStillBean fixed_height_small_still;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/100w.gif
             * width : 100
             * height : 56
             * size : 227617
             * mp4 : http://media2.giphy.com/media/64zSh1uTE7xxm/100w.mp4
             * mp4_size : 32032
             * webp : http://media2.giphy.com/media/64zSh1uTE7xxm/100w.webp
             * webp_size : 153452
             */

            private FixedWidthSmallBean fixed_width_small;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/100w_s.gif
             * width : 100
             * height : 56
             */

            private FixedWidthSmallStillBean fixed_width_small_still;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif
             * width : 320
             * height : 180
             * size : 1957423
             */

            private DownsizedBean downsized;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/giphy_s.gif
             * width : 320
             * height : 180
             */

            private DownsizedStillBean downsized_still;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif
             * width : 320
             * height : 180
             * size : 1957423
             */

            private DownsizedLargeBean downsized_large;
            /**
             * url : http://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif
             * width : 320
             * height : 180
             * size : 1957423
             */

            private DownsizedMediumBean downsized_medium;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.gif
             * width : 320
             * height : 180
             * size : 1957423
             * frames : 62
             * mp4 : https://media2.giphy.com/media/64zSh1uTE7xxm/giphy.mp4
             * mp4_size : 289048
             * webp : http://media2.giphy.com/media/64zSh1uTE7xxm/giphy.webp
             * webp_size : 990738
             */

            private OriginalBean original;
            /**
             * url : https://media2.giphy.com/media/64zSh1uTE7xxm/giphy_s.gif
             * width : 320
             * height : 180
             */

            private OriginalStillBean original_still;
            /**
             * mp4 : https://media.giphy.com/media/64zSh1uTE7xxm/giphy-loop.mp4
             */

            private LoopingBean looping;

            public FixedHeightBean getFixed_height() {
                return fixed_height;
            }

            public void setFixed_height(FixedHeightBean fixed_height) {
                this.fixed_height = fixed_height;
            }

            public FixedHeightStillBean getFixed_height_still() {
                return fixed_height_still;
            }

            public void setFixed_height_still(FixedHeightStillBean fixed_height_still) {
                this.fixed_height_still = fixed_height_still;
            }

            public FixedHeightDownsampledBean getFixed_height_downsampled() {
                return fixed_height_downsampled;
            }

            public void setFixed_height_downsampled(FixedHeightDownsampledBean fixed_height_downsampled) {
                this.fixed_height_downsampled = fixed_height_downsampled;
            }

            public FixedWidthBean getFixed_width() {
                return fixed_width;
            }

            public void setFixed_width(FixedWidthBean fixed_width) {
                this.fixed_width = fixed_width;
            }

            public FixedWidthStillBean getFixed_width_still() {
                return fixed_width_still;
            }

            public void setFixed_width_still(FixedWidthStillBean fixed_width_still) {
                this.fixed_width_still = fixed_width_still;
            }

            public FixedWidthDownsampledBean getFixed_width_downsampled() {
                return fixed_width_downsampled;
            }

            public void setFixed_width_downsampled(FixedWidthDownsampledBean fixed_width_downsampled) {
                this.fixed_width_downsampled = fixed_width_downsampled;
            }

            public FixedHeightSmallBean getFixed_height_small() {
                return fixed_height_small;
            }

            public void setFixed_height_small(FixedHeightSmallBean fixed_height_small) {
                this.fixed_height_small = fixed_height_small;
            }

            public FixedHeightSmallStillBean getFixed_height_small_still() {
                return fixed_height_small_still;
            }

            public void setFixed_height_small_still(FixedHeightSmallStillBean fixed_height_small_still) {
                this.fixed_height_small_still = fixed_height_small_still;
            }

            public FixedWidthSmallBean getFixed_width_small() {
                return fixed_width_small;
            }

            public void setFixed_width_small(FixedWidthSmallBean fixed_width_small) {
                this.fixed_width_small = fixed_width_small;
            }

            public FixedWidthSmallStillBean getFixed_width_small_still() {
                return fixed_width_small_still;
            }

            public void setFixed_width_small_still(FixedWidthSmallStillBean fixed_width_small_still) {
                this.fixed_width_small_still = fixed_width_small_still;
            }

            public DownsizedBean getDownsized() {
                return downsized;
            }

            public void setDownsized(DownsizedBean downsized) {
                this.downsized = downsized;
            }

            public DownsizedStillBean getDownsized_still() {
                return downsized_still;
            }

            public void setDownsized_still(DownsizedStillBean downsized_still) {
                this.downsized_still = downsized_still;
            }

            public DownsizedLargeBean getDownsized_large() {
                return downsized_large;
            }

            public void setDownsized_large(DownsizedLargeBean downsized_large) {
                this.downsized_large = downsized_large;
            }

            public DownsizedMediumBean getDownsized_medium() {
                return downsized_medium;
            }

            public void setDownsized_medium(DownsizedMediumBean downsized_medium) {
                this.downsized_medium = downsized_medium;
            }

            public OriginalBean getOriginal() {
                return original;
            }

            public void setOriginal(OriginalBean original) {
                this.original = original;
            }

            public OriginalStillBean getOriginal_still() {
                return original_still;
            }

            public void setOriginal_still(OriginalStillBean original_still) {
                this.original_still = original_still;
            }

            public LoopingBean getLooping() {
                return looping;
            }

            public void setLooping(LoopingBean looping) {
                this.looping = looping;
            }

            public static class FixedHeightBean {
                private String url;
                private String width;
                private String height;
                private String size;
                private String mp4;
                private String mp4_size;
                private String webp;
                private String webp_size;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getMp4() {
                    return mp4;
                }

                public void setMp4(String mp4) {
                    this.mp4 = mp4;
                }

                public String getMp4_size() {
                    return mp4_size;
                }

                public void setMp4_size(String mp4_size) {
                    this.mp4_size = mp4_size;
                }

                public String getWebp() {
                    return webp;
                }

                public void setWebp(String webp) {
                    this.webp = webp;
                }

                public String getWebp_size() {
                    return webp_size;
                }

                public void setWebp_size(String webp_size) {
                    this.webp_size = webp_size;
                }
            }

            public static class FixedHeightStillBean {
                private String url;
                private String width;
                private String height;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }
            }

            public static class FixedHeightDownsampledBean {
                private String url;
                private String width;
                private String height;
                private String size;
                private String webp;
                private String webp_size;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getWebp() {
                    return webp;
                }

                public void setWebp(String webp) {
                    this.webp = webp;
                }

                public String getWebp_size() {
                    return webp_size;
                }

                public void setWebp_size(String webp_size) {
                    this.webp_size = webp_size;
                }
            }

            public static class FixedWidthBean {
                private String url;
                private String width;
                private String height;
                private String size;
                private String mp4;
                private String mp4_size;
                private String webp;
                private String webp_size;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getMp4() {
                    return mp4;
                }

                public void setMp4(String mp4) {
                    this.mp4 = mp4;
                }

                public String getMp4_size() {
                    return mp4_size;
                }

                public void setMp4_size(String mp4_size) {
                    this.mp4_size = mp4_size;
                }

                public String getWebp() {
                    return webp;
                }

                public void setWebp(String webp) {
                    this.webp = webp;
                }

                public String getWebp_size() {
                    return webp_size;
                }

                public void setWebp_size(String webp_size) {
                    this.webp_size = webp_size;
                }
            }

            public static class FixedWidthStillBean {
                private String url;
                private String width;
                private String height;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }
            }

            public static class FixedWidthDownsampledBean {
                private String url;
                private String width;
                private String height;
                private String size;
                private String webp;
                private String webp_size;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getWebp() {
                    return webp;
                }

                public void setWebp(String webp) {
                    this.webp = webp;
                }

                public String getWebp_size() {
                    return webp_size;
                }

                public void setWebp_size(String webp_size) {
                    this.webp_size = webp_size;
                }
            }

            public static class FixedHeightSmallBean {
                private String url;
                private String width;
                private String height;
                private String size;
                private String mp4;
                private String mp4_size;
                private String webp;
                private String webp_size;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getMp4() {
                    return mp4;
                }

                public void setMp4(String mp4) {
                    this.mp4 = mp4;
                }

                public String getMp4_size() {
                    return mp4_size;
                }

                public void setMp4_size(String mp4_size) {
                    this.mp4_size = mp4_size;
                }

                public String getWebp() {
                    return webp;
                }

                public void setWebp(String webp) {
                    this.webp = webp;
                }

                public String getWebp_size() {
                    return webp_size;
                }

                public void setWebp_size(String webp_size) {
                    this.webp_size = webp_size;
                }
            }

            public static class FixedHeightSmallStillBean {
                private String url;
                private String width;
                private String height;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }
            }

            public static class FixedWidthSmallBean {
                private String url;
                private String width;
                private String height;
                private String size;
                private String mp4;
                private String mp4_size;
                private String webp;
                private String webp_size;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getMp4() {
                    return mp4;
                }

                public void setMp4(String mp4) {
                    this.mp4 = mp4;
                }

                public String getMp4_size() {
                    return mp4_size;
                }

                public void setMp4_size(String mp4_size) {
                    this.mp4_size = mp4_size;
                }

                public String getWebp() {
                    return webp;
                }

                public void setWebp(String webp) {
                    this.webp = webp;
                }

                public String getWebp_size() {
                    return webp_size;
                }

                public void setWebp_size(String webp_size) {
                    this.webp_size = webp_size;
                }
            }

            public static class FixedWidthSmallStillBean {
                private String url;
                private String width;
                private String height;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }
            }

            public static class DownsizedBean {
                private String url;
                private String width;
                private String height;
                private String size;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }
            }

            public static class DownsizedStillBean {
                private String url;
                private String width;
                private String height;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }
            }

            public static class DownsizedLargeBean {
                private String url;
                private String width;
                private String height;
                private String size;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }
            }

            public static class DownsizedMediumBean {
                private String url;
                private String width;
                private String height;
                private String size;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }
            }

            public static class OriginalBean {
                private String url;
                private String width;
                private String height;
                private String size;
                private String frames;
                private String mp4;
                private String mp4_size;
                private String webp;
                private String webp_size;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getFrames() {
                    return frames;
                }

                public void setFrames(String frames) {
                    this.frames = frames;
                }

                public String getMp4() {
                    return mp4;
                }

                public void setMp4(String mp4) {
                    this.mp4 = mp4;
                }

                public String getMp4_size() {
                    return mp4_size;
                }

                public void setMp4_size(String mp4_size) {
                    this.mp4_size = mp4_size;
                }

                public String getWebp() {
                    return webp;
                }

                public void setWebp(String webp) {
                    this.webp = webp;
                }

                public String getWebp_size() {
                    return webp_size;
                }

                public void setWebp_size(String webp_size) {
                    this.webp_size = webp_size;
                }
            }

            public static class OriginalStillBean {
                private String url;
                private String width;
                private String height;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }
            }

            public static class LoopingBean {
                private String mp4;

                public String getMp4() {
                    return mp4;
                }

                public void setMp4(String mp4) {
                    this.mp4 = mp4;
                }
            }
        }
    }
}
