package net.uchoice.travelgift.vote.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.uchoice.travelgift.user.domain.UserDO;
import net.uchoice.travelgift.user.service.UserService;
import net.uchoice.travelgift.vote.common.Const;
import net.uchoice.travelgift.vote.dao.ArticleMapper;
import net.uchoice.travelgift.vote.dao.VoteHisMapper;
import net.uchoice.travelgift.vote.entity.Article;
import net.uchoice.travelgift.vote.entity.VoteHis;
import net.uchoice.travelgift.vote.exception.ArticleNotExistsException;
import net.uchoice.travelgift.vote.exception.ForbiddenRequestException;
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
	private UserService userService;

	@Autowired
	private ObjectMapper om;

	@Override
	public List<ArticleVo> findAll() {
		Article article = new Article();
		article.setStatus(Const.AUDIT_PASS);
		return articleMapper.select(article).stream().map(a -> new ArticleVo(a)).collect(Collectors.toList());
	}

	public ArticleDetail getArticle(Integer id, String userId) {
		Article article = articleMapper.selectByPrimaryKey(id);
		if (article == null) {
			throw new ArticleNotExistsException("user: " + userId + ", notExists article: " + id);
		}
		boolean canVote;
		if (StringUtils.isEmpty(userId)) {
			canVote = true;
		} else {
			UserDO userDO = userService.getUserByOpenId(userId);
			if(userDO != null && userDO.getIsAdmin() == 1) {
				
			} else if (article.getStatus() != Const.AUDIT_PASS && !userId.equals(article.getAuthor())) {
				throw new ForbiddenRequestException(
						"user: " + userId + " cannot view unAudit article: " + id + " of user: " + article.getAuthor());
			}
			canVote = voteHisMapper.selectCount(userId, id, DateUtils.todayZeroOclock()) == 0;
		}
		return new ArticleDetail(new ArticleVo(article), canVote);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean addArticle(ArticleVo articleVo) {
		if (StringUtils.isEmpty(articleVo.getAuthor())) {
			throw new ForbiddenRequestException("user: null cannot add article: " + articleVo.getTitle());
		}
		Article article = new Article();
		Date d = new Date();
		article.setCreateDate(d);
		article.setUpdateDate(d);
		article.setVotes(0);
		article.setStatus(Const.AUDIT_ING);
		article.setTitle(articleVo.getTitle());
		try {
			article.setContent(om.writeValueAsString(articleVo.getContent()));
			article.setCrowd(om.writeValueAsString(articleVo.getCrowds()));
			article.setScene(om.writeValueAsString(articleVo.getScenes()));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		article.setAuthor(articleVo.getAuthor());
		return articleMapper.insert(article) > 0;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean vote(int articleId, String userId) {
		if (StringUtils.isEmpty(userId)) {
			throw new ForbiddenRequestException("user: [" + userId + "] cannot vote article: " + articleId);
		}
		Article article = articleMapper.selectByPrimaryKey(articleId);
		if (article == null) {
			throw new ArticleNotExistsException("user: " + userId + ", notExists article: " + articleId);
		}
		if (article.getStatus() != Const.AUDIT_PASS) {
			throw new ForbiddenRequestException("user: " + userId + " cannot vote article: " + article.getId()
					+ " of user: " + article.getAuthor());
		}
		int count = voteHisMapper.selectCount(userId, articleId, DateUtils.todayZeroOclock());
		if (count > 0) {
			throw new ForbiddenRequestException("user: " + userId + " has voted: " + articleId + " today");
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
	public List<ArticleVo> find(Article article) {
		return articleMapper.select(article).stream().map(a -> new ArticleVo(a)).collect(Collectors.toList());
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean updateArticle(ArticleVo articleVo) {
		if (StringUtils.isEmpty(articleVo)) {
			throw new ForbiddenRequestException(
					"user: [" + articleVo.getAuthor() + "] cannot vote article: " + articleVo.getId());
		}
		Article article = articleMapper.selectByPrimaryKey(articleVo.getId());
		if (article == null) {
			throw new ArticleNotExistsException(
					"user: " + articleVo.getAuthor() + ", notExists article: " + articleVo.getId());
		}
		if (!article.getAuthor().equals(articleVo.getAuthor())) {
			throw new ForbiddenRequestException("user: " + articleVo.getAuthor() + ", cannot update article: "
					+ article.getId() + " of " + article.getAuthor());
		}
		article.setTitle(articleVo.getTitle());
		try {
			article.setContent(om.writeValueAsString(articleVo.getContent()));
			article.setCrowd(om.writeValueAsString(articleVo.getCrowds()));
			article.setScene(om.writeValueAsString(articleVo.getScenes()));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		article.setUpdateDate(new Date());
		article.setStatus(Const.AUDIT_ING);
		return articleMapper.updateByPrimaryKey(article) > 0;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean audit(int articleId, int status, String userId) {
		log.info("[AUDIT] -> {} audit {}, status:{}", userId, articleId, status);
		if (StringUtils.isEmpty(userId)) {
			throw new ForbiddenRequestException("userId: " + userId + " cannot audit article: " + articleId);
		}
		UserDO userDO = userService.getUserByOpenId(userId);
		if (userDO == null || userDO.getIsAdmin() != 1) {
			throw new ForbiddenRequestException("userId: " + userId + " cannot audit article: " + articleId);
		}
		return articleMapper.updateStatus(articleId, status) > 0;
	}

}
