package com.daw.dtos;

import java.time.LocalDate;

public class ReportDto {

	private Long id;
	private LocalDate createDate;
	private String text;
	private String userAlias;	
	private Long commentId;
	
	
	public ReportDto() {
		super();
	}
	public ReportDto(Long id, LocalDate createDate, String text, String userAlias, Long commentId) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.text = text;
		this.userAlias = userAlias;
		this.commentId = commentId;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUserAlias() {
		return userAlias;
	}
	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
}
