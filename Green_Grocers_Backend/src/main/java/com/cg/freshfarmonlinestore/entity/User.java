package com.cg.freshfarmonlinestore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;

//	@NotBlank(message = "User name cannot be blank")
    @Size(max = 50, message = "User name cannot exceed 50 characters")
	@Column(name = "user_name", unique = true)
	private String userName;

	@NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
	@Column(name = "email", unique = true)
	private String email;

//	@NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
	@Column(name = "user_password")
	private String userPassword;

    @NotBlank(message = "Name cannot be blank")
	@Size(max = 100, message = "Name cannot exceed 100 characters")
	private String name;

	

}
