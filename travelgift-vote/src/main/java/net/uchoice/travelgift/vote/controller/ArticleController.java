package net.uchoice.travelgift.vote.controller;

import java.security.InvalidParameterException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import net.uchoice.travelgift.user.domain.UserDO;
import net.uchoice.travelgift.user.service.UserService;
import net.uchoice.travelgift.vote.common.Const;
import net.uchoice.travelgift.vote.entity.Article;
import net.uchoice.travelgift.vote.service.ArticleService;
import net.uchoice.travelgift.vote.vo.ArticleDetail;
import net.uchoice.travelgift.vote.vo.ArticleVo;
import net.uchoice.travelgift.vote.vo.PageVo;

@RestController
@RequestMapping("/vote/article")
public class ArticleController {
	
	
	private static final Logger log = LoggerFactory.getLogger(ArticleController.class);


	public static final String AUTH_KEY = "authorization";

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public PageVo<ArticleVo> list(@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "1") int pageNo) {
		try {
			pageNo = pageNo == 0 ? 1 : pageNo;
			Page<ArticleVo> page = PageHelper.startPage(pageNo, size);
			PageHelper.orderBy("votes desc");
			List<ArticleVo> results = articleService.findAll();
			PageVo<ArticleVo> pageVo = new PageVo<>(page);
			pageVo.setData(results);
			return pageVo;
		} finally {
			PageHelper.clearPage();
		}
	}

	@GetMapping("/mylist")
	public PageVo<ArticleVo> mylist(@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "1") int pageNo,
			@RequestHeader(name = AUTH_KEY, required = false) String userId) {
		if (StringUtils.isEmpty(userId)) {
			return PageVo.emptyPage();
		}
		try {
			pageNo = pageNo == 0 ? 1 : pageNo;
			Page<ArticleVo> page = PageHelper.startPage(pageNo, size);
			PageHelper.orderBy("votes desc");
			Article article = new Article();
			article.setAuthor(userId);
			List<ArticleVo> results = articleService.find(article);
			PageVo<ArticleVo> pageVo = new PageVo<>(page);
			pageVo.setData(results);
			return pageVo;
		} finally {
			PageHelper.clearPage();
		}
	}

	@GetMapping("/auditlist")
	public PageVo<ArticleVo> auditlist(@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "1") int pageNo,
			@RequestHeader(name = AUTH_KEY, required = false) String userId) {
		if (StringUtils.isEmpty(userId)) {
			return PageVo.emptyPage();
		}
		UserDO userDO = userService.getUserByOpenId(userId);
		if (userDO == null || userDO.getIsAdmin() != 1) {
			log.warn("[unauditlist] user {} isn't admin", userId);
			return PageVo.emptyPage();
		}
		try {
			pageNo = pageNo == 0 ? 1 : pageNo;
			Page<ArticleVo> page = PageHelper.startPage(pageNo, size);
			PageHelper.orderBy("update_date desc");
			Article article = new Article();
			article.setStatus(Const.AUDIT_ING);
			List<ArticleVo> results = articleService.find(article);
			PageVo<ArticleVo> pageVo = new PageVo<>(page);
			pageVo.setData(results);
			return pageVo;
		} finally {
			PageHelper.clearPage();
		}
	}

	@GetMapping("/detail/{id}")
	public ArticleDetail detail(@PathVariable("id") int id,
			@RequestHeader(name = AUTH_KEY, required = false) String openId) {
		return articleService.getArticle(id, openId);
	}

	@RequestMapping("/vote")
	public boolean vote(@RequestParam int id, @RequestHeader(name = AUTH_KEY, required = false) String openId) {
		return articleService.vote(id, openId);
	}

	@PostMapping("/publish")
	public boolean publish(@RequestBody @Valid ArticleVo articleVo, BindingResult bindingResult,
			@RequestHeader(name = AUTH_KEY, required = false) String openId) {
		if (bindingResult.hasErrors()) {
			throw new InvalidParameterException(errMsg(bindingResult));
		}
		articleVo.setAuthor(openId);
		return articleService.addArticle(articleVo);
	}

	@PostMapping("/update")
	public boolean update(@RequestBody @Valid ArticleVo articleVo, BindingResult bindingResult,
			@RequestHeader(name = AUTH_KEY, required = false) String openId) {
		if (bindingResult.hasErrors()) {
			throw new InvalidParameterException(errMsg(bindingResult));
		}
		if (articleVo.getId() == null) {
			throw new InvalidParameterException("InvalidParameter: No articleId");
		}
		articleVo.setAuthor(openId);
		return articleService.updateArticle(articleVo);
	}

	@RequestMapping("/audit/pass")
	public boolean pass(@RequestParam int id, @RequestHeader(name = AUTH_KEY, required = false) String openId) {
		return articleService.audit(id, Const.AUDIT_PASS, openId);
	}

	@RequestMapping("/audit/unpass")
	public boolean unpass(@RequestParam int id, @RequestHeader(name = AUTH_KEY, required = false) String openId) {
		return articleService.audit(id, Const.AUDIT_UNPASS, openId);
	}

	private String errMsg(BindingResult bindingResult) {
		StringBuilder sb = new StringBuilder();
		bindingResult.getAllErrors().stream().forEach(o -> {
			sb.append(o.getDefaultMessage()).append("</br>");
		});
		return sb.toString();
	}

}
