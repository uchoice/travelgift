package net.uchoice.travelgift.vote.vo;

public class ArticleDetail {

	private ArticleVo article;

	private boolean canVote;

	public ArticleDetail() {
	}

	public ArticleDetail(ArticleVo article, boolean canVote) {
		this.article = article;
		this.canVote = canVote;
	}

	public ArticleVo getArticle() {
		return article;
	}

	public void setArticle(ArticleVo article) {
		this.article = article;
	}

	public boolean isCanVote() {
		return canVote;
	}

	public void setCanVote(boolean canVote) {
		this.canVote = canVote;
	}

}
