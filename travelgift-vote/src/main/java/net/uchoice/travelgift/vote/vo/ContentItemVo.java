package net.uchoice.travelgift.vote.vo;

import java.io.Serializable;

public class ContentItemVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String pic;

	private String desc;

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
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
		sb.append(", pic=").append(pic);
		sb.append(", desc=").append(desc);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}

}
