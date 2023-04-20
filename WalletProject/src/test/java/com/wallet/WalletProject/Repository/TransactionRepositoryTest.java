package com.wallet.WalletProject.Repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wallet.WalletProject.Entity.Transaction;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TransactionRepositoryTest {
	

	@MockBean
	private TransactionRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	@Test
	public void findAllTransactionsByWalletId() {

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
		List<Transaction> t2 = repo.findAllTransactionsByWalletId(1);
		assertEquals(2, t2.size());
	

      }
}
