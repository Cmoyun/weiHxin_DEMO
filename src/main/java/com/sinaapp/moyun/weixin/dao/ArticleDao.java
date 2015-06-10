package com.sinaapp.moyun.weixin.dao;

import com.sinaapp.moyun.weixin.bean.Article;
import org.nutz.dao.Cnd;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.List;

/**
 * Created by Moy on 六月10  010.
 */
@IocBean
public class ArticleDao extends BaseDao{

    // 获得文章列表 分页 size 8条
    public QueryResult getPage(int num) {
        Pager pager = getDao().createPager(num, 8);
        List<Article> list = getDao().query(Article.class, Cnd.orderBy().asc("id"), pager);
        pager.setRecordCount(getDao().count(Article.class));
        return new QueryResult(list, pager);
    }

    // 获得匹配的文章 like 最多5篇
    public List<Article> getArticleForLike(String like) {
        Pager pager = getDao().createPager(1, 5);
        List<Article> list = getDao().query(Article.class, Cnd.where("title", "LIKE", like).or("description", "LIKE", like), pager);
        return list;
    }

    // 获得最新文章 最多4篇
    public List<Article> getArticleForNow() {
        Pager pager = getDao().createPager(1, 4);
        List<Article> list = getDao().query(Article.class, Cnd.orderBy().desc("id"), pager);
        return list;
    }

    // 获得指定id的文章
    public Article getArticleById(int id) {
        return getDao().fetch(Article.class, Cnd.where("id", "=", id));
    }

    public List<Article> getTitleList(){
        return getDao().query(Article.class, Cnd.where("1","=","1"));
    }
}
