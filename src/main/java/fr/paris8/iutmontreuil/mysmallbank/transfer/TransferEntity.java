package fr.paris8.iutmontreuil.mysmallbank.transfer;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "transfer")
public class TransferEntity {

	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
	
	@Column(name = "uidfrom")
	private String uidFrom;
	
	@Column(name = "uidto")
	private String uidTo;

	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "executiondate")
	private LocalDateTime executionDate;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUidFrom() {
		return this.uidFrom;
	}
	
	public void setUidFrom(String uidFrom) {
		this.uidFrom = uidFrom;
	}
	
	public String getUidTo() {
		return this.uidTo;
	}
	
	public void setUidTo(String uidTo) {
		this.uidTo = uidTo;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public LocalDateTime getExecutionDate() {
		return this.executionDate;
	}
	
	public void setExecutionDate(LocalDateTime executionDate) {
		this.executionDate = executionDate;
	}
}
