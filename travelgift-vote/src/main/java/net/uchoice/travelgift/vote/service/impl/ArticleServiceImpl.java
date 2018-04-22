package net.uchoice.travelgift.vote.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.uchoice.travelgift.vote.dao.ArticleImageMapper;
import net.uchoice.travelgift.vote.dao.ArticleMapper;
import net.uchoice.travelgift.vote.dao.VoteHisMapper;
import net.uchoice.travelgift.vote.entity.Article;
import net.uchoice.travelgift.vote.entity.ArticleImage;
import net.uchoice.travelgift.vote.entity.VoteHis;
import net.uchoice.travelgift.vote.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private VoteHisMapper voteHisMapper;

	@Autowired
	private ArticleImageMapper articleImageMapper;

	@Override
	public List<Article> findAll() {
		return articleMapper.selectAll();
	}
	
	public Article get(Integer id) {
		return articleMapper.selectByPrimaryKey(id);
	}

	@Transactional
	public void addArticle(Article article) {
		article.setCreateDate(new Date());
		articleMapper.insert(article);
		if (article.getImages() != null && !article.getImages().isEmpty()) {
			List<ArticleImage> images = article.getImages().stream().map(r -> {
				ArticleImage image = new ArticleImage();
				image.setImage(r);
				image.setArticleId(article.getId());
				image.setCreateBy(article.getCreateBy());
				image.setCreateDate(article.getCreateDate());
				return image;
			}).collect(Collectors.toList());
			articleImageMapper.batchInsert(images);
		}
	}

	@Transactional
	public void vote(int articleId, String userId) {
		VoteHis voteHis = new VoteHis();
		voteHis.setArticleId(articleId);
		voteHis.setUserId(userId);
		voteHis.setVoteDate(new Date());
		voteHisMapper.insert(voteHis);
		articleMapper.updateVotes(articleId, 1);
	}

}
