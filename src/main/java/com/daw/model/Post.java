package com.daw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private Long id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@CreatedDate
	@Column(name = "create_date", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate createDate;	
	
	@Column(name = "event_date", nullable = true)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate eventDate;
	
	@Column(name = "event_name", length = 60, nullable = true)
	private String eventName;
	
	@Column(name = "content", length = 2000, nullable = false)
	private String content;
	
	//Guardo la imagen como un string porque la almaceno en bb.dd. en base64
	@Basic (fetch = FetchType.LAZY)
	@Lob
	@Column (name = "picture", nullable = true)
	private String picture;
	
	@Column (name = "punctuaction", length = 10, nullable = true)
	private int punctuaction;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
	private List<Comment> commentingUsers;
	
	
	public Post() {
		super();
		commentingUsers = new ArrayList<Comment>();
	}	
	public Post(Long id, String title, LocalDate postDate, LocalDate eventDate, String eventName, String content,
			String picture, int punctuaction, Category category, User user, List<Comment> commentingUsers) {
		super();
		this.id = id;
		this.title = title;
		this.createDate = postDate;
		this.eventDate = eventDate;
		this.eventName = eventName;
		this.content = content;
		this.picture = picture;
		this.punctuaction = punctuaction;
		this.category = category;
		this.user = user;
		this.commentingUsers = commentingUsers;
	}
	public Post(String title, LocalDate postDate, LocalDate eventDate, String eventName, String content, Category category, User user) {
		super();
		this.title = title;
		this.createDate = postDate;
		this.eventDate = eventDate;
		this.eventName = eventName;
		this.content = content;
		this.category = category;
		this.user = user;
		commentingUsers = new ArrayList<Comment>();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getPunctuaction() {
		return punctuaction;
	}
	public void setPunctuaction(int punctuaction) {
		this.punctuaction = punctuaction;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	/* Creo que ya no lo necesito
	 * public void setCategoryId(long id) {
		if (category == null) category = new Category();
		category.setId(id);
	}*/
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Comment> getCommentingUsers() {
		return commentingUsers;
	}
	public void setCommentingUsers(List<Comment> commentingUsers) {
		this.commentingUsers = commentingUsers;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + punctuaction;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Post other = (Post) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
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
		if (punctuaction != other.punctuaction)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
