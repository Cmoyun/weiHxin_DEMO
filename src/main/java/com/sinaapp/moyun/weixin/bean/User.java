package com.sinaapp.moyun.weixin.bean;

import com.sun.istack.internal.NotNull;
import org.nutz.dao.DB;
import org.nutz.dao.entity.annotation.*;

import java.util.Date;

/**
 * Created by Moy on 六月07  007.
 */
@Table("t_user")
public class User {

    @Id
    private Integer id;
    @Column
    @NotNull
    private String openId;
    @Column
    @Prev({
            @SQL(db = DB.MYSQL, value="select id from t_article order by id desc limit 0,1")
    })
    private String new_pos; // 最新指针
    @Column
    @Prev({
            @SQL(db = DB.MYSQL, value="select id from t_music order by id desc limit 0,1")
    })
    private String music_pos; // 音乐指针
    @Prev({
            @SQL(db = DB.MYSQL, value="select id from t_article order by id desc limit 0,1")
    })
    @Column
    private String article_pos; // 文章指针
    @Column
    private boolean clock; // 是否上锁
    @Column
    private boolean power; // 是否管理员权限
    @Prev({
            @SQL(db = DB.MYSQL, value="select now()")
    })
    @Column
    private Date createTime;
    @Prev({
            @SQL(db = DB.MYSQL, value="select now()")
    })
    @Column
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNew_pos() {
        return new_pos;
    }

    public void setNew_pos(String new_pos) {
        this.new_pos = new_pos;
    }

    public String getMusic_pos() {
        return music_pos;
    }

    public void setMusic_pos(String music_pos) {
        this.music_pos = music_pos;
    }

    public String getArticle_pos() {
        return article_pos;
    }

    public void setArticle_pos(String article_pos) {
        this.article_pos = article_pos;
    }

    public boolean isClock() {
        return clock;
    }

    public void setClock(boolean clock) {
        this.clock = clock;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", new_pos='" + new_pos + '\'' +
                ", music_pos='" + music_pos + '\'' +
                ", article_pos='" + article_pos + '\'' +
                ", clock=" + clock +
                ", power=" + power +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}