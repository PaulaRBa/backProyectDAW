package com.daw.dtos;

import java.time.LocalDate;

public class PostDto {

	private Long id;
	private String title;
	private LocalDate createDate;			
	private LocalDate eventDate;	
	private String eventName;	
	private String content;	
	private int punctuaction;
	private String categoryName;	
	private String userAlias;
	//Imagen almacenada en base64
	private String picture;
	
	
	public PostDto() {
		super();
	}
	public PostDto(Long postId, String title, LocalDate postDate, LocalDate eventDate, String eventName, String content, int punctuaction, String categoryName, String userAlias, String picture) {
		super();
		this.id = postId;
		this.title = title;
		this.createDate = postDate;
		this.eventDate = eventDate;
		this.eventName = eventName;
		this.content = content;
		this.punctuaction = punctuaction;
		this.categoryName = categoryName;
		this.userAlias = userAlias;
		this.picture = picture;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long postId) {
		this.id = postId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate postDate) {
		this.createDate = postDate;
	}
	public LocalDate getEventDate() {
		return eventDate;
	}
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPunctuaction() {
		return punctuaction;
	}
	public void setPunctuaction(int punctuaction) {
		this.punctuaction = punctuaction;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getUserAlias() {
		return userAlias;
	}
	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
}
