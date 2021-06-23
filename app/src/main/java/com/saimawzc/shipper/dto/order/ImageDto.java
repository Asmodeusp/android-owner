package com.saimawzc.shipper.dto.order;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-07-28.
 */

public class ImageDto implements Serializable {
    private String imgurls;
    private String type;
    private String videos;
    private String httpUrl;//用来保存本地的

    public String getImgurls() {
        return imgurls;
    }

    public void setImgurls(String imgurls) {
        this.imgurls = imgurls;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }
}
