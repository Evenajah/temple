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
@Table(name = "history_dharma")
@Embeddable
@Getter
@Setter
@ToString
public class HistoryDharmaEntity implements Serializable {
	private static final long serialVersionUID = -1422669375047737226L;

	@Id
	@Column(name = "history_dharma_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long historyDharmaId;

	@Column(name = "history_dharma_desc")
	private String historyDharmaDesc;

	@Column(name = "history_dharma_location")
	private String historyDharmaLocation;

	@Column(name = "history_dharma_member_id")
	private Long historyDharmaMemberId;
}
