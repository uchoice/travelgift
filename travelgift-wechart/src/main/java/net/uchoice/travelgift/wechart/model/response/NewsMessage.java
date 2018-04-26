package net.uchoice.travelgift.wechart.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import net.uchoice.travelgift.wechart.util.MessageUtil;

@XStreamAlias("xml")
public class NewsMessage extends BaseMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NewsMessage() {
		this.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	}

	@XStreamAlias("ArticleCount")
	private Integer ArticleCount = 0;

	@XStreamAlias("Articles")
	private List<Article> Articles = new ArrayList<Article>();

	public Integer getArticleCount() {
		return ArticleCount == null ? Articles.size() : ArticleCount;
	}

	public void setArticleCount(Integer articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
	
	public void addArticle(Article article) {
		Articles.add(article);
		setArticleCount(Articles.size());
	}

}
