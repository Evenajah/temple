package com.cdgs.temple.entity;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "special_approve")
@Embeddable
@Getter
@Setter
@ToString
public class SpecialApproveEntity implements Serializable {
	private static final long serialVersionUID = -4780915762853262556L;

	@Id
	@Column(name = "special_approve_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long specialApproveId;

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "course_id")
	private Long courseId;

	@Column(name = "spa_detail")
	private String spaDetail;

	@Column(name = "spa_status")
	private String spaStatus;

	@Column(name = "create_date")
	@CreationTimestamp
	private LocalDateTime createDate;

	@Column(name = "last_update")
	@CreationTimestamp
	private LocalDateTime lastUpdate;

	@Column(name = "sense_id")
	private Long senseId;

	@Column(name = "tran_id")
	private Long tranId;

	@ManyToOne
	@JoinColumn(name = "tran_id", insertable = false, updatable = false)
	private TransportationEntity transpotation;
}
