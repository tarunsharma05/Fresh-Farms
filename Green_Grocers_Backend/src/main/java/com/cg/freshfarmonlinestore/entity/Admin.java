package com.cg.freshfarmonlinestore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin extends User {

	@Column(name = "admin_id")
	private long adminId;

	private String accessLevel;
}
