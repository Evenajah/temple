package com.cdgs.temple.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class GraduatedEntity implements Serializable {
	private static final long serialVersionUID = 6171146455155425873L;
	@Id
	private Long membersHasCourseId;
	private Long memberId;
	private Long courseId;
	private String courseName;
	private String displayName;
	private char mhcStatus;
}
