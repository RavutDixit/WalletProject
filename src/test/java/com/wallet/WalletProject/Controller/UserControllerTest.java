package com.wallet.WalletProject.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wallet.WalletProject.Entity.Transaction;
import com.wallet.WalletProject.Entity.User;
import com.wallet.WalletProject.Entity.Wallet;
import com.wallet.WalletProject.Exception.UserNotFoundException;
import com.wallet.WalletProject.Exception.WalletNotFoundException;
import com.wallet.WalletProject.Repository.WalletRepository;
import com.wallet.WalletProject.Service.TransactionService;
import com.wallet.WalletProject.Service.UserService;
import com.wallet.WalletProject.Service.WalletService;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserControllerTest {

	@MockBean
	private UserService uservice;
	
	@MockBean
	private WalletService walser;
	
	@MockBean
	private TransactionService tservice;
	
	@MockBean
	private WalletRepository repo;
	
	@Test
	public void viewHomePage()
	{
		User user = new User();
		user.setId(1);
		user.setFirstName("vinayak");
		user.setPassword("123");
		
		User user1 = new User();
		user1.setId(2);
		user1.setFirstName("adarsh");
		user1.setPassword("123");
		
		List<User> list = new ArrayList<User>();
		list.add(user);
		list.add(user1);
		
		Mockito.when(uservice.listAll()).thenReturn(list);
		List<User> list1 = uservice.listAll();
		assertEquals(2, list1.size());
		
	}
	
	@Test
	public void viewLoginPage()
	{

		User user = new User();
		user.setId(1);
		user.setUsername("vinayak");
		user.setPassword("123");
		

		Mockito.when(uservice.userLogin(user.getUsername(), user.getPassword())).thenReturn(user);
		User user1= uservice.userLogin(user.getUsername(), user.getPassword());
		assertEquals("123", user1.getPassword());
	}
	
	@Test
	public void saveRegister()
	{

		User user = new User();
		user.setId(1);
		user.setUsername("vinayak");
		user.setPassword("123");
		
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
		
		Wallet wallet1= walser.createWallet();
		
		user.setWallet(wallet1);
	
		uservice.save(user);
		verify(uservice, times(1)).save(user);
	}
	
	@Test
	public void saveUser()
	{

		User user = new User();
		user.setId(1);
		user.setUsername("vinayak");
		user.setPassword("123");
		
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
//		Mockito.when(repo.createWallet(0)).thenReturn(wallet);
//		Wallet wallet1= walser.createWallet();
	
		user.setWallet(wallet);
		
//		wallet1.setWalletId(1);
//		wallet1.setWalletBalance(100);
	
		uservice.save(user);
		verify(uservice, times(1)).save(user);
		assertEquals(100, user.getWallet().getWalletBalance());

	}
	
	@Test
	public void saveEditUser() throws WalletNotFoundException
	{

		User user = new User();
		user.setId(1);
		user.setUsername("vinayak");
		user.setPassword("123");
		
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
		
	//	Wallet wallet1 = walser.get(wallet.getWalletId());
	
		user.setWallet(wallet);

		uservice.save(user);
		verify(uservice, times(1)).save(user);
		assertEquals(100, user.getWallet().getWalletBalance());

	}
	
	@Test
	public void deleteUser() throws WalletNotFoundException, UserNotFoundException
	{

		User user = new User();
		user.setId(1);
		user.setUsername("vinayak");
		user.setPassword("123");

		uservice.delete(1);
		verify(uservice, times(1)).delete(1);
		//assertEquals(100, user.getWallet().getWalletBalance());

	}
	
	@Test
	public void addMoney() throws WalletNotFoundException, UserNotFoundException
	{

		User user = new User();
		user.setId(1);
		user.setUsername("vinayak");
		user.setPassword("123");
		
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
		
	//	Wallet wallet1 = walser.get(wallet.getWalletId());
	
		user.setWallet(wallet);
		wallet.setUser(user);

		Mockito.when(uservice.get(1)).thenReturn(user);
		User user1 = uservice.get(1);
		assertEquals("vinayak", user1.getUsername());
		
		Mockito.when(walser.get(1)).thenReturn(wallet);
		Wallet wallet1 = walser.get(1);
		assertEquals(100, wallet1.getWalletBalance());
		
		Transaction t = new Transaction();
		t.setFromWallet(1);
		t.setAmount(100);
		t.setDebit("debitted");
		
		tservice.saveTransaction(t);
		verify(tservice, times(1)).saveTransaction(t);
		assertEquals(100, t.getAmount());

	}
	
	@Test
	public void withdrawMoney() throws WalletNotFoundException, UserNotFoundException
	{

		User user = new User();
		user.setId(1);
		user.setUsername("vinayak");
		user.setPassword("123");
		
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
		
	//	Wallet wallet1 = walser.get(wallet.getWalletId());
	
		user.setWallet(wallet);
		wallet.setUser(user);

		Mockito.when(uservice.get(1)).thenReturn(user);
		User user1 = uservice.get(1);
		assertEquals("vinayak", user1.getUsername());
		
		Mockito.when(walser.get(1)).thenReturn(wallet);
		Wallet wallet1 = walser.get(1);
		assertEquals(100, wallet1.getWalletBalance());
		
		Transaction t = new Transaction();
		t.setFromWallet(1);
		t.setAmount(100);
		t.setDebit("debitted");
		
		tservice.saveTransaction(t);
		verify(tservice, times(1)).saveTransaction(t);
		assertEquals(100, t.getAmount());

	}
	
	@Test
	public void sendMoney() throws WalletNotFoundException, UserNotFoundException
	{

		User user = new User();
		user.setId(1);
		user.setUsername("vinayak");
		user.setPassword("123");
		
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
		
//		Wallet wallet1 = walser.get(wallet.getWalletId());
	
		user.setWallet(wallet);
		wallet.setUser(user);

		Mockito.when(uservice.get(1)).thenReturn(user);
		User user1 = uservice.get(1);
		assertEquals("vinayak", user1.getUsername());
		
		Mockito.when(walser.get(1)).thenReturn(wallet);
		Wallet wallet1 = walser.get(1);
		assertEquals(100, wallet1.getWalletBalance());
		
		Transaction t = new Transaction();
		t.setFromWallet(1);
		t.setAmount(100);
		t.setDebit("debitted");
		
		tservice.saveTransaction(t);
		verify(tservice, times(1)).saveTransaction(t);
		assertEquals(100, t.getAmount());

	}
	
	
	@Test
	public void viewTransactionHistrory() throws UserNotFoundException, WalletNotFoundException
	{
		
		User user = new User();
		user.setId(1);
		user.setUsername("vinayak");
		user.setPassword("123");
		
		Wallet wallet = new Wallet();
		wallet.setWalletId(1);
		wallet.setWalletBalance(100);
	
		user.setWallet(wallet);
		wallet.setUser(user);

		Mockito.when(uservice.get(1)).thenReturn(user);
		User user1 = uservice.get(1);
		assertEquals("vinayak", user1.getUsername());
		
		Mockito.when(walser.get(1)).thenReturn(wallet);
		Wallet wallet1 = walser.get(1);
		assertEquals(100, wallet1.getWalletBalance());
		
		
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
		
		Mockito.when(tservice.listAllTransactions(1)).thenReturn(list);
		List<Transaction> t2 = tservice.listAllTransactions(1);
		assertEquals(2, t2.size());
		
	}
	
	@Test
	public void exportToExcel()
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
		
		Mockito.when(tservice.listAllTransactions(1)).thenReturn(list);
		List<Transaction> t2 = tservice.listAllTransactions(1);
		assertEquals(2, t2.size());
		
	}
}
