package com.github.isuperred.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Video implements Parcelable {

    /**
     * code : 200
     * data : {"name":"精英律师","actor":"靳东,蓝盈莹,孙淳,田雨,刘敏涛,朱珠,代旭,王鸥,邬君梅,海一天,王阳,许娣,袁泉,蒋欣,凌潇","Episode":[{"groupName":"1-10","groupDetail":[{"isVip":false,"number":"1","url":"http://cn7.kankia.com/hls/20191221/e7008a3a456befc61b60720103b216af/1576895692/index.m3u8"},{"isVip":false,"number":"2","url":"http://cn7.kankia.com/hls/20191221/c752b231e04fe0c428e428a90aff55e9/1576895976/index.m3u8"},{"isVip":false,"number":"3","url":"http://cn7.kankia.com/hls/20191221/eec113a9522266dc0cc15c764d7070ab/1576935123/index.m3u8"},{"isVip":false,"number":"4","url":"http://cn7.kankia.com/hls/20191222/c1d903a602ab167e28127d47fc5b917f/1577022619/index.m3u8"},{"isVip":false,"number":"5","url":"http://cn7.kankia.com/hls/20191222/cfabd9464d2012f8c4ccf7448dd145e4/1577025007/index.m3u8"},{"isVip":false,"number":"6","url":"http://cn7.kankia.com/hls/20191223/8df3c198e7a97360735313eee12e03c2/1577109970/index.m3u8"},{"isVip":false,"number":"7","url":"http://cn7.kankia.com/hls/20191223/b3a7dd1cb167141eaf2558ea1b21e273/1577110202/index.m3u8"},{"isVip":false,"number":"8","url":"http://cn7.kankia.com/hls/20191224/bc4e2ec9ae2e29f1159c98e65dd0f1c6/1577168033/index.m3u8"},{"isVip":false,"number":"9","url":"http://cn7.kankia.com/hls/20191224/a90acc7d065e856e59c45630ad61aa30/1577168333/index.m3u8"},{"isVip":false,"number":"10","url":"http://cn7.kankia.com/hls/20191225/690d6c44f7261027c8611ef252a99997/1577283933/index.m3u8"}]},{"groupName":"11-20","groupDetail":[{"isVip":false,"number":"11","url":"http://cn7.kankia.com/hls/20191221/e7008a3a456befc61b60720103b216af/1576895692/index.m3u8"},{"isVip":false,"number":"12","url":"http://cn7.kankia.com/hls/20191221/c752b231e04fe0c428e428a90aff55e9/1576895976/index.m3u8"},{"isVip":false,"number":"13","url":"http://cn7.kankia.com/hls/20191221/eec113a9522266dc0cc15c764d7070ab/1576935123/index.m3u8"},{"isVip":false,"number":"14","url":"http://cn7.kankia.com/hls/20191222/c1d903a602ab167e28127d47fc5b917f/1577022619/index.m3u8"},{"isVip":false,"number":"15","url":"http://cn7.kankia.com/hls/20191222/cfabd9464d2012f8c4ccf7448dd145e4/1577025007/index.m3u8"},{"isVip":false,"number":"16","url":"http://cn7.kankia.com/hls/20191223/8df3c198e7a97360735313eee12e03c2/1577109970/index.m3u8"},{"isVip":false,"number":"17","url":"http://cn7.kankia.com/hls/20191223/b3a7dd1cb167141eaf2558ea1b21e273/1577110202/index.m3u8"},{"isVip":false,"number":"18","url":"http://cn7.kankia.com/hls/20191229/dc6e794faee161bb156288d98918aeba/1577628105/index.m3u8"},{"isVip":false,"number":"19","url":"http://cn7.kankia.com/hls/20191230/7ae289168f04e9c0d99dcf24d8725a33/1577715623/index.m3u8"},{"isVip":false,"number":"20","url":"http://cn7.kankia.com/hls/20191230/9268a716ce6e314ea3248e3749dec13a/1577715865/index.m3u8"}]},{"groupName":"21-30","groupDetail":[{"isVip":false,"number":"21","url":"http://cn7.kankia.com/hls/20191231/7587989158b0da80eccae9741def7d87/1577780601/index.m3u8"},{"isVip":false,"number":"22","url":"http://cn7.kankia.com/hls/20191231/cfd27d9f68fc9ffc586ecd066af20d68/1577780885/index.m3u8"},{"isVip":false,"number":"23","url":"http://cn7.kankia.com/hls/20200102/2bd6ac75c8d40e749b8bcc2e7a419262/1577945686/index.m3u8"},{"isVip":false,"number":"24","url":"http://cn7.kankia.com/hls/20200102/c5ad61238fec6c682d5bbfc060f7a374/1577946015/index.m3u8"},{"isVip":false,"number":"25","url":"http://cn7.kankia.com/hls/20200103/4d7ae6b1078d7003ee117138386399ef/1578035983/index.m3u8"},{"isVip":false,"number":"26","url":"http://cn7.kankia.com/hls/20200103/78291d6ae84a9f361480a77ffb4d70db/1578036271/index.m3u8"},{"isVip":false,"number":"27","url":"http://cn7.kankia.com/hls/20200104/44fb70fe0704e0b4c670a0ffe656150f/1578145611/index.m3u8"},{"isVip":false,"number":"28","url":"http://cn7.kankia.com/hls/20200105/18e6e2da84f0a4d06daded271e7f987c/1578232664/index.m3u8"},{"isVip":false,"number":"29","url":"http://cn7.kankia.com/hls/20200105/ab18bb4d7156d1e23d4c145dfbf52bbe/1578232379/index.m3u8"},{"isVip":false,"number":"30","url":"http://cn7.kankia.com/hls/20200106/10bf669ed5764bac2dd1809527c0b232/1578314101/index.m3u8"}]},{"groupName":"31-40","groupDetail":[{"isVip":false,"number":"31","url":"http://cn7.kankia.com/hls/20200106/3e4dfe905936c3f22c3266498ecfea85/1578316550/index.m3u8"},{"isVip":false,"number":"32","url":"http://cn7.kankia.com/hls/20200107/1702ce7c1091460ad8d140fc85ced6aa/1578404861/index.m3u8"},{"isVip":false,"number":"33","url":"http://cn7.kankia.com/hls/20200107/05e180183cd7452e4a8efecbf857a2b5/1578403341/index.m3u8"},{"isVip":false,"number":"34","url":"http://cn7.kankia.com/hls/20200108/943cf828ad06e81afd7d397b30b65a61/1578485907/index.m3u8"},{"isVip":false,"number":"35","url":"http://cn7.kankia.com/hls/20200108/a86f3a8bbef0ed73e471afb36c842274/1578490971/index.m3u8"},{"isVip":false,"number":"36","url":"http://cn7.kankia.com/hls/20200109/8767e00073c861de8a9c06abaedfe128/1578572713/index.m3u8"},{"isVip":false,"number":"37","url":"http://cn7.kankia.com/hls/20200109/97824c7ef3c910ba92b62920c3bff2f2/1578575663/index.m3u8"},{"isVip":false,"number":"38","url":"http://cn7.kankia.com/hls/20200110/e7c8ba43a870c7cdbe5b5e4c1b89a583/1578667223/index.m3u8"},{"isVip":false,"number":"39","url":"http://cn7.kankia.com/hls/20200110/cacca4fd693fba0110f5b2ef8959c5e3/1578668381/index.m3u8"},{"isVip":false,"number":"40","url":"http://cn7.kankia.com/hls/20200111/a981ee9d4db9f5acfd2649aeb8702308/1578751434/index.m3u8"}]},{"groupName":"41-50","groupDetail":[{"isVip":false,"number":"41","url":"http://cn7.kankia.com/hls/20200112/637978176bbb94892a0fef906ff85696/1578837223/index.m3u8"},{"isVip":false,"number":"42","url":"http://cn7.kankia.com/hls/20200112/43e810dbb789a4f906dfc8f8b9583042/1578838175/index.m3u8"}]}]}
     */

    private int code;
    private DataBean data;

    protected Video(Parcel in) {
        code = in.readInt();
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
    }

    public static class DataBean implements Parcelable{
        /**
         * name : 精英律师
         * actor : 靳东,蓝盈莹,孙淳,田雨,刘敏涛,朱珠,代旭,王鸥,邬君梅,海一天,王阳,许娣,袁泉,蒋欣,凌潇
         * Episode : [{"groupName":"1-10","groupDetail":[{"isVip":false,"number":"1","url":"http://cn7.kankia.com/hls/20191221/e7008a3a456befc61b60720103b216af/1576895692/index.m3u8"},{"isVip":false,"number":"2","url":"http://cn7.kankia.com/hls/20191221/c752b231e04fe0c428e428a90aff55e9/1576895976/index.m3u8"},{"isVip":false,"number":"3","url":"http://cn7.kankia.com/hls/20191221/eec113a9522266dc0cc15c764d7070ab/1576935123/index.m3u8"},{"isVip":false,"number":"4","url":"http://cn7.kankia.com/hls/20191222/c1d903a602ab167e28127d47fc5b917f/1577022619/index.m3u8"},{"isVip":false,"number":"5","url":"http://cn7.kankia.com/hls/20191222/cfabd9464d2012f8c4ccf7448dd145e4/1577025007/index.m3u8"},{"isVip":false,"number":"6","url":"http://cn7.kankia.com/hls/20191223/8df3c198e7a97360735313eee12e03c2/1577109970/index.m3u8"},{"isVip":false,"number":"7","url":"http://cn7.kankia.com/hls/20191223/b3a7dd1cb167141eaf2558ea1b21e273/1577110202/index.m3u8"},{"isVip":false,"number":"8","url":"http://cn7.kankia.com/hls/20191224/bc4e2ec9ae2e29f1159c98e65dd0f1c6/1577168033/index.m3u8"},{"isVip":false,"number":"9","url":"http://cn7.kankia.com/hls/20191224/a90acc7d065e856e59c45630ad61aa30/1577168333/index.m3u8"},{"isVip":false,"number":"10","url":"http://cn7.kankia.com/hls/20191225/690d6c44f7261027c8611ef252a99997/1577283933/index.m3u8"}]},{"groupName":"11-20","groupDetail":[{"isVip":false,"number":"11","url":"http://cn7.kankia.com/hls/20191221/e7008a3a456befc61b60720103b216af/1576895692/index.m3u8"},{"isVip":false,"number":"12","url":"http://cn7.kankia.com/hls/20191221/c752b231e04fe0c428e428a90aff55e9/1576895976/index.m3u8"},{"isVip":false,"number":"13","url":"http://cn7.kankia.com/hls/20191221/eec113a9522266dc0cc15c764d7070ab/1576935123/index.m3u8"},{"isVip":false,"number":"14","url":"http://cn7.kankia.com/hls/20191222/c1d903a602ab167e28127d47fc5b917f/1577022619/index.m3u8"},{"isVip":false,"number":"15","url":"http://cn7.kankia.com/hls/20191222/cfabd9464d2012f8c4ccf7448dd145e4/1577025007/index.m3u8"},{"isVip":false,"number":"16","url":"http://cn7.kankia.com/hls/20191223/8df3c198e7a97360735313eee12e03c2/1577109970/index.m3u8"},{"isVip":false,"number":"17","url":"http://cn7.kankia.com/hls/20191223/b3a7dd1cb167141eaf2558ea1b21e273/1577110202/index.m3u8"},{"isVip":false,"number":"18","url":"http://cn7.kankia.com/hls/20191229/dc6e794faee161bb156288d98918aeba/1577628105/index.m3u8"},{"isVip":false,"number":"19","url":"http://cn7.kankia.com/hls/20191230/7ae289168f04e9c0d99dcf24d8725a33/1577715623/index.m3u8"},{"isVip":false,"number":"20","url":"http://cn7.kankia.com/hls/20191230/9268a716ce6e314ea3248e3749dec13a/1577715865/index.m3u8"}]},{"groupName":"21-30","groupDetail":[{"isVip":false,"number":"21","url":"http://cn7.kankia.com/hls/20191231/7587989158b0da80eccae9741def7d87/1577780601/index.m3u8"},{"isVip":false,"number":"22","url":"http://cn7.kankia.com/hls/20191231/cfd27d9f68fc9ffc586ecd066af20d68/1577780885/index.m3u8"},{"isVip":false,"number":"23","url":"http://cn7.kankia.com/hls/20200102/2bd6ac75c8d40e749b8bcc2e7a419262/1577945686/index.m3u8"},{"isVip":false,"number":"24","url":"http://cn7.kankia.com/hls/20200102/c5ad61238fec6c682d5bbfc060f7a374/1577946015/index.m3u8"},{"isVip":false,"number":"25","url":"http://cn7.kankia.com/hls/20200103/4d7ae6b1078d7003ee117138386399ef/1578035983/index.m3u8"},{"isVip":false,"number":"26","url":"http://cn7.kankia.com/hls/20200103/78291d6ae84a9f361480a77ffb4d70db/1578036271/index.m3u8"},{"isVip":false,"number":"27","url":"http://cn7.kankia.com/hls/20200104/44fb70fe0704e0b4c670a0ffe656150f/1578145611/index.m3u8"},{"isVip":false,"number":"28","url":"http://cn7.kankia.com/hls/20200105/18e6e2da84f0a4d06daded271e7f987c/1578232664/index.m3u8"},{"isVip":false,"number":"29","url":"http://cn7.kankia.com/hls/20200105/ab18bb4d7156d1e23d4c145dfbf52bbe/1578232379/index.m3u8"},{"isVip":false,"number":"30","url":"http://cn7.kankia.com/hls/20200106/10bf669ed5764bac2dd1809527c0b232/1578314101/index.m3u8"}]},{"groupName":"31-40","groupDetail":[{"isVip":false,"number":"31","url":"http://cn7.kankia.com/hls/20200106/3e4dfe905936c3f22c3266498ecfea85/1578316550/index.m3u8"},{"isVip":false,"number":"32","url":"http://cn7.kankia.com/hls/20200107/1702ce7c1091460ad8d140fc85ced6aa/1578404861/index.m3u8"},{"isVip":false,"number":"33","url":"http://cn7.kankia.com/hls/20200107/05e180183cd7452e4a8efecbf857a2b5/1578403341/index.m3u8"},{"isVip":false,"number":"34","url":"http://cn7.kankia.com/hls/20200108/943cf828ad06e81afd7d397b30b65a61/1578485907/index.m3u8"},{"isVip":false,"number":"35","url":"http://cn7.kankia.com/hls/20200108/a86f3a8bbef0ed73e471afb36c842274/1578490971/index.m3u8"},{"isVip":false,"number":"36","url":"http://cn7.kankia.com/hls/20200109/8767e00073c861de8a9c06abaedfe128/1578572713/index.m3u8"},{"isVip":false,"number":"37","url":"http://cn7.kankia.com/hls/20200109/97824c7ef3c910ba92b62920c3bff2f2/1578575663/index.m3u8"},{"isVip":false,"number":"38","url":"http://cn7.kankia.com/hls/20200110/e7c8ba43a870c7cdbe5b5e4c1b89a583/1578667223/index.m3u8"},{"isVip":false,"number":"39","url":"http://cn7.kankia.com/hls/20200110/cacca4fd693fba0110f5b2ef8959c5e3/1578668381/index.m3u8"},{"isVip":false,"number":"40","url":"http://cn7.kankia.com/hls/20200111/a981ee9d4db9f5acfd2649aeb8702308/1578751434/index.m3u8"}]},{"groupName":"41-50","groupDetail":[{"isVip":false,"number":"41","url":"http://cn7.kankia.com/hls/20200112/637978176bbb94892a0fef906ff85696/1578837223/index.m3u8"},{"isVip":false,"number":"42","url":"http://cn7.kankia.com/hls/20200112/43e810dbb789a4f906dfc8f8b9583042/1578838175/index.m3u8"}]}]
         */

        private String name;
        private String actor;
        private List<EpisodeBean> Episode;

        protected DataBean(Parcel in) {
            name = in.readString();
            actor = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public List<EpisodeBean> getEpisode() {
            return Episode;
        }

        public void setEpisode(List<EpisodeBean> Episode) {
            this.Episode = Episode;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(actor);
        }

        public static class EpisodeBean implements Parcelable{
            /**
             * groupName : 1-10
             * groupDetail : [{"isVip":false,"number":"1","url":"http://cn7.kankia.com/hls/20191221/e7008a3a456befc61b60720103b216af/1576895692/index.m3u8"},{"isVip":false,"number":"2","url":"http://cn7.kankia.com/hls/20191221/c752b231e04fe0c428e428a90aff55e9/1576895976/index.m3u8"},{"isVip":false,"number":"3","url":"http://cn7.kankia.com/hls/20191221/eec113a9522266dc0cc15c764d7070ab/1576935123/index.m3u8"},{"isVip":false,"number":"4","url":"http://cn7.kankia.com/hls/20191222/c1d903a602ab167e28127d47fc5b917f/1577022619/index.m3u8"},{"isVip":false,"number":"5","url":"http://cn7.kankia.com/hls/20191222/cfabd9464d2012f8c4ccf7448dd145e4/1577025007/index.m3u8"},{"isVip":false,"number":"6","url":"http://cn7.kankia.com/hls/20191223/8df3c198e7a97360735313eee12e03c2/1577109970/index.m3u8"},{"isVip":false,"number":"7","url":"http://cn7.kankia.com/hls/20191223/b3a7dd1cb167141eaf2558ea1b21e273/1577110202/index.m3u8"},{"isVip":false,"number":"8","url":"http://cn7.kankia.com/hls/20191224/bc4e2ec9ae2e29f1159c98e65dd0f1c6/1577168033/index.m3u8"},{"isVip":false,"number":"9","url":"http://cn7.kankia.com/hls/20191224/a90acc7d065e856e59c45630ad61aa30/1577168333/index.m3u8"},{"isVip":false,"number":"10","url":"http://cn7.kankia.com/hls/20191225/690d6c44f7261027c8611ef252a99997/1577283933/index.m3u8"}]
             */

            private String groupName;
            private List<GroupDetailBean> groupDetail;

            protected EpisodeBean(Parcel in) {
                groupName = in.readString();
            }

            public static final Creator<EpisodeBean> CREATOR = new Creator<EpisodeBean>() {
                @Override
                public EpisodeBean createFromParcel(Parcel in) {
                    return new EpisodeBean(in);
                }

                @Override
                public EpisodeBean[] newArray(int size) {
                    return new EpisodeBean[size];
                }
            };

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public List<GroupDetailBean> getGroupDetail() {
                return groupDetail;
            }

            public void setGroupDetail(List<GroupDetailBean> groupDetail) {
                this.groupDetail = groupDetail;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(groupName);
            }

            public static class GroupDetailBean implements Parcelable{
                /**
                 * isVip : false
                 * number : 1
                 * url : http://cn7.kankia.com/hls/20191221/e7008a3a456befc61b60720103b216af/1576895692/index.m3u8
                 */

                private boolean isVip;
                private String number;
                private String url;

                protected GroupDetailBean(Parcel in) {
                    isVip = in.readByte() != 0;
                    number = in.readString();
                    url = in.readString();
                }

                public static final Creator<GroupDetailBean> CREATOR = new Creator<GroupDetailBean>() {
                    @Override
                    public GroupDetailBean createFromParcel(Parcel in) {
                        return new GroupDetailBean(in);
                    }

                    @Override
                    public GroupDetailBean[] newArray(int size) {
                        return new GroupDetailBean[size];
                    }
                };

                public boolean isIsVip() {
                    return isVip;
                }

                public void setIsVip(boolean isVip) {
                    this.isVip = isVip;
                }

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeByte((byte) (isVip ? 1 : 0));
                    dest.writeString(number);
                    dest.writeString(url);
                }
            }
        }
    }
}
