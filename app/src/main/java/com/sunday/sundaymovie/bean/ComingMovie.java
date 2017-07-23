package com.sunday.sundaymovie.bean;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 * 此类为即将上映电影
 */
public class ComingMovie {
    /**
     * actor1 : 马修·麦康纳
     * actor2 : 瑞茜·威瑟斯彭
     * director : 加斯·詹宁斯
     * id : 229728
     * image : http://img5.mtime.cn/mt/2017/01/18/115740.32791274_1280X720X2.jpg
     * locationName : 美国
     * releaseDate : 2月17日上映
     * title : 欢乐好声音
     * type : 动画 / 喜剧 / 剧情
     */

    private String actor1;
    private String actor2;
    private String director;
    private int id;
    private String image;
    private String locationName;
    private String releaseDate;
    private String title;
    private String type;

    public String getActor1() {
        return actor1;
    }

    public void setActor1(String actor1) {
        this.actor1 = actor1;
    }

    public String getActor2() {
        return actor2;
    }

    public void setActor2(String actor2) {
        this.actor2 = actor2;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}