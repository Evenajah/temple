package com.cdgs.temple.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "gender")
@Embeddable
@Getter
@Setter
@ToString
public class GenderEntity implements Serializable {
	private static final long serialVersionUID = -674732870527105399L;

	@Id
	@Column(name = "gender_id")
	private Long genderId;

	@Column(name = "gender_name")
	private String genderName;
}
