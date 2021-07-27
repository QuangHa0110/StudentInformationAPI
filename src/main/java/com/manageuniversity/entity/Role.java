package com.manageuniversity.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable =  false)
	private Integer id;
	
	@Column(name = "name", nullable =  false)
	private String name;
	
	@OneToMany( mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<User> users;

	@ManyToMany
	@JoinTable(
		name = "role_permission",
		joinColumns = @JoinColumn(name="role_id"),
		inverseJoinColumns = @JoinColumn(name="permission_id")
	)
	@JsonIgnore
	private Set<Permission> permissions;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	
	
	

}
