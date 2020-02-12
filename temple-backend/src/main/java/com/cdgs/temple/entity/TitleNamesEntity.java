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
@Table(name = "title_names")
@Embeddable
@Getter
@Setter
@ToString
public class TitleNamesEntity implements Serializable {
	private static final long serialVersionUID = 3445764127909844707L;

	@Id
	@Column(name = "title_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long titleId;

	@Column(name = "title_display", unique = true)
	private String titleDisplay;

	@Column(name = "title_name", unique = true)
	private String titleName;

	public TitleNamesEntity() {
		super();
	}

	public TitleNamesEntity(Long titleId, String titleDisplay, String titleName) {
		super();
		this.titleId = titleId;
		this.titleDisplay = titleDisplay;
		this.titleName = titleName;
	}
}
