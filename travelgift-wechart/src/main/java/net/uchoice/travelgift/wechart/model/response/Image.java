package net.uchoice.travelgift.wechart.model.response;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XStreamAlias("MediaId")
	private String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
