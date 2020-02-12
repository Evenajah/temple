package com.cdgs.temple.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "members")
@Getter
@Setter
@ToString
public class MemberEntity implements Serializable {
	private static final long serialVersionUID = -6122881300354684546L;

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(name = "member_username", unique = true)
	private String memberUsername;

	@Column(name = "member_password")
	private String memberPassword;

	@Column(name = "member_fname")
	private String memberFname;

	@Column(name = "member_lname")
	private String memberLname;

	@Column(name = "member_address")
	private String memberAddress;

	@Column(name = "member_tel")
	private String memberTel;

	@Column(name = "member_emergency_tel")
	private String memberEmergencyTel;

	@Column(name = "member_email")
	private String memberEmail;

	@Lob
	@Column(name = "member_img")
	private byte[] memberImg;

	@Column(name = "member_register_date")
	@CreationTimestamp
	private LocalDateTime memberRegisterDate;

	@Column(name = "member_last_update")
	@CreationTimestamp
	private LocalDateTime memberLastUpdate;

	@Column(name = "member_gender_id")
	private Long memberGenderId;

	@Column(name = "member_role_id")
	private Long memberRoleId;

	@Column(name = "member_title_id")
	private Long memberTitleId;

	@Column(name = "member_job")
	private String memberJob;

	@Column(name = "member_other")
	private String memberOther;

	@Column(name = "member_blood")
	private String memberBlood;

	@Column(name = "member_allergy_food")
	private String memberAllergyFood;

	@Column(name = "member_allergy_medicine")
	private String memberAllergyMedicine;

	@Column(name = "member_disease")
	private String memberDisease;

	@Column(name = "member_emer_name")
	private String memberEmerName;

	@Column(name = "member_emer_relationship")
	private String memberEmerRelationship;

	@Column(name = "member_province_id")
	private Long memberProvinceId;

	@Column(name = "member_postal_code")
	private String memberPostalCode;

	@Column(name = "member_id_card")
	private String memberIdCard;

	@Column(name = "member_age")
	private Long memberAge;

	@Column(name = "member_ordian_number")
	private Long ordianNumber;

	@Column(name = "member_ordian_date")
	private LocalDateTime ordianDate;

	@ManyToOne
	@JoinColumn(name = "member_gender_id", insertable = false, updatable = false)
	private GenderEntity gender;

	@ManyToOne
	@JoinColumn(name = "member_role_id", insertable = false, updatable = false)
	private RoleEntity role;

	@ManyToOne
	@JoinColumn(name = "member_title_id", insertable = false, updatable = false)
	private TitleNamesEntity titleName;

	@ManyToOne
	@JoinColumn(name = "member_province_id", insertable = false, updatable = false)
	private ProvinceEntity province;
}
