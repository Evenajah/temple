package com.cdgs.temple.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "members")
@Embeddable
@Getter
@Setter
@ToString
public class ForgetPassEntity implements Serializable {
	private static final long serialVersionUID = -7701296893488454698L;

	@Id
	@Column(name = "member_username")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberUsername;

	@Column(name = "member_id_card")
	private String memberIdCard;

	@Column(name = "member_tel")
	private String memberTel;
}
