package com.daw.dtos;

import java.time.LocalDate;

public class CommentDto {

	private Long id;
	private LocalDate createDate;
	private String content;
	private String userAlias;
	private Long postId;
	
	
	public CommentDto() {
		super();
	}
	public CommentDto(Long id, LocalDate createDate, String content, String userAlias, Long postId) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.content = content;
		this.userAlias = userAlias;
		this.postId = postId;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserAlias() {
		return userAlias;
	}
	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
}
