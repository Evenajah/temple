package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthDto implements Serializable {

	private static final long serialVersionUID = 2258512224536230594L;
	private String username;
	private String password;
}
