package com.sunday.sundaymovie.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 * 一个电影所有视频的集合 类
 * 第一个视频与电影详情中的视频是同一个
 */
public class VideoAll {
    private List<Video> list;

    public VideoAll() {
        list = new ArrayList<>();
    }

    public int getVideoCount() {
        return list.size();
    }

    public List<Video> getList() {
        return list;
    }

    public void addVideo(Video video) {
        list.add(video);
    }

    /*
    * 电影视频类
    *
    * */
    public static class Video {
        /**
         * id : 64406
         * url : https://vfx.mtime.cn/Video/2017/01/28/mp4/170128094656851772_480.mp4
         * hightUrl : https://vfx.mtime.cn/Video/2017/01/28/mp4/170128094656851772.mp4
         * image : http://img5.mtime.cn/mg/2017/01/28/094614.86140407_235X132X4.jpg
         * title : 功夫瑜伽 终极版预告片
         * type : 0
         * length : 127
         */

        private int id;
        private String url;
        private String hightUrl;
        private String image;
        private String title;
        private int type;
        private int length;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHightUrl() {
            return hightUrl;
        }

        public void setHightUrl(String hightUrl) {
            this.hightUrl = hightUrl;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }
}
