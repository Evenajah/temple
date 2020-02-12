package com.cdgs.temple.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "province")
@Embeddable
@Getter
@Setter
@ToString
public class ProvinceEntity implements Serializable {
	private static final long serialVersionUID = 6625697339055110519L;

	@Id
	@Column(name = "province_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long provinceId;

	@Column(name = "province_desc", unique = true)
	private String provinceName;

	@Column(name = "region_id", unique = true)
	private Long regionId;

	@ManyToOne
	@JoinColumn(name = "region_id", insertable = false, updatable = false)
	private RegionEntity region;

	public ProvinceEntity() {
		super();
	}

	public ProvinceEntity(Long provinceId, String provinceName, Long regionId) {
		super();
		this.provinceId = provinceId;
		this.provinceName = provinceName;
		this.regionId = regionId;
	}
}
