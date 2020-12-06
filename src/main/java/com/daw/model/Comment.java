package com.daw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
//Para que se genere automáticamente la fecha de creación
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@CreatedDate
	@Column(name = "create_date", nullable = false)
	@DateTimeFormat (iso = ISO.DATE)
	private LocalDate createDate;
	
	@Column(name = "content", length = 1000, nullable = false)
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	
	@OneToMany(mappedBy = "comment", cascade = CascadeType.PERSIST)
	private List<Report> reportingUsers;

	
	public Comment() {
		super();
		reportingUsers = new ArrayList<Report>();
	}
	public Comment(Long id, LocalDate createDate, String content, User user, Post post, List<Report> reportingUsers) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.content = content;
		this.user = user;
		this.post = post;
		this.reportingUsers = reportingUsers;
	}
	public Comment(String content, User user, Post post) {
		super();
		this.content = content;
		this.user = user;
		this.post = post;
		reportingUsers = new ArrayList<Report>();
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public List<Report> getReportingUsers() {
		return reportingUsers;
	}
	public void setReportingUsers(List<Report> reportingUsers) {
		this.reportingUsers = reportingUsers;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((post == null) ? 0 : post.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (post == null) {
			if (other.post != null)
				return false;
		} else if (!post.equals(other.post))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
