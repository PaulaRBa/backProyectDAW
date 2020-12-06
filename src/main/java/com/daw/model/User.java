package com.daw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
//Para que se generen automáticamente las fechas de creación y actualización
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")

/*
 * Implementamos la interfaz UserDetails, de tal forma que nuestro modelo de usuario
 * está más integrado con Spring Security y así, cuando en algún punto de la aplicación,
 * obtengamos el Authentication que está alojado dentro de nuestro contexto de seguridad,
 * podremos extraer de él nuestro modelo de usuario, y podremos así extraer toda la
 * información que hayamos almacenado en él, sin tener que, en base al id o al nombre de
 * usuario, irnos de nuevo a la bb.dd a rescatarlos.
 * 
 * Si no implementaramos esta interfaz, cuando implementamos en el servicio la interfaz
 * UserDetailsService y sobreescribimos el método loadUserByUsername, que tiene que devolver
 * un objeto UserDetails, tendríamos que hacer una conversión de nuestra entidad
 */

public class User implements UserDetails {
	
	private static final long serialVersionUID = 8741368430156155951L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", length = 20, nullable = false)
	private String name;
	
	@Column(name = "surnames", length = 40, nullable = false)
	private String surnames;
	
	@Column(name = "alias", length = 20, unique = true, nullable = false)
	private String alias;
	
	@Column(name = "email", length = 100, nullable = false)
	private String email;
	
	@Column(name = "password", length = 100, nullable = false)
	private String password;
	
	@Basic (fetch = FetchType.LAZY)
	@Lob
	@Column (name = "picture", nullable = true)
	private String profilePicture;
	
	@CreatedDate
	@Column(name = "registration_date", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate registrationDate;
	
	@LastModifiedDate
	@Column(name = "update_date", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate updateDate;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<UserRole> roles;
	
	@OneToMany(mappedBy = "user")
	private List<Post> posts;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Report> reports;

	
	public User() {
		super();
		roles = new ArrayList<UserRole>();
		posts = new ArrayList<Post>();
		comments = new ArrayList<Comment>();
		reports = new ArrayList<Report>();
	}
	public User(Long id, String name, String surnames, String alias, String email, String password,
			String profilePicture, LocalDate registrationDate, LocalDate updateDate, List<UserRole> roles,
			List<Post> posts, List<Comment> comments, List<Report> reports) {
		super();
		this.id = id;
		this.name = name;
		this.surnames = surnames;
		this.alias = alias;
		this.email = email;
		this.password = password;
		this.profilePicture = profilePicture;
		this.registrationDate = registrationDate;
		this.updateDate = updateDate;
		this.roles = roles;
		this.posts = posts;
		this.comments = comments;
		this.reports = reports;
	}

	public User(Long id, String name, String surnames, String alias, String email, String password, String profilePicture,
			List<UserRole> roles, List<Post> posts, List<Comment> comments,
			List<Report> reports) {
		super();
		this.id = id;
		this.name = name;
		this.surnames = surnames;
		this.alias = alias;
		this.email = email;
		this.password = password;
		this.profilePicture = profilePicture;
		roles = new ArrayList<UserRole>();
		posts = new ArrayList<Post>();
		comments = new ArrayList<Comment>();
		reports = new ArrayList<Report>();
	}
	public User(Long id, String name, String surnames, String alias, String email, String password,
			String profilePicture) {
		super();
		this.id = id;
		this.name = name;
		this.surnames = surnames;
		this.alias = alias;
		this.email = email;
		this.password = password;
		this.profilePicture = profilePicture;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurnames() {
		return surnames;
	}
	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}	
	public LocalDate getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}
	public List<UserRole> getRoles() {
		return roles;
	}
	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Report> getReports() {
		return reports;
	}
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	
	
	 public Role getRole() {
		 return roles.get(0).getRole();
	}
	 
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((surnames == null) ? 0 : surnames.hashCode());
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
		User other = (User) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (surnames == null) {
			if (other.surnames != null)
				return false;
		} else if (!surnames.equals(other.surnames))
			return false;
		return true;
	}
	
	
	//Métodos que necesitamos sobreescribir de la interfaz UserDetails
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role -> {
			if (role.getRole().getName().equalsIgnoreCase("admin")) {
				return new SimpleGrantedAuthority("ROLE_ADMIN");
			}
			else {
				return new SimpleGrantedAuthority("ROLE_USER");
			}
		}).collect(Collectors.toList());
	}
	
	@Override
	public String getUsername() {
		return alias;
	}
	
	/*
	 * No se gestionan los supuestos de: cuenta expirada, cuenta bloqueada,
	 * cuenta no permitida o credenciales expirados, por lo tanto los métodos
	 * relativos a estas comprobaciones nos devuelven siempre true (en otro caso,
	 * tendríamos que dar cuerpo a estos métodos)
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
