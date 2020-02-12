package com.cdgs.temple.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "lockers")
@Getter
@Setter
@ToString
public class LockerEntity implements Serializable {
	private static final long serialVersionUID = 4407068550942794862L;

	@Id
	@Column(name = "locker_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lockerId;

	@Column(name = "location_id")
	private Long locationId;

	@Column(name = "locker_number")
	private String lockerNumber;

	@Column(name = "locker_create_by")
	private Long lockerCreateBy;

	@Column(name = "locker_status")
	private char isActive;

	@Column(name = "locker_enable")
	private boolean enable;

	@Column(name = "locker_last_update")
	@CreationTimestamp
	private LocalDateTime lockerLastUpdate;

	@Column(name = "locker_create_date")
	@CreationTimestamp
	private LocalDateTime lockerCreateDate;

	@ManyToOne
	@JoinColumn(name = "location_id", insertable = false, updatable = false)
	private LocationEntity location;
}
