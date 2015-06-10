package com.sinaapp.moyun.weixin.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * Created by Moy on 六月10  010.
 */
@Table("t_url")
public class Url {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String url;
    @Column
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescribe() {
        return description;
    }

    public void setDescribe(String description) {
        this.description = description;
    }
}
