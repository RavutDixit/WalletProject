package com.wallet.WalletProject.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wallet.WalletProject.Entity.User;
import com.wallet.WalletProject.Entity.Wallet;
import com.wallet.WalletProject.Exception.WalletNotFoundException;
import com.wallet.WalletProject.Repository.WalletRepository;

@SpringBootTest
class WalletServiceTest {

	@MockBean
	private WalletRepository repo;
	
	@Autowired
	private WalletService service;
	
	@Test
	public void save()
	{
		
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
		
		service.save(wallet);
		verify(repo, times(1)).save(wallet);
	}
	
	@Test
	public void createWallet()
	{
		
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
	
		
		Mockito.when(repo.createWallet(0)).thenReturn(wallet);
		Wallet wallet1= service.createWallet();
		assertEquals(100, wallet1.getWalletBalance());
	}
	
	@Test
	public void delete()
	{
		
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
		
		service.delete(1);;
		verify(repo, times(1)).deleteById(1);
	}
	
	@Test
	public void get() throws WalletNotFoundException
	{
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
		
//		repo.findById(1);
//		verify(repo, times(1)).findById(1);
		
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(wallet));
		Wallet wallet1 = service.get(1);
		assertEquals(100, wallet1.getWalletBalance());

	}
	
}
