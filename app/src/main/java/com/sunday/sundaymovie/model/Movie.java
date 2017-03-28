package com.sunday.sundaymovie.model;

import java.util.List;

/**
 * Created by agentchen on 2017/2/16.
 * Email agentchen97@gmail.com
 * 电影详情类
 */
public class Movie {

    private BasicBean basic;
    private BoxOfficeBean boxOffice;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public BoxOfficeBean getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BoxOfficeBean boxOffice) {
        this.boxOffice = boxOffice;
    }

    public static class BasicBean {
        /**
         * actors : [{"actorId":1256584,"img":"http://img31.mtime.cn/ph/2014/03/16/192505.66926952_1280X720X2.jpg","name":"邓超","nameEn":"Chao Deng","roleImg":"http://img31.mtime.cn/mg/2016/01/27/165331.94692758.jpg","roleName":"轩少"},{"actorId":1257132,"img":"http://img31.mtime.cn/ph/2014/06/18/142239.80267938_1280X720X2.jpg","name":"罗志祥","nameEn":"Show Lo","roleImg":"http://img31.mtime.cn/mg/2016/01/27/165343.89248491.jpg","roleName":"八爪鱼"},{"actorId":1250122,"img":"http://img31.mtime.cn/ph/2015/06/09/092110.44942932_1280X720X2.jpg","name":"张雨绮","nameEn":"Kitty Zhang","roleImg":"http://img31.mtime.cn/mg/2016/01/27/165351.80815975.jpg","roleName":"李若兰"},{"actorId":2118873,"img":"http://img31.mtime.cn/ph/2015/12/16/103054.23380456_1280X720X2.jpg","name":"林允","nameEn":"Jelly Lin","roleImg":"http://img31.mtime.cn/mg/2016/02/01/084746.25859318.jpg","roleName":"人鱼珊珊"},{"actorId":2089799,"img":"http://img31.mtime.cn/ph/2016/08/26/160916.67475672_1280X720X2.jpg","name":"孔连顺","nameEn":"Lianshun Kong","roleImg":"http://img31.mtime.cn/mg/2016/02/01/085841.68538622.jpg","roleName":"博物馆游客妇人"},{"actorId":2060062,"img":"http://img31.mtime.cn/ph/2015/06/09/154938.34268679_1280X720X2.jpg","name":"白客","nameEn":"Baike","roleImg":"http://img31.mtime.cn/mg/2016/02/01/085856.91152754.jpg","roleName":"博物馆游客小白"},{"actorId":1266212,"img":"http://img31.mtime.cn/ph/2014/03/14/153402.28276255_1280X720X2.jpg","name":"文章","nameEn":"Zhang Wen","roleImg":"http://img31.mtime.cn/mg/2016/02/01/090124.24992082.jpg","roleName":"莫探员"},{"actorId":1704012,"img":"http://img31.mtime.cn/ph/2016/02/01/171311.63059811_1280X720X2.jpg","name":"李尚正","nameEn":"Shangzheng Li","roleImg":"http://img31.mtime.cn/mg/2016/02/01/085907.20842889.jpg","roleName":"史探员"},{"actorId":1981491,"img":"http://img31.mtime.cn/ph/2016/06/15/141117.21300390_1280X720X2.jpg","name":"吴亦凡","nameEn":"Kris Wu","roleImg":"http://img31.mtime.cn/mg/2016/01/27/165439.40314650.jpg","roleName":"龙剑飞"},{"actorId":2178902,"img":"http://img31.mtime.cn/ph/2016/08/27/141955.25667440_1280X720X2.jpg","name":"杨能","nameEn":"Neng Yang","roleImg":"http://img31.mtime.cn/mg/2016/02/01/085919.86587722.jpg","roleName":"浴缸人鱼大叔/博物馆馆长"},{"actorId":1505802,"img":"http://img5.mtime.cn/ph/2016/10/31/173018.97723493_1280X720X2.jpg","name":"卢正雨","nameEn":"Zhengyu Lu","roleImg":"","roleName":"廖先生"},{"actorId":892903,"img":"http://img31.mtime.cn/ph/2016/09/02/175158.73867789_1280X720X2.jpg","name":"徐克","nameEn":"Hark Tsui","roleImg":"","roleName":"四爷"},{"actorId":2189119,"img":"http://img31.mtime.cn/ph/2016/02/09/104834.32526978_1280X720X2.jpg","name":"郑冀峰","nameEn":"Ji-feng Zheng","roleImg":"","roleName":"郑总"},{"actorId":2178807,"img":"http://img31.mtime.cn/ph/2016/08/27/142731.46218473_1280X720X2.jpg","name":"张美娥","nameEn":"Meie Zhang","roleImg":"","roleName":"人鱼"},{"actorId":957607,"img":"http://img31.mtime.cn/ph/2016/08/24/195548.31097634_1280X720X2.jpg","name":"林子聪","nameEn":"Chi Chung Lam","roleImg":"","roleName":"修理工人"},{"actorId":942189,"img":"http://img31.mtime.cn/ph/2014/12/01/162716.90373838_1280X720X2.jpg","name":"田启文","nameEn":"Kai Man Tin","roleImg":"","roleName":"博物馆游客"},{"actorId":1829994,"img":"http://img21.mtime.cn/ph/2011/12/02/173155.81444840_1280X720X2.jpg","name":"钱国伟","nameEn":"Guowei Qian","roleImg":"","roleName":"人鱼小胖"},{"actorId":2189116,"img":"http://img31.mtime.cn/ph/2016/02/09/103303.10562260_1280X720X2.jpg","name":"松冈李那","nameEn":"Linah","roleImg":"","roleName":"佐治博士助理"},{"actorId":2189118,"img":"http://img31.mtime.cn/ph/2016/08/27/143132.73851031_1280X720X2.jpg","name":"徐轸轸","nameEn":"Zhenzhen Xu","roleImg":"","roleName":"佐治博士助理"},{"actorId":2189117,"img":"http://img31.mtime.cn/ph/2016/02/09/103946.77904864_1280X720X2.jpg","name":"夏尉喻","nameEn":"Barbie","roleImg":"","roleName":"人鱼麦太"}]
         * commentSpecial :
         * director : {"directorId":893017,"img":"http://img31.mtime.cn/ph/2014/06/20/101912.19906894_1280X720X2.jpg","name":"周星驰","nameEn":"Stephen Chow"}
         * festivals : [{"festivalId":17,"img":"http://img31.mtime.cn/mg/2014/02/24/145913.30887805.jpg","nameCn":"香港电影金像奖","nameEn":"Hong Kong Film Awards","shortName":"香港金像奖"}]
         * img : http://img31.mtime.cn/mt/2016/02/04/165933.21865133_1280X720X2.jpg
         * is3D : true
         * mins : 93分钟
         * movieId : 209007
         * name : 美人鱼
         * nameEn : The Mermaid
         * overallRating : 7.4
         * releaseArea : 中国
         * releaseDate : 20160208
         * stageImg : {"count":365,"list":[{"imgId":7166188,"imgUrl":"http://img31.mtime.cn/pi/2016/01/29/102954.23316584_1280X720X2.jpg"},{"imgId":7171130,"imgUrl":"http://img31.mtime.cn/pi/2016/02/08/112328.32225663_1280X720X2.jpg"},{"imgId":7160172,"imgUrl":"http://img31.mtime.cn/pi/2016/01/18/162516.56772373_1280X720X2.jpg"},{"imgId":7160171,"imgUrl":"http://img31.mtime.cn/pi/2016/01/18/162507.54761204_1280X720X2.jpg"}]}
         * story : 富豪轩少(邓超 饰)的地产计划涉及填海工程，威胁靠海以为生的居民。背负家族秘密的美人鱼珊珊(林允 饰)被派遣前往阻止，二人在交手过程中互生情愫，虽然轩最终因为爱上珊而停止填海工作，但她因意外受伤而消失于大海。
         * type : ["喜剧","奇幻","爱情","剧情"]
         * video : {"count":19,"hightUrl":"https://vfx.mtime.cn/Video/2016/01/31/mp4/160131153611366308.mp4","img":"http://img31.mtime.cn/mg/2016/02/10/142108.44578797_235X132X4.jpg","title":"美人鱼 终极版预告片","url":"https://vfx.mtime.cn/Video/2016/01/31/mp4/160131153611366308_480.mp4","videoId":58704}
         */

        private String commentSpecial;
        private DirectorBean director;
        private String img;
        private boolean is3D;
        private String mins;
        private int movieId;
        private String name;
        private String nameEn;
        private double overallRating;
        private String releaseArea;
        private String releaseDate;
        private StageImgBean stageImg;
        private String story;
        private VideoBean video;
        private List<ActorsBean> actors;
        private List<FestivalsBean> festivals;
        private List<String> type;

        public String getCommentSpecial() {
            return commentSpecial;
        }

        public void setCommentSpecial(String commentSpecial) {
            this.commentSpecial = commentSpecial;
        }

        public DirectorBean getDirector() {
            return director;
        }

        public void setDirector(DirectorBean director) {
            this.director = director;
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

        public String getMins() {
            return mins;
        }

        public void setMins(String mins) {
            this.mins = mins;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNameEn() {
            return nameEn;
        }

        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }

        public double getOverallRating() {
            return overallRating;
        }

        public void setOverallRating(double overallRating) {
            this.overallRating = overallRating;
        }

        public String getReleaseArea() {
            return releaseArea;
        }

        public void setReleaseArea(String releaseArea) {
            this.releaseArea = releaseArea;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public StageImgBean getStageImg() {
            return stageImg;
        }

        public void setStageImg(StageImgBean stageImg) {
            this.stageImg = stageImg;
        }

        public String getStory() {
            return story;
        }

        public void setStory(String story) {
            this.story = story;
        }

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public List<ActorsBean> getActors() {
            return actors;
        }

        public void setActors(List<ActorsBean> actors) {
            this.actors = actors;
        }

        public List<FestivalsBean> getFestivals() {
            return festivals;
        }

        public void setFestivals(List<FestivalsBean> festivals) {
            this.festivals = festivals;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }

        public static class DirectorBean {

            /**
             * directorId : 893017
             * img : http://img31.mtime.cn/ph/2014/06/20/101912.19906894_1280X720X2.jpg
             * name : 周星驰
             * nameEn : Stephen Chow
             */

            private int directorId;
            private String img;
            private String name;
            private String nameEn;

            public int getDirectorId() {
                return directorId;
            }

            public void setDirectorId(int directorId) {
                this.directorId = directorId;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return "".equals(name) ? "无" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNameEn() {
                return nameEn;
            }

            public void setNameEn(String nameEn) {
                this.nameEn = nameEn;
            }

        }

        public static class StageImgBean {
            /**
             * count : 365
             * list : [{"imgId":7166188,"imgUrl":"http://img31.mtime.cn/pi/2016/01/29/102954.23316584_1280X720X2.jpg"},{"imgId":7171130,"imgUrl":"http://img31.mtime.cn/pi/2016/02/08/112328.32225663_1280X720X2.jpg"},{"imgId":7160172,"imgUrl":"http://img31.mtime.cn/pi/2016/01/18/162516.56772373_1280X720X2.jpg"},{"imgId":7160171,"imgUrl":"http://img31.mtime.cn/pi/2016/01/18/162507.54761204_1280X720X2.jpg"}]
             */

            private int count;
            private List<ListBean> list;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }


            public static class ListBean {
                /**
                 * imgId : 7166188
                 * imgUrl : http://img31.mtime.cn/pi/2016/01/29/102954.23316584_1280X720X2.jpg
                 */

                private int imgId;
                private String imgUrl;

                public int getImgId() {
                    return imgId;
                }

                public void setImgId(int imgId) {
                    this.imgId = imgId;
                }

                public String getImgUrl() {
                    return imgUrl;
                }

                public void setImgUrl(String imgUrl) {
                    this.imgUrl = imgUrl;
                }

            }
        }

        public static class VideoBean {
            /**
             * count : 19
             * hightUrl : https://vfx.mtime.cn/Video/2016/01/31/mp4/160131153611366308.mp4
             * img : http://img31.mtime.cn/mg/2016/02/10/142108.44578797_235X132X4.jpg
             * title : 美人鱼 终极版预告片
             * url : https://vfx.mtime.cn/Video/2016/01/31/mp4/160131153611366308_480.mp4
             * videoId : 58704
             */

            private int count;
            private String hightUrl;
            private String img;
            private String title;
            private int videoId;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getHightUrl() {
                return hightUrl;
            }

            public void setHightUrl(String hightUrl) {
                this.hightUrl = hightUrl;
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

            public int getVideoId() {
                return videoId;
            }

            public void setVideoId(int videoId) {
                this.videoId = videoId;
            }

        }

        public static class ActorsBean {
            /**
             * actorId : 1256584
             * img : http://img31.mtime.cn/ph/2014/03/16/192505.66926952_1280X720X2.jpg
             * name : 邓超
             * nameEn : Chao Deng
             * roleImg : http://img31.mtime.cn/mg/2016/01/27/165331.94692758.jpg
             * roleName : 轩少
             */

            private int actorId;
            private String img;
            private String name;
            private String nameEn;
            private String roleImg;
            private String roleName;

            public int getActorId() {
                return actorId;
            }

            public void setActorId(int actorId) {
                this.actorId = actorId;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNameEn() {
                return nameEn;
            }

            public void setNameEn(String nameEn) {
                this.nameEn = nameEn;
            }

            public String getRoleImg() {
                return roleImg;
            }

            public void setRoleImg(String roleImg) {
                this.roleImg = roleImg;
            }

            public String getRoleName() {
                return roleName;
            }

            public void setRoleName(String roleName) {
                this.roleName = roleName;
            }

        }

        public static class FestivalsBean {
            /**
             * festivalId : 17
             * img : http://img31.mtime.cn/mg/2014/02/24/145913.30887805.jpg
             * nameCn : 香港电影金像奖
             * nameEn : Hong Kong Film Awards
             * shortName : 香港金像奖
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

    }

    public static class BoxOfficeBean {

        /**
         * movieId : 209007
         * totalBoxDes : 33.92亿元
         * totalBoxUnit : 累计票房(亿)
         */

        private int movieId;
        private String totalBoxDes;
        private String totalBoxUnit;

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getTotalBoxDes() {
            return totalBoxDes == null ? "暂无" : totalBoxDes;
        }

        public void setTotalBoxDes(String totalBoxDes) {
            this.totalBoxDes = totalBoxDes;
        }

        public String getTotalBoxUnit() {
            return totalBoxUnit;
        }

        public void setTotalBoxUnit(String totalBoxUnit) {
            this.totalBoxUnit = totalBoxUnit;
        }

    }

}