package com.daw.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateReportDto {

	@NotEmpty
	private String text;	
	@NotNull
	private Long commentId;
	@NotNull
	private Long userId;

	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}	
}
