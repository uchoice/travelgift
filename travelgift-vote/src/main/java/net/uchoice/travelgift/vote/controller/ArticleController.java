package net.uchoice.travelgift.vote.controller;

import java.security.InvalidParameterException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import net.uchoice.travelgift.vote.entity.Article;
import net.uchoice.travelgift.vote.intercepter.AuthFilter;
import net.uchoice.travelgift.vote.service.ArticleService;
import net.uchoice.travelgift.vote.vo.ArticleVo;
import net.uchoice.travelgift.vote.vo.PageVo;

@RestController
@RequestMapping("/vote/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping("/list")
	public PageVo<ArticleVo> list(@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "1") int pageNo) {
		try {
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
	public PageVo<ArticleVo> mylist(@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "1") int pageNo, @SessionAttribute(AuthFilter.KEY) String userId) {
		try {
			Page<ArticleVo> page = PageHelper.startPage(pageNo, size);
			PageHelper.orderBy("votes desc");
			List<ArticleVo> results = articleService.find(userId);
			PageVo<ArticleVo> pageVo = new PageVo<>(page);
			pageVo.setData(results);
			return pageVo;
		} finally {
			PageHelper.clearPage();
		}
	}

	@GetMapping("/get/{id}")
	public ArticleVo get(@PathVariable("id") int id) {
		return articleService.get(id);
	}

	@RequestMapping("/vote")
	public boolean vote(int id, @SessionAttribute(AuthFilter.KEY) String userId) {
		return articleService.vote(id, userId);
	}

	@PostMapping("/publish")
	public boolean publish(@RequestBody @Valid Article article, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidParameterException(errMsg(bindingResult));
		}
		try {
			JSONUtils.parse(article.getContent());
		} catch (Exception e) {
			throw new InvalidParameterException("参数错误：内容格式错误");
		}
		return articleService.addArticle(article);
	}

	@PostMapping("/update")
	public boolean update(@RequestBody @Valid Article article, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new InvalidParameterException(errMsg(bindingResult));
		}
		if (article.getId() == null) {
			throw new InvalidParameterException("参数错误：没有文章ID");
		}
		try {
			JSONUtils.parse(article.getContent());
		} catch (Exception e) {
			throw new InvalidParameterException("参数错误：内容格式错误");
		}
		return articleService.updateArticle(article);
	}

	private String errMsg(BindingResult bindingResult) {
		StringBuilder sb = new StringBuilder();
		bindingResult.getAllErrors().stream().forEach(o -> {
			sb.append(o.getDefaultMessage()).append("</br>");
		});
		return sb.toString();
	}

}
