package com.sinaapp.moyun.weixin.service;

import com.sinaapp.moyun.weixin.bean.Article;
import com.sinaapp.moyun.weixin.dao.ArticleDao;
import org.nutz.dao.QueryResult;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * Created by Moy on 六月10  010.
 */
@IocBean
public class ArticleService extends BaseService {

    @Inject
    private ArticleDao articleDao;

    // 获得文章列表 分页 size 8条
    public QueryResult getPage(int num) {
        return articleDao.getPage(num);
    }

    // 获得匹配的文章 like 最多5篇
    public List<Article> getArticleForLike(String like) {
        return articleDao.getArticleForLike("%"+like+"%");
    }

    // 获得最新文章 最多4篇
    public List<Article> getArticleForNow() {
        return articleDao.getArticleForNow();
    }

    // 获得指定id的文章
    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public List<Article> getTitleList() {
        return articleDao.getTitleList();
    }
}
