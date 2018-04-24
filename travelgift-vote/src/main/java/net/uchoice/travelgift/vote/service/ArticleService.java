package net.uchoice.travelgift.vote.service;

import java.util.List;

import net.uchoice.travelgift.vote.entity.Article;
import net.uchoice.travelgift.vote.vo.ArticleVo;

public interface ArticleService {

	List<ArticleVo> findAll();

	List<ArticleVo> find(String userId);

	boolean addArticle(Article article);

	boolean updateArticle(Article article);

	boolean vote(int articleId, String userId);

	boolean audit(int articleId, int status);

	ArticleVo get(Integer id);

}
