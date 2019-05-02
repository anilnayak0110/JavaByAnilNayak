package com.anilnayak.blockchain.model;

import java.util.Date;

import com.anilnayak.blockchain.security.StringUtil;


public class Block {
	
	public String currentHashValue;
	public String previousHashValue;
	public String data;
	public String debitorName;
	public String creditorName;
	public String transactionDate;
	public long timeStamp;
	private int nonce;
	public String getDebitorName() {
		return debitorName;
	}
	public void setDebitorName(String debitorName) {
		this.debitorName = debitorName;
	}
	public String getCreditorName() {
		return creditorName;
	}
	public void setCreditorName(String creditorName) {
		this.creditorName = creditorName;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	private float amount;
	
	public Block(){
		
	}
	public Block(String data,String previousHashValue) {
		this.previousHashValue = previousHashValue;
		this.data = data;
		this.timeStamp = new Date().getTime();
		this.currentHashValue = calculateHash();
	}
	
	
	/*public String calculateHashWithoutMine() {
		String calculatedhash = StringUtil.applySha256( 
				previousHashValue +
				Long.toString(timeStamp) +
				data 
				);
		
		return calculatedhash;
	}
	*/
	
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHashValue +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				data 
				);
		//System.out.println("Calculated Hash:::"+calculatedhash);
		return calculatedhash;
	}
	
	public String mineBlock(int difficulty) {
		
	
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		 
		
		while(!currentHashValue.substring( 0, difficulty).equals(target)) {
			nonce ++;
			currentHashValue = calculateHash();
			
			
		}
		
		System.out.println("Block Mined!!! : " + currentHashValue);
		return currentHashValue;
	}
	public String getCurrentHashValue() {
		return currentHashValue;
	}
	public void setCurrentHashValue(String currentHashValue) {
		this.currentHashValue = currentHashValue;
	}
	public String getPreviousHashValue() {
		return previousHashValue;
	}
	public void setPreviousHashValue(String previousHashValue) {
		this.previousHashValue = previousHashValue;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public int getNonce() {
		return nonce;
	}
	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Block [currentHashValue=" + currentHashValue
				+ ", previousHashValue=" + previousHashValue + ", data=" + data
				+ ", debitorName=" + debitorName + ", creditorName="
				+ creditorName + ", transactionDate=" + transactionDate
				+ ", timeStamp=" + timeStamp + ", nonce=" + nonce + ", amount="
				+ amount + "]";
	}
	


	


}
