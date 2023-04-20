package com.wallet.WalletProject.Entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "wallet")
public class Wallet implements Serializable {

	@Id
	@Column(name = "wallet_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer walletId;
	
	@Column(name = "wallet_balance")
	private Integer walletBalance;
	
	@Column(name = "status")
	private Integer status;
	
	@OneToOne(mappedBy = "wallet")
	private User user;

	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public Integer getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(Integer walletBalance) {
		this.walletBalance = walletBalance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Wallet [walletId=" + walletId + ", walletBalance=" + walletBalance + ", status=" + status + ", user="
				+ user + "]";
	}
	
}
