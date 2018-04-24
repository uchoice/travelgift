package net.uchoice.travelgift.vote.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.uchoice.travelgift.vote.common.Const;
import net.uchoice.travelgift.vote.dao.ArticleMapper;
import net.uchoice.travelgift.vote.dao.VoteHisMapper;
import net.uchoice.travelgift.vote.entity.Article;
import net.uchoice.travelgift.vote.entity.VoteHis;
import net.uchoice.travelgift.vote.service.ArticleService;
import net.uchoice.travelgift.vote.util.DateUtils;
import net.uchoice.travelgift.vote.vo.ArticleVo;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private VoteHisMapper voteHisMapper;

	@Override
	public List<ArticleVo> findAll() {
		Article article = new Article();
		article.setStatus(Const.AUDIT_PASS);
		return articleMapper.select(article).stream().map(a -> new ArticleVo(a)).collect(Collectors.toList());
	}

	public ArticleVo get(Integer id) {
		return new ArticleVo(articleMapper.selectByPrimaryKey(id));
	}

	@Transactional
	public boolean addArticle(Article article) {
		article.setCreateDate(new Date());
		article.setVotes(0);
		article.setStatus(Const.AUDIT_ING);
		return articleMapper.insert(article) > 0;
	}

	@Transactional
	public boolean vote(int articleId, String userId) {
		int count = voteHisMapper.selectCount(userId, DateUtils.todayZeroOclock());
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
	public boolean updateArticle(Article article) {
		article.setUpdateDate(new Date());
		article.setStatus(Const.AUDIT_ING);
		return articleMapper.updateByPrimaryKey(article) > 0;
	}

	@Transactional
	public boolean audit(int articleId, int status) {
		return articleMapper.updateStatus(articleId, status) > 0;
	}

}
