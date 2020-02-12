package com.cdgs.temple.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "members")
@Getter
@Setter
@ToString
@SecondaryTable(name = "gender", pkJoinColumns = @PrimaryKeyJoinColumn(name = "gender_id"))
// pkJoinColumns = @PrimaryKeyJoinColumn(name = "gender_id",referencedColumnName = "member_gender_id")
public class MapEntity implements Serializable {
	private static final long serialVersionUID = -6122881300354684546L;

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "member_fname")
	private String memberFname;

	@Column(name = "member_lname")
	private String memberLname;

	@Id
	@Column(name = "member_gender_id")
	private Long memberGenderId;

	@Column(name = "gender_name", table = "gender")
	private String genderName;
}
