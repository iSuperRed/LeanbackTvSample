package com.github.isuperred.bean;

import java.util.List;


public class Title {


    /**
     * code : 200
     * totalCount : 21
     * data : [{"id":1114,"tabCode":"c40248cac1f44c278f8bd23a0bba8b4f","name":"我的","icon":""},{"id":1113,"tabCode":"7359d189a049468d9d4e280fd1ec15c5","name":"看电视","icon":""},{"id":1138,"tabCode":"1b14cb1608d3449c83585b48d47b53c1","name":"极清4K","icon":""},{"id":1095,"tabCode":"5f6874e8106e41a680e05fe49fe4a198","name":"少儿","icon":""},{"id":1030,"tabCode":"50e4dfe685a84f929ba08952d6081877","name":"精选","icon":""},{"id":925,"tabCode":"dae28835ebac4f629cc610b4d5a8df25","name":"70年","icon":""},{"id":1098,"tabCode":"5e1958d0cf9341589db884d83aca79e3","name":"花花万物","icon":""},{"id":1103,"tabCode":"c4a72503d2374b188cf74767f2276220","name":"VIP","icon":""},{"id":1110,"tabCode":"8146c5ff88a245b9af2ce7d2bf301b27","name":"电视剧","icon":""},{"id":1112,"tabCode":"7412804a6aa24ca9be25fd8cd26f1995","name":"电影","icon":""},{"id":1048,"tabCode":"d179143bacc948d28748338562a94648","name":"综艺","icon":""},{"id":1061,"tabCode":"9c58bbdacc1449a4bb84ad6af16ba20d","name":"课堂","icon":""},{"id":1166,"tabCode":"c33db6793aba48bea06b075c35c8be5a","name":"动漫","icon":""},{"id":1116,"tabCode":"65504aa451fb4b159bbfeb7161750411","name":"篮球","icon":""},{"id":950,"tabCode":"a4c28944cb0448579007c6c20c037127","name":"体育","icon":""},{"id":939,"tabCode":"d971d4585bd14e6fadab1aa2d27b71d6","name":"游戏","icon":""},{"id":845,"tabCode":"a868db298ef84dcbb22d919d02f473cb","name":"纪录片","icon":""},{"id":1115,"tabCode":"634e89b44aeb4b2a99e9a1bb449daf8b","name":"生活","icon":""},{"id":1124,"tabCode":"695ed6a510934a93a9593b034a99fc01","name":"东方大剧院","icon":""},{"id":1125,"tabCode":"9a5fd09ddfa64c4b95b3dc02b27c7576","name":"汽车","icon":""},{"id":1136,"tabCode":"b9c9229ef6534682919d7af67438e4d6","name":"搞笑","icon":""}]
     */

    private int code;
    private int totalCount;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1114
         * tabCode : c40248cac1f44c278f8bd23a0bba8b4f
         * name : 我的
         * icon :
         */

        private int id;
        private String tabCode;
        private String name;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTabCode() {
            return tabCode;
        }

        public void setTabCode(String tabCode) {
            this.tabCode = tabCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
