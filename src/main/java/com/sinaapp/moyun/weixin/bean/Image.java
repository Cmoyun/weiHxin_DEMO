package com.sinaapp.moyun.weixin.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * Created by Moy on 六月09  009.
 */
@Table("t_image")
public class Image {
    @Id
    private Integer id;
    @Column
    private String mediaId; //通过上传多媒体文件，得到的id。

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
