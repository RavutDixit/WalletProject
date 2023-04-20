package com.wallet.WalletProject.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.wallet.WalletProject.Entity.Transaction;
import com.wallet.WalletProject.Repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository repository;
	
	public List<Transaction> listAllTransactions(Integer id)
	{
		return repository.findAllTransactionsByWalletId(id);
	}

	public void saveTransaction(Transaction t) {
		repository.save(t);
		
	}

	public List<Transaction> listAllTransactions(Integer id, LocalDate fromDate, LocalDate toDate) {
		return repository.findDateWiseTransaction(id,fromDate,toDate);
	}
}
