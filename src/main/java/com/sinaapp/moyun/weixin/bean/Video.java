package com.sinaapp.moyun.weixin.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * Created by Moy on 六月09  009.
 */
@Table("t_video")
public class Video {
    @Id
    private Integer id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String mediaId;
}
