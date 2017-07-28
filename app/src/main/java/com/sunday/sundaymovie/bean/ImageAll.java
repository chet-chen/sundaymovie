package com.sunday.sundaymovie.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 * 此类为一个电影所有照片的集合
 */
public class ImageAll {

    private List<Image> images;

    public ImageAll() {
        images = new ArrayList<>();
    }

    public List<Image> getImages() {
        return images;
    }

    public void addImage(Image image) {
        images.add(image);
    }

    /**
     * id : 7317312
     * image : http://img5.mtime.cn/pi/2016/12/25/153213.67270192_1000X1000.jpg
     * type : 1
     */

    public static class Image {
        // type: -1  typeName: "显示所有"
        // type: 6   typeName: "剧照"
        // type: 1   typeName: "海报"
        // type: 41  typeName: "工作照"
        // type: 31  typeName: "新闻图片"
        private int id;
        private String image;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
