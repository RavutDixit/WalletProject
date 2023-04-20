package com.wallet.WalletProject.Entity;


import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;


@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@Column(name = "transaction_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;
	
	@Column(name = "transaction_date")
	private LocalDate transactionDate;
	
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name="fromwalletid")
	private Integer fromWallet;
	
	@Column(name="towalletid")
	private Integer toWallet;
	
	@Column(name="debit")
	private String debit;
	
	@Column(name="credit")
	private String credit;
	
	public Transaction()
	{
		
	}
	
	public Integer getFromWallet() {
		return fromWallet;
	}

	public void setFromWallet(Integer fromWallet) {
		this.fromWallet = fromWallet;
	}

	public Integer getToWallet() {
		return toWallet;
	}

	public void setToWallet(Integer toWallet) {
		this.toWallet = toWallet;
	}


	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", transactionDate=" + transactionDate + ", amount="
				+ amount + ", fromWallet=" + fromWallet + ", toWallet=" + toWallet + ", debit=" + debit + ", credit="
				+ credit + "]";
	}

	


}
