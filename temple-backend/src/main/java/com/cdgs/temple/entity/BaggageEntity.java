package com.cdgs.temple.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "baggage")
public class BaggageEntity implements Serializable {

	private static final long serialVersionUID = 7137087519797763636L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "baggage_id")
	private Long baggageId;

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "locker_id")
	private Long lockerId;

	@Column(name = "baggage_status")
	private char status;

	@Column(name = "baggage_create_date")
	@CreationTimestamp
	private LocalDateTime baggageCreateDate;

	@Column(name = "baggage_last_update")
	@CreationTimestamp
	private LocalDateTime baggageLastUpdate;

	@ManyToOne
	@JoinColumn(name = "member_id", insertable = false, updatable = false)
	private MemberEntity member;

	@ManyToOne
	@JoinColumn(name = "locker_id", insertable = false, updatable = false)
	private LockerEntity locker;

	public Long getBaggageId() {
		return baggageId;
	}

	public void setBaggageId(Long baggageId) {
		this.baggageId = baggageId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public LocalDateTime getBaggageCreateDate() {
		return baggageCreateDate;
	}

	public void setBaggageCreateDate(LocalDateTime baggageCreateDate) {
		this.baggageCreateDate = baggageCreateDate;
	}

	public LocalDateTime getBaggageLastUpdate() {
		return baggageLastUpdate;
	}

	public void setBaggageLastUpdate(LocalDateTime baggageLastUpdate) {
		this.baggageLastUpdate = baggageLastUpdate;
	}

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public Long getLockerId() {
		return lockerId;
	}

	public void setLockerId(Long lockerId) {
		this.lockerId = lockerId;
	}

	public LockerEntity getLocker() {
		return locker;
	}

	public void setLocker(LockerEntity locker) {
		this.locker = locker;
	}

	@Override
	public String toString() {
		return "BaggageEntity [baggageId=" + baggageId + ", memberId=" + memberId + ", lockerId=" + lockerId
				+ ", status=" + status + ", baggageCreateDate=" + baggageCreateDate + ", baggageLastUpdate="
				+ baggageLastUpdate + ", member=" + member + ", locker=" + locker + "]";
	}

}
