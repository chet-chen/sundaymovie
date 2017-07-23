package com.sunday.sundaymovie.bean;

import java.util.List;

/**
 * Created by agentchen on 2017/2/15.
 * Email agentchen97@gmail.com
 * 正在热映 类
 */
public class ShowTimeMovies {

    /**
     * date : 2017-02-02
     * ms : [{"NearestCinemaCount":152,"NearestDay":1486022400,"NearestShowtimeCount":1767,"aN1":"成龙","aN2":"李治廷","cC":152,"commonSpecial":"大哥携\u201c小鲜肉\u201d全球寻宝石","d":"108","dN":"唐季礼","def":0,"id":217896,"img":"http://img5.mtime.cn/mt/2017/01/13/191421.14582165_1280X720X2.jpg","is3D":false,"isDMAX":false,"isFilter":false,"isHot":true,"isIMAX":false,"isIMAX3D":false,"isNew":false,"isTicket":true,"m":"","movieType":"动作 / 冒险 / 喜剧","p":["动作冒险喜剧"],"r":6.2,"rc":0,"rd":"20170128","rsC":0,"sC":3003,"t":"功夫瑜伽","tCn":"功夫瑜伽","tEn":"Kung-Fu Yoga","ua":-1,"versions":[],"wantedCount":1132},{"NearestCinemaCount":151,"NearestDay":1486022400,"NearestShowtimeCount":1112,"aN1":"邓超","aN2":"彭于晏","cC":151,"commonSpecial":"邓超彭于晏一起街头热血","d":"102","dN":"韩寒","def":0,"id":237054,"img":"http://img5.mtime.cn/mt/2017/01/12/181512.62044353_1280X720X2.jpg","is3D":false,"isDMAX":false,"isFilter":false,"isHot":true,"isIMAX":false,"isIMAX3D":false,"isNew":false,"isTicket":true,"m":"","movieType":"喜剧","p":["喜剧"],"r":6.5,"rc":0,"rd":"20170128","rsC":0,"sC":1928,"t":"乘风破浪","tCn":"乘风破浪","tEn":"Duckweed","ua":-1,"versions":[{"enum":1,"version":"2D"}],"wantedCount":2802}]
     */

    private String date;
    private List<MsBean> ms;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MsBean> getMs() {
        return ms;
    }

    public void setMs(List<MsBean> ms) {
        this.ms = ms;
    }

    public static class MsBean {
        /**
         * aN1 : 成龙
         * aN2 : 李治廷
         * commonSpecial : 大哥携“小鲜肉”全球寻宝石
         * d : 108
         * dN : 唐季礼
         * id : 217896
         * img : http://img5.mtime.cn/mt/2017/01/13/191421.14582165_1280X720X2.jpg
         * is3D : false
         * movieType : 动作 / 冒险 / 喜剧
         * r : 6.2
         * rd : 20170128
         * tCn : 功夫瑜伽
         * tEn : Kung-Fu Yoga
         */

        private String aN1;
        private String aN2;
        private String commonSpecial;
        private String d;
        private String dN;
        private int id;
        private String img;
        private boolean is3D;
        private String movieType;
        private double r;
        private String rd;
        private String tCn;
        private String tEn;

        public String getAN1() {
            return aN1;
        }

        public void setAN1(String aN1) {
            this.aN1 = aN1;
        }

        public String getAN2() {
            return aN2;
        }

        public void setAN2(String aN2) {
            this.aN2 = aN2;
        }

        public String getCommonSpecial() {
            return commonSpecial;
        }

        public void setCommonSpecial(String commonSpecial) {
            this.commonSpecial = commonSpecial;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }

        public String getDN() {
            return dN;
        }

        public void setDN(String dN) {
            this.dN = dN;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public boolean isIs3D() {
            return is3D;
        }

        public void setIs3D(boolean is3D) {
            this.is3D = is3D;
        }

        public String getMovieType() {
            return movieType;
        }

        public void setMovieType(String movieType) {
            this.movieType = movieType;
        }

        public double getR() {
            return r;
        }

        public void setR(double r) {
            this.r = r;
        }

        public String getRd() {
            return rd;
        }

        public void setRd(String rd) {
            this.rd = rd;
        }

        public String getTCn() {
            return tCn;
        }

        public void setTCn(String tCn) {
            this.tCn = tCn;
        }

        public String getTEn() {
            return tEn;
        }

        public void setTEn(String tEn) {
            this.tEn = tEn;
        }

    }

}
