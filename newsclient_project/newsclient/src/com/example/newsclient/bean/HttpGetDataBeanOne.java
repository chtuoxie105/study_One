package com.example.newsclient.bean;


public class HttpGetDataBeanOne {
	private String title;//标题
	private String digest;//内容

	private String ptime;//时间
	private String docid;//具体内容需要
	private String replyCount;//跟帖
	
	private String url;//地址
	private String source;//新华网，3张图片没有
	private String imgOne;
	private String imgsrc;//图片地址
	private String imgTwo;
	private String[] imgStringList;
	
	
	public String[] getImgStringList() {
		return imgStringList;
	}
	public void setImgStringList(String[] imgStringList) {
		this.imgStringList = imgStringList;
	}

	public String getImgTwo() {
		return imgTwo;
	}
	public void setImgTwo(String imgTwo) {
		this.imgTwo = imgTwo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getPtime() {
		return ptime;
	}
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}

	public String getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getImgOne() {
		return imgOne;
	}
	public void setImgOne(String imgOne) {
		this.imgOne = imgOne;
	}

}
