package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ForgetPassDto implements Serializable {
	private static final long serialVersionUID = -1853716198256100356L;
	private String username;
	private String idCard;
	private String phoneNumber;
}
