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
@Table(name = "sensations")
@Embeddable
@Getter
@Setter
@ToString
public class SensationEntity implements Serializable {
	private static final long serialVersionUID = -3971704619188825868L;

	@Id
	@Column(name = "sense_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long senseId;

	@Column(name = "sense_expected")
	private String senseExpected;

	@Column(name = "sense_experience")
	private String senseExprience;
}
