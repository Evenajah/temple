package com.cdgs.temple.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProvinceDto implements Serializable {
	private static final long serialVersionUID = 7647045371881029922L;
	private Long provinceId;
	private String provinceName;
	private Long regionId;
	private String regionName;
}
