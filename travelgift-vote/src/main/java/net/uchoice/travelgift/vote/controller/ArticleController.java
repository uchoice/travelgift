package net.uchoice.travelgift.vote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;

import net.uchoice.travelgift.vote.entity.Article;
import net.uchoice.travelgift.vote.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/list")
	public List<Article> all() {
		PageHelper.startPage(0, 1);
		return articleService.findAll();
	}

	
	@RequestMapping("/get/{id}")
	public Article get(@PathVariable("id") int id) {
		return articleService.get(id);
	}
	
}
