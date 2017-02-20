package com.sunday.sundaymovie.model;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 * 搜索结果为影人 类
 */
public class SearchPerson extends Search {

    /**
     * personId : 892871
     * love : 90
     * personTitle : 姜文 Wen Jiang
     * personFilmography : 职业：导演、制作人
     * birth : 生于1963-1-5 中国 河北 唐山
     */

    private int personId;
    private int love;
    private String personTitle;
    private String personFilmography;
    private String birth;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public String getPersonTitle() {
        return personTitle;
    }

    public void setPersonTitle(String personTitle) {
        this.personTitle = personTitle;
    }

    public String getPersonFilmography() {
        return personFilmography;
    }

    public void setPersonFilmography(String personFilmography) {
        this.personFilmography = personFilmography;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
