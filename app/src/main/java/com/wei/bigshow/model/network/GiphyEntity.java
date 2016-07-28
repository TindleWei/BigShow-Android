package com.wei.bigshow.model.network;

/**
 * describe
 * created by tindle
 * created time 16/3/16 下午2:05
 */
public class GiphyEntity {

    public String type;
    public String id;
    public String slug;
    public String url;
    public String bitly_gif_url;
    public String bitly_url;
    public String embed_url;
    public String username;
    public String source;
    public String rating;
    public String content_url;
    public String source_tld;
    public String source_post_url;
    public String import_datetime;
    public String trending_datetime;
    public Images images;

    /**
     * fixed_height - Height set to 200px. Good for mobile use.
     * fixed_height_still - Static preview image for fixed_height
     * fixed_height_downsampled - Height set to 200px. Reduced to 6 frames to minimize file size to the lowest. Works well for unlimited scroll on mobile and as animated previews. See Giphy.com on mobile web as an example.
     * fixed_width - Width set to 200px. Good for mobile use.
     * fixed_width_still - Static preview image for fixed_width
     * fixed_width_downsampled - Width set to 200px. Reduced to 6 frames. Works well for unlimited scroll on mobile and as animated previews.
     * fixed_height_small - Height set to 100px. Good for mobile keyboards.
     * fixed_height_small_still - Static preview image for fixed_height_small
     * fixed_width_small - Width set to 100px. Good for mobile keyboards
     * fixed_width_small_still - Static preview image for fixed_width_small
     * downsized - File size under 1.5mb.
     * downsized_still - Static preview image for downsized
     * downsized_medium - File size under 5mb.
     * downsized_large - File size under 8mb.
     * original - Original file size and file dimensions. Good for desktop use.
     * original_still - Preview image for original
     */
    public class Images {
        /**
         * 重要, url, width, height, size: e.g.'145080'
         * 与fixed_width对比选择size最小的
         */
        public FixedSize fixed_height;
        public FixedSizeStill fixed_height_still;
        public FixedDownsampled fixed_height_downsampled;
        /**
         * 重要, url, width, height, size: e.g.'46294'
         * 与fixed_height对比选择size最小的
         */
        public FixedSize fixed_width;
        public FixedSizeStill fixed_width_still;
        public FixedDownsampled fixed_width_downsampled;
        /**
         * Small Size
         */
        public FixedSize fixed_height_small;
        public FixedSizeStill fixed_height_small_still;
        public FixedSize fixed_width_small;
        public FixedSizeStill fixed_width_small_still;
        /**
         * Download Size
         */
        public Downsized downsized;
        public FixedSizeStill downsized_still;
        public Downsized downsized_large;
        public Downsized downsized_medium;
        /**
         * 重要, url, width, height, size: e.g.'268905'
         */
        public Original original;
        public FixedSizeStill original_still;
        public Looping looping;

        public class FixedSize {
            public String url;
            public String width;
            public String height;
            public String size;
            public String mp4;
            public String mp4_size;
            public String webp;
            public String webp_size;
        }

        public class FixedSizeStill {
            public String url;
            public String width;
            public String height;
        }

        public class FixedDownsampled {
            public String url;
            public String width;
            public String height;
            public String size;
            public String webp;
            public String webp_size;
        }

        public class Downsized {
            public String url;
            public String width;
            public String height;
            public String size;
        }

        public class Original {
            public String url;
            public String width;
            public String height;
            public String size;
            public String frames;
            public String mp4;
            public String mp4_size;
            public String webp;
            public String webp_size;
        }

        public class Looping {
            public String mp4;
        }


    }

}
