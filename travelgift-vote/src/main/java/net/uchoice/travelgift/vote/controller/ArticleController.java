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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import net.uchoice.travelgift.vote.intercepter.AuthFilter;
import net.uchoice.travelgift.vote.service.ArticleService;
import net.uchoice.travelgift.vote.vo.ArticleDetail;
import net.uchoice.travelgift.vote.vo.ArticleVo;
import net.uchoice.travelgift.vote.vo.PageVo;

@RestController
@RequestMapping("/vote/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping("/list")
	public PageVo<ArticleVo> list(@RequestParam(defaultValue = "10") int size,
			@RequestParam int pageNo) {
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
			@RequestParam int pageNo, @RequestHeader(name = AuthFilter.AUTH_KEY) String userId) {
		try {
			pageNo = pageNo == 0 ? 1 : pageNo;
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

	@GetMapping("/detail/{id}")
	public ArticleDetail detail(@PathVariable("id") int id,
			 @RequestHeader(name = AuthFilter.AUTH_KEY) String openId) {
		return articleService.getArticle(id, openId);
	}

	@RequestMapping("/vote")
	public boolean vote(@RequestParam int id, @RequestHeader(name = AuthFilter.AUTH_KEY) String openId) {
		return articleService.vote(id, openId);
	}

	@PostMapping("/publish")
	public boolean publish(@RequestBody @Valid ArticleVo articleVo, BindingResult bindingResult,
			@RequestHeader(name = AuthFilter.AUTH_KEY) String openId) {
		if (bindingResult.hasErrors()) {
			throw new InvalidParameterException(errMsg(bindingResult));
		}
		articleVo.setAuthor(openId);
		return articleService.addArticle(articleVo);
	}

	@PostMapping("/update")
	public boolean update(@RequestBody @Valid ArticleVo articleVo, BindingResult bindingResult
			, @RequestHeader(name = AuthFilter.AUTH_KEY) String openId) {
		if (bindingResult.hasErrors()) {
			throw new InvalidParameterException(errMsg(bindingResult));
		}
		if (articleVo.getId() == null) {
			throw new InvalidParameterException("参数错误：没有文章ID");
		}
		articleVo.setAuthor(openId);
		return articleService.updateArticle(articleVo);
	}

	private String errMsg(BindingResult bindingResult) {
		StringBuilder sb = new StringBuilder();
		bindingResult.getAllErrors().stream().forEach(o -> {
			sb.append(o.getDefaultMessage()).append("</br>");
		});
		return sb.toString();
	}

}
