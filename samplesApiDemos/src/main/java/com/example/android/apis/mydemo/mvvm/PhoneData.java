package com.example.android.apis.mydemo.mvvm;

import java.util.List;

public class PhoneData {


    private DataBean data;
    private int code;
    private String msg;
    private long timestamp;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class DataBean {
        /**
         * img_url : http://47.106.182.74/demo/images/phone1.webp
         * desc : 麒麟980 莱卡20倍变焦
         * price : 3999.88
         * title : 华为P30
         * info_list : ["6.4英寸","8G内存","256G"]
         */

        private String img_url;
        private String desc;
        private String price;
        private String title;
        private List<String> info_list;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getInfo_list() {
            return info_list;
        }

        public void setInfo_list(List<String> info_list) {
            this.info_list = info_list;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "img_url='" + img_url + '\'' +
                    ", desc='" + desc + '\'' +
                    ", price='" + price + '\'' +
                    ", title='" + title + '\'' +
                    ", info_list=" + info_list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PhoneData{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
