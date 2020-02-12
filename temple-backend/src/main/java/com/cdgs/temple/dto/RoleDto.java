package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RoleDto implements Serializable {
	private static final long serialVersionUID = -1928530578040668271L;
	private Long id;
	private String name;
}
