package com.cdgs.temple.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "transportations_time")
@Embeddable
@Getter
@Setter
@ToString
public class TransportationTimeEntity implements Serializable {
	private static final long serialVersionUID = 2044543602178920317L;

	@Id
	@Column(name = "tran_time_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transportationTimeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tran_time_pickup")
	private Date transportationTempleTimePickup;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tran_time_send")
	private Date transportationTempleTimeSend;
}
