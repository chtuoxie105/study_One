package com.example.newsclient.bean;

public class VideoBean {
	private String title;//标题
	private String description;//内容介绍
	private String playCount;//播放次数
	private String cover;//引导图片
	private String mp4_url;//地址
	private String replyCount;//跟帖人数
	private int length;//时间354分钟
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlayCount() {
		return playCount;
	}
	public void setPlayCount(String playCount) {
		this.playCount = playCount;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getMp4_url() {
		return mp4_url;
	}
	public void setMp4_url(String mp4_url) {
		this.mp4_url = mp4_url;
	}
	public String getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
}
