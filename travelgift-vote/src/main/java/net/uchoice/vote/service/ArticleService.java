package net.uchoice.vote.service;

import java.util.List;

import net.uchoice.vote.entity.Article;

public interface ArticleService {

	List<Article> findAll();

	void addArticle(Article article);

	void vote(int articleId, String userId);
	
	Article get(Integer id);

}
