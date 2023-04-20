package com.wallet.WalletProject.Repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wallet.WalletProject.Entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
	
	@Query("SELECT t from Transaction t WHERE ((t.fromWallet = :id  AND t.debit='debitted') OR (t.toWallet = :id AND t.credit='credited'))")
	public List<Transaction> findAllTransactionsByWalletId(Integer id);

	@Query("SELECT t from Transaction t WHERE(t.fromWallet = :id) AND (t.transactionDate BETWEEN :fromDate AND :toDate)")
	public List<Transaction> findDateWiseTransaction(Integer id, LocalDate fromDate, LocalDate toDate);
}