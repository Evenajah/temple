package com.cdgs.temple.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "transportations")
@Embeddable
@Getter
@Setter
@ToString
public class TransportationEntity implements Serializable {
	private static final long serialVersionUID = -8609326581235221457L;

	@Id
	@Column(name = "tran_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transportationId;

	@Column(name = "tran_name")
	private String transportationName;

	@Column(name = "tran_time_id")
	private Long transportationTimeId;

	@Column(name = "course_id")
	private Long transportationCoursesId;

	@OneToOne
	@JoinColumn(name = "tran_time_id", insertable = false, updatable = false)
	private TransportationTimeEntity transportationTimeEntity;
}
