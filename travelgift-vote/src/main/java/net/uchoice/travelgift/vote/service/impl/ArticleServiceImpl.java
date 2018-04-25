package net.uchoice.travelgift.vote.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.uchoice.travelgift.vote.common.Const;
import net.uchoice.travelgift.vote.dao.ArticleMapper;
import net.uchoice.travelgift.vote.dao.VoteHisMapper;
import net.uchoice.travelgift.vote.entity.Article;
import net.uchoice.travelgift.vote.entity.VoteHis;
import net.uchoice.travelgift.vote.service.ArticleService;
import net.uchoice.travelgift.vote.util.DateUtils;
import net.uchoice.travelgift.vote.vo.ArticleDetail;
import net.uchoice.travelgift.vote.vo.ArticleVo;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private VoteHisMapper voteHisMapper;
	
	@Autowired
	private ObjectMapper om;

	@Override
	public List<ArticleVo> findAll() {
		Article article = new Article();
		article.setStatus(Const.AUDIT_PASS);
		return articleMapper.select(article).stream().map(a -> new ArticleVo(a)).collect(Collectors.toList());
	}

	public ArticleDetail getArticle(Integer id, String userId) {
		return new ArticleDetail(new ArticleVo(articleMapper.selectByPrimaryKey(id)),
				voteHisMapper.selectCount(userId, id, DateUtils.todayZeroOclock()) == 0);
	}

	@Transactional
	public boolean addArticle(ArticleVo articleVo) {
		Article article = new Article();
		Date d = new Date();
		article.setCreateDate(d);
		article.setUpdateDate(d);
		article.setVotes(0);
		article.setStatus(Const.AUDIT_ING);
		article.setTitle(articleVo.getTitle());
		try {
			article.setContent(om.writeValueAsString(articleVo.getContent()));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		article.setAuthor(articleVo.getAuthor());
		article.setCrowd(articleVo.getCrowd());
		article.setScene(articleVo.getScene());
		return articleMapper.insert(article) > 0;
	}

	@Transactional
	public boolean vote(int articleId, String userId) {
		int count = voteHisMapper.selectCount(userId, articleId, DateUtils.todayZeroOclock());
		if (count > 0) {
			return false;
		}
		VoteHis voteHis = new VoteHis();
		voteHis.setArticleId(articleId);
		voteHis.setUserId(userId);
		Date d = new Date();
		voteHis.setVoteDate(d);
		voteHis.setCreateDate(d);
		voteHisMapper.insert(voteHis);
		return articleMapper.updateVotes(articleId, 1) > 0;
	}

	@Override
	public List<ArticleVo> find(String userId) {
		Article article = new Article();
		article.setAuthor(userId);
		return articleMapper.select(article).stream().map(a -> new ArticleVo(a)).collect(Collectors.toList());
	}

	@Transactional
	public boolean updateArticle(ArticleVo articleVo) {
		Article article = articleMapper.selectByPrimaryKey(articleVo.getId());
		if(article.getAuthor().equals(articleVo.getAuthor())) {
			log.warn("Illegal Request: {} want to update article: {} of {}",  articleVo.getAuthor(), article.getId(), article.getAuthor());
			return false;
		}
		article.setTitle(articleVo.getTitle());
		try {
			article.setContent(om.writeValueAsString(articleVo.getContent()));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		article.setCrowd(articleVo.getCrowd());
		article.setScene(articleVo.getScene());
		article.setUpdateDate(new Date());
		article.setStatus(Const.AUDIT_ING);
		return articleMapper.updateByPrimaryKey(article) > 0;
	}

	@Transactional
	public boolean audit(int articleId, int status) {
		return articleMapper.updateStatus(articleId, status) > 0;
	}

}
