package com.reactive.store.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professor {
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String phoneNumber;
	private String city;
	private String address;
	private String photo;
	private String specialty;
	private String aboutMe;
}
