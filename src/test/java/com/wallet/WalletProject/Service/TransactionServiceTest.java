package com.wallet.WalletProject.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wallet.WalletProject.Entity.Transaction;
import com.wallet.WalletProject.Repository.TransactionRepository;

@SpringBootTest
class TransactionServiceTest {

	@MockBean
	private TransactionRepository repo;
	
	@Autowired
	private TransactionService service;
	
	@Test
	public void listAllTransactions()
	{
		
		Transaction t = new Transaction();
		t.setAmount(100);
		t.setFromWallet(1);
		t.setTransactionId(1);
		t.setDebit("debitted");
		
		Transaction t1 = new Transaction();
		t1.setAmount(1000);
		t1.setFromWallet(2);
		t1.setTransactionId(2);
	
		List<Transaction> list = new ArrayList<Transaction>();
		list.add(t);
		list.add(t1);
		
		Mockito.when(repo.findAllTransactionsByWalletId(t.getFromWallet())).thenReturn(list);
		List<Transaction> t2 = service.listAllTransactions(1);
		assertEquals(2, t2.size());
		
	}
	
	@Test
	public void saveTransaction()
	{

		Transaction t = new Transaction();
		t.setAmount(100);
		t.setFromWallet(1);
		t.setTransactionId(1);
		t.setDebit("debitted");
		
		service.saveTransaction(t);
		verify(repo, times(1)).save(t);
	}

}
