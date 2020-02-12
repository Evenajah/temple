package com.cdgs.temple.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LockerIdEntity implements Serializable {
	private static final long serialVersionUID = 4407068550942794862L;

	protected Long locationId;
	protected String lockerNumber;

	public LockerIdEntity() {
	}

	public LockerIdEntity(Long locationId, String lockerNumber) {
		this.locationId = locationId;
		this.lockerNumber = lockerNumber;
	}
}
