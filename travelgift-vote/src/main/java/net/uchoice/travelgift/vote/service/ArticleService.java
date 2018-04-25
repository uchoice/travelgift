package net.uchoice.travelgift.vote.service;

import java.util.List;

import net.uchoice.travelgift.vote.vo.ArticleDetail;
import net.uchoice.travelgift.vote.vo.ArticleVo;

public interface ArticleService {

	List<ArticleVo> findAll();

	List<ArticleVo> find(String userId);

	boolean addArticle(ArticleVo articleVo);

	boolean updateArticle(ArticleVo articleVo);

	boolean vote(int articleId, String userId);

	boolean audit(int articleId, int status);

	ArticleDetail getArticle(Integer id, String userId);

}
