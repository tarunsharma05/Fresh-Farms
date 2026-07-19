package com.cg.freshfarmonlinestore.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class APIException extends RuntimeException {
	
	private HttpStatus status;
	private String message;

}
