package com.wallet.WalletProject.Repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wallet.WalletProject.Entity.Wallet;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class WalletRepositoryTest {

	@MockBean
	private WalletRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	@Test
	public void createWallet() {
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setStatus(1);
		wallet.setWalletBalance(0);
		
		Mockito.when(repo.createWallet(1)).thenReturn(wallet);
		
		Wallet wallet1 = repo.createWallet(1);
		assertEquals(1, wallet1.getWalletId());
	}


}
