package com.sunday.sundaymovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 * 影人详情类
 */
public class Person {

    /**
     * address : 中国 香港
     * birthDay : 7
     * birthMonth : 4
     * birthYear : 1954
     * content : 　　香港演员、导演。原籍山东，生于香港，原名陈港生，曾用名陈元龙，自幼随父母移居澳洲。1961年返港进入于占元的中国戏剧学院学习京剧及功夫，共历十年，“七小福”之一。1962年在《大小黄天霸》中首登银幕，后又参加《梁山伯与祝英台》、《秦香莲》等影片的拍摄。1971年满师后始任龙虎武师和特技演员。1972 年入大地电影公司。1973年改名陈元龙，出演《女警察》(1973)并任武术指导；同年还主演《广东小老虎》。后一度离港赴澳发展。　　1976年，罗维游说他重返影坛，并改名成龙，主演《新精武门》、《少林木人巷》、《剑花烟雨江南》、《拳精》、《蛇鹤八步》等，但未能引起注意。1978年思远公司邀他主演《蛇形刁手》和《醉拳》，与导演袁和平找对谐趣功夫戏路，一举成名，此后平步青云。1979年编导演《笑拳怪招》，成绩不俗。　　1980年转入嘉禾公司，编导演《师弟出马》。后曾往好莱坞拍摄《杀手壕》、《炮弹飞车》等，不大理想。折返香港后编导演《龙少爷》(1982)，再度掀起热潮。后陆续编导演《A计划》(1983)、《警察故事》(1985)、《龙兄虎弟》、《A计划续集》(1987)、《警察故事续集》(1988)、《奇迹》(1989)、《火烧岛》(1990)、《飞鹰计划》(1991)等，皆票房大热。他陆续主演的影片有师兄洪金宝导演的“福星”系列、《快餐车》(1984)、《龙的心》(1985)、《飞龙猛将》等，以及《警察故事III超级警察》、《双龙会》(1992)、《城市猎人》、《重案组》(1993)、《醉拳2》(1994)、《红番区》、《霹雳火》(1995)、《警察故事四之简单任务》(1996)、《一个好人》(1997)、《我是谁》(1998)等。　　1998年后曾往好莱坞拍摄了《尖峰时刻》两集、《上海正午》两集，终获好莱坞肯定，成为继李小龙之后扬名西方的华人功夫巨星。近年作品有《飞龙再生》(2003，陈嘉上)、《环游地球八十天》(2004)、《新警察故事》(2004)、《神话》(2005)等。《宝贝计划》(2006)。成龙执导的《警察故事》曾获第五届金像奖最佳影片奖，并以《红番区》、《我是谁》分别获第15、18届金像奖最佳动作指导奖；他个人并因主演《超级警察》、《重案组》两度荣获金马奖影帝，2005年获金像奖专业精神奖。2008年，成龙与李连杰携手，主演好莱坞影片《功夫之王》，影片口碑平平，但“双J合作”令无数影迷兴奋。2009年，成龙又与香港导演尔冬升合作《新宿事件》，影片充满血腥暴力与真实震撼，令人惊艳，于导演和主演成龙来说都是一大里程碑式的作品。2010年主演好莱坞影片《邻家特工》，是他拿手的功夫喜剧片。2011年出演《新少林寺》，2012年出演《十二生肖》。　　由成龙带领的成家班战功彪炳，曾荣获第4、5、7、8、9、14、21届金像奖最佳动作指导奖，以及金马奖第31、32、34届金马奖最佳动作指导奖。
     * expriences : [{"content":"1978年，以武师身份进入电影圈的成龙，接到了吴思远的邀请，主演袁和平导演的动作片《蛇形刁手》。从这部影片开始，成龙走上了他大银幕上的功夫巨星之路。虽然本片的剧情较弱，但是剧中袁小田与成龙间的动作设计现在看来依旧是极为精彩。之后成龙再次和导演袁和平合作了《醉拳》一片，延续了其谐趣功夫的戏路。","img":"http://img31.mtime.cn/mg/2014/03/13/115841.94207861.jpg","title":"一举成名《蛇形刁手》","year":1978},{"content":"《师弟出马》是成龙加入嘉禾以后拍摄的首部功夫喜剧，也是他首次自编自导自演的影片。片中成龙在借鉴了喜剧大师卓别林和基顿的精华后，巧妙的将这些元素结合起来，用武打动作来展现谐趣，在武打的设计上真正有了成龙风格。成龙式小人物打不死的精神也体现出来。踏入嘉禾以后成龙真正开启了他功夫巨星的时代。","img":"http://img31.mtime.cn/mg/2014/02/28/110447.84358774.jpg","title":"成龙风格的诞生","year":1980},{"content":"《师弟出马》成功后成龙曾往好莱坞发展，拍摄影片《杀手壕》、《炮弹飞车》等，但都不大理想。折返香港后，成龙编导演了影片《龙少爷》。在当时旧式的功夫喜剧已经无法再与新艺城模式的城市喜剧相抗衡的香港市场中，本片取得了当年票房第二的成绩。本片也成为成龙长达15年时间专注在香港发展的分水岭。","img":"http://img31.mtime.cn/mg/2014/02/28/110617.96878301.jpg","title":"回归之作《龙少爷》","year":1982},{"content":"这是成龙、洪金宝和元彪师兄弟三人第一次在一部影片里共同担任主角，危险性甚高的武打动作场面令人目不暇接。该片发行第一个星期就赚到了1400万港元，成家班凭借此片荣获第4届香港电影金像奖最佳动作设计，成龙也因此获得了最佳男主角的提名。本片之后又推出了《A计划续集》（1987），同样取得了不错的成绩。","img":"http://img31.mtime.cn/mg/2014/02/28/110817.77176743.jpg","title":"成家班之《A计划》","year":1983},{"content":"作为香港80年代十大名片之一，本片将曲折复杂的故事情节与打斗结合在一起。为了保持动作奇观的真实性，影片的特技勇于冒险地运用长镜头无剪接的方式纪录，并且辅以重复不同角度的倒叙镜头，将银幕英雄与现实英雄很好地结合起来。如成龙所说，他的特技是\u201c货真价实\u201d的，是好莱坞的科技与特殊摄影所不能取代的。","img":"http://img31.mtime.cn/mg/2014/02/28/110938.73075681.jpg","title":"经典作品《警察故事》","year":1985},{"content":"《红番区》是一部兼具多个重要意义的成龙电影。首先本片是成龙第一部制作费过亿元的影片，从《红番区》以后，成龙的每部影片投资都超过亿元；其次，本片从真正意义上开创了中国内地的电影贺岁档；而《红番区》亦是成龙打入好莱坞的里程碑之作。本片以美国纽约为背景，故事简单直接，节奏爽快明朗，娱乐性十足。","img":"http://img31.mtime.cn/mg/2014/02/28/111023.22114750.jpg","title":"里程碑之作《红番区》","year":1995},{"content":"15年前，当成龙从美国回到香港时，他已经完全丧失了信心，所以在多年后接拍的第一部美国片对他来说非常重要。最后他选择了《尖峰时刻》，由罗杰·伯恩鲍姆担当制作人，布莱特·拉特纳执导，与风趣的克里斯·塔克搭档。影片获得了2.4亿美元的票房成绩，并拍摄了三部续集，为成龙进军好莱坞打了一个漂亮的翻身仗。","img":"http://img31.mtime.cn/mg/2014/02/28/111111.71128176.jpg","title":"成功进军好莱坞","year":1998},{"content":"本片被喻为陈木胜时代的回归，而导演则希望可以拍出成龙尚未离港前的巅峰状态。成龙饰演的警官荣，是警队中的传奇人物，面对祖等年轻人的高科技犯罪，却一度全军覆没，陷入消沉。新警察的形象摆脱了前作的束缚，抓住了当代都市人的心理，最终获得了观众的好评。可以说是《警察故事》续集中影响力最大的一部。","img":"http://img31.mtime.cn/mg/2014/02/28/111523.30037137.jpg","title":"新时代《新警察故事》","year":2004}]
     * festivals : [{"festivalId":3,"img":"http://img31.mtime.cn/mg/2014/02/24/144331.32490714.jpg","nameCn":"奥斯卡金像奖","nameEn":"Academy Awards","shortName":"奥斯卡"},{"festivalId":17,"img":"http://img31.mtime.cn/mg/2014/02/24/145913.30887805.jpg","nameCn":"香港电影金像奖","nameEn":"Hong Kong Film Awards","shortName":"香港金像奖"},{"festivalId":12,"img":"http://img31.mtime.cn/mg/2014/02/24/145924.59251220.jpg","nameCn":"台湾电影金马奖","nameEn":"Golden Horse Film Festival","shortName":"金马奖"},{"festivalId":28,"img":"http://img31.mtime.cn/mg/2014/02/24/145935.79571988.jpg","nameCn":"中国电影金鸡奖","nameEn":"Golden Rooster Awards","shortName":"金鸡奖"},{"festivalId":29,"img":"http://img31.mtime.cn/mg/2014/02/24/145950.93186683.jpg","nameCn":"大众电影百花奖","nameEn":"Hundred Flowers Awards","shortName":"百花奖"},{"festivalId":7,"img":"http://img31.mtime.cn/mg/2014/02/24/165731.49200484.jpg","nameCn":"上海国际电影节","nameEn":"Shanghai International Film Festival","shortName":"上海电影节"},{"festivalId":31,"img":"http://img31.mtime.cn/mg/2014/02/24/170911.23109708.jpg","nameCn":"MTV电影奖","nameEn":"MTV Movie Awards","shortName":"MTV电影奖"},{"festivalId":35,"img":"http://img31.mtime.cn/mg/2014/02/24/171735.72919757.jpg","nameCn":"人民选择奖","nameEn":"People's Choice Awards, USA","shortName":"人民选择奖"},{"festivalId":39,"img":"http://img31.mtime.cn/mg/2014/02/24/171957.55880164.jpg","nameCn":"青少年选择奖","nameEn":"Teen Choice Awards","shortName":"青少年选择奖"}]
     * hotMovie : {"isTiket":true,"movieCover":"http://img5.mtime.cn/mt/2017/01/13/191421.14582165_1280X720X2.jpg","movieId":217896,"movieTitleCn":"功夫瑜伽","movieTitleEn":"Kung-Fu Yoga","ratingFinal":6.1,"roleName":"Jack","ticketPrice":2790,"type":"动作 / 冒险 / 喜剧"}
     * image : http://img31.mtime.cn/ph/2014/02/28/112422.17365543_1280X720X2.jpg
     * images : [{"image":"http://img31.mtime.cn/pi/2015/07/01/111641.62987889_1000X1000.jpg","imageId":7071011,"type":7002},{"image":"http://img31.mtime.cn/pi/2015/02/03/111606.54052065_1000X1000.jpg","imageId":1006575,"type":7002},{"image":"http://img21.mtime.cn/pi/2011/07/18/111040.65259220_1000X1000.jpg","imageId":1832937,"type":7002},{"image":"http://img21.mtime.cn/pi/2011/08/24/110113.50616180_1000X1000.jpg","imageId":1774325,"type":7002}]
     * movieCount : 208
     * nameCn : 成龙
     * nameEn : Jackie Chan
     * otherHonor : [{"honor":"2013福布斯中国名人榜"}]
     * profession : 演员 | 制作人 | 导演
     * quizGame : {}
     * rating : -1.0
     * ratingFinal : 8.3
     * relationPersons : [{"rCover":"http://img31.mtime.cn/ph/2014/08/21/100817.69701394_1280X720X2.jpg","rNameCn":"房祖名","rNameEn":"Jaycee Chan","rPersonId":929637,"relation":"儿子"},{"rCover":"http://img31.mtime.cn/ph/2014/06/30/090858.16358953_1280X720X2.jpg","rNameCn":"林凤娇","rNameEn":"Joan Lin","rPersonId":1067060,"relation":"妻子"},{"rCover":"http://img31.mtime.cn/ph/2014/02/22/192751.51048323_1280X720X2.jpg","rNameCn":"洪金宝","rNameEn":"Sammo Hung","rPersonId":892825,"relation":"师兄"},{"rCover":"http://img31.mtime.cn/ph/2014/02/22/201126.11951595_1280X720X2.jpg","rNameCn":"元彪","rNameEn":"Yuen Biao","rPersonId":927910,"relation":"师弟"},{"rCover":"http://img31.mtime.cn/ph/2014/03/05/101256.81242824_1280X720X2.jpg","rNameCn":"张曼玉","rNameEn":"Maggie Cheung","rPersonId":925074,"relation":"合作影人"}]
     * style : {"isLeadPage":0,"leadImg":"https://img2.mtime.cn/mg/.jpg","leadUrl":""}
     * totalNominateAward : 40
     * totalWinAward : 18
     * url : http://people.mtime.com/892908/
     */

    private String address;
    private int birthDay;
    private int birthMonth;
    private int birthYear;
    private String content;
    private HotMovieBean hotMovie;
    private String image;
    private int movieCount;
    private String nameCn;
    private String nameEn;
    private String profession;
    private String ratingFinal;
    private List<ExpriencesBean> expriences;
    private List<FestivalsBean> festivals;
    private List<ImagesBean> images;
    private List<RelationPersonsBean> relationPersons;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HotMovieBean getHotMovie() {
        return hotMovie;
    }

    public void setHotMovie(HotMovieBean hotMovie) {
        this.hotMovie = hotMovie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(int movieCount) {
        this.movieCount = movieCount;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRatingFinal() {
        return ratingFinal;
    }

    public void setRatingFinal(String ratingFinal) {
        this.ratingFinal = ratingFinal;
    }

    public List<ExpriencesBean> getExpriences() {
        return expriences;
    }

    public void setExpriences(List<ExpriencesBean> expriences) {
        this.expriences = expriences;
    }

    public List<FestivalsBean> getFestivals() {
        return festivals;
    }

    public void setFestivals(List<FestivalsBean> festivals) {
        this.festivals = festivals;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public List<RelationPersonsBean> getRelationPersons() {
        return relationPersons;
    }

    public void setRelationPersons(List<RelationPersonsBean> relationPersons) {
        this.relationPersons = relationPersons;
    }

    public static class HotMovieBean {

        /**
         * movieCover : http://img5.mtime.cn/mt/2017/01/13/191421.14582165_1280X720X2.jpg
         * movieId : 217896
         * movieTitleCn : 功夫瑜伽
         * movieTitleEn : Kung-Fu Yoga
         * ratingFinal : 6.1
         * roleName : Jack
         * type : 动作 / 冒险 / 喜剧
         */

        private String movieCover;
        private int movieId;
        private String movieTitleCn;
        private String movieTitleEn;
        private double ratingFinal;
        private String roleName;
        private String type;

        public String getMovieCover() {
            return movieCover;
        }

        public void setMovieCover(String movieCover) {
            this.movieCover = movieCover;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getMovieTitleCn() {
            return movieTitleCn;
        }

        public void setMovieTitleCn(String movieTitleCn) {
            this.movieTitleCn = movieTitleCn;
        }

        public String getMovieTitleEn() {
            return movieTitleEn;
        }

        public void setMovieTitleEn(String movieTitleEn) {
            this.movieTitleEn = movieTitleEn;
        }

        public double getRatingFinal() {
            return ratingFinal;
        }

        public void setRatingFinal(double ratingFinal) {
            this.ratingFinal = ratingFinal;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class ExpriencesBean implements Parcelable {

        /**
         * content : 1978年，以武师身份进入电影圈的成龙，接到了吴思远的邀请，主演袁和平导演的动作片《蛇形刁手》。从这部影片开始，成龙走上了他大银幕上的功夫巨星之路。虽然本片的剧情较弱，但是剧中袁小田与成龙间的动作设计现在看来依旧是极为精彩。之后成龙再次和导演袁和平合作了《醉拳》一片，延续了其谐趣功夫的戏路。
         * img : http://img31.mtime.cn/mg/2014/03/13/115841.94207861.jpg
         * title : 一举成名《蛇形刁手》
         * year : 1978
         */

        private String content;
        private String img;
        private String title;
        private int year;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(year);
            dest.writeString(img);
            dest.writeString(title);
            dest.writeString(content);
        }

        public static final Parcelable.Creator<ExpriencesBean> CREATOR = new Parcelable.ClassLoaderCreator<ExpriencesBean>() {

            @Override
            public ExpriencesBean createFromParcel(Parcel source) {
                return new ExpriencesBean(source);
            }

            @Override
            public ExpriencesBean[] newArray(int size) {
                return new ExpriencesBean[size];
            }

            @Override
            public ExpriencesBean createFromParcel(Parcel source, ClassLoader loader) {
                return new ExpriencesBean(source);
            }
        };

        private ExpriencesBean(Parcel in) {
            year = in.readInt();
            img = in.readString();
            title = in.readString();
            content = in.readString();
        }
    }

    public static class FestivalsBean {

        /**
         * festivalId : 3
         * img : http://img31.mtime.cn/mg/2014/02/24/144331.32490714.jpg
         * nameCn : 奥斯卡金像奖
         * nameEn : Academy Awards
         * shortName : 奥斯卡
         */

        private int festivalId;
        private String img;
        private String nameCn;
        private String nameEn;
        private String shortName;

        public int getFestivalId() {
            return festivalId;
        }

        public void setFestivalId(int festivalId) {
            this.festivalId = festivalId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getNameCn() {
            return nameCn;
        }

        public void setNameCn(String nameCn) {
            this.nameCn = nameCn;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }
    }

    public static class ImagesBean {

        /**
         * image : http://img31.mtime.cn/pi/2015/07/01/111641.62987889_1000X1000.jpg
         * imageId : 7071011
         * type : 7002
         */

        private String image;
        private int imageId;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getImageId() {
            return imageId;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }
    }

    public static class RelationPersonsBean {

        /**
         * rCover : http://img31.mtime.cn/ph/2014/08/21/100817.69701394_1280X720X2.jpg
         * rNameCn : 房祖名
         * rNameEn : Jaycee Chan
         * rPersonId : 929637
         * relation : 儿子
         */

        private String rCover;
        private String rNameCn;
        private String rNameEn;
        private int rPersonId;
        private String relation;

        public String getRCover() {
            return rCover;
        }

        public void setRCover(String rCover) {
            this.rCover = rCover;
        }

        public String getRNameCn() {
            return rNameCn;
        }

        public void setRNameCn(String rNameCn) {
            this.rNameCn = rNameCn;
        }

        public String getRNameEn() {
            return rNameEn;
        }

        public void setRNameEn(String rNameEn) {
            this.rNameEn = rNameEn;
        }

        public int getRPersonId() {
            return rPersonId;
        }

        public void setRPersonId(int rPersonId) {
            this.rPersonId = rPersonId;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }
    }
}
