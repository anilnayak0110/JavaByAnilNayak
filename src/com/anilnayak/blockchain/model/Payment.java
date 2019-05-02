package com.anilnayak.blockchain.model;

public class Payment {
	private int txn_id;
	private String debitorName;
	private String debitorAccNo;
	private float amount;
	private String creditorName;
	private String creditorAccNo;
	private String txnDate;
	private String txnId;

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public int getTxn_id() {
		return txn_id;
	}

	public void setTxn_id(int txn_id) {
		this.txn_id = txn_id;
	}

	public String getDebitorName() {
		return debitorName;
	}

	public void setDebitorName(String debitorName) {
		this.debitorName = debitorName;
	}

	public String getDebitorAccNo() {
		return debitorAccNo;
	}

	public void setDebitorAccNo(String debitorAccNo) {
		this.debitorAccNo = debitorAccNo;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getCreditorName() {
		return creditorName;
	}

	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}

	public String getCreditorAccNo() {
		return creditorAccNo;
	}

	public void setCreditorAccNo(String creditorAccNo) {
		this.creditorAccNo = creditorAccNo;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	@Override
	public String toString() {
		return "Payment [txn_id=" + txn_id + ", debitorName=" + debitorName
				+ ", debitorAccNo=" + debitorAccNo + ", amount=" + amount
				+ ", creditorName=" + creditorName + ", creditorAccNo="
				+ creditorAccNo + ", txnDate=" + txnDate + ", txnId=" + txnId
				+ "]";
	}

	

}
