package com.daw.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateCommentDto {

	@NotEmpty
	private String content;
	@NotNull
	private Long userId;
	@NotNull
	private Long postId;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
}
