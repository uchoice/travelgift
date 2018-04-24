package net.uchoice.travelgift.vote.vo;

import java.io.Serializable;
import java.util.List;

public class ContentItemVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<String> pics;

	private String desc;

	public List<String> getPics() {
		return pics;
	}

	public void setPics(List<String> pics) {
		this.pics = pics;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", pics=").append(pics);
		sb.append(", desc=").append(desc);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}

}
