package com.wallet.WalletProject.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wallet.WalletProject.Entity.User;
import com.wallet.WalletProject.Exception.NullPointerException;
import com.wallet.WalletProject.Exception.UserNotFoundException;
import com.wallet.WalletProject.Repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@MockBean
	private UserRepository repo;
	
	@Autowired
	private UserService service;
	
	@Test
	public void listAll()
	{
		
		User user = new User();
		user.setId(1);
		user.setFirstName("vinayak");
		
		User user1 = new User();
		user1.setId(2);
		user1.setFirstName("adarsh");
		
		List<User> list = new ArrayList<User>();
		list.add(user);
		list.add(user1);
		
		Mockito.when(repo.findAll()).thenReturn(list);
		List<User> list1 =service.listAll();
		assertEquals(2, list1.size());
		
	}
	
	@Test
	public void save()
	{

		User user = new User();
		user.setId(1);
		user.setFirstName("vinayak");
		user.setPassword("123");
		
		service.save(user);
		verify(repo, times(1)).save(user);
	}

	@Test
	public void get() throws UserNotFoundException
	{
		User user = new User();
		user.setId(1);
		user.setFirstName("vinayak");
		user.setPassword("123");
		service.save(user);
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(user));
		User user1 = service.get(1);
		assertEquals("vinayak", user1.getFirstName());

	}
	
	@Test
	public void getCurrentUser() throws NullPointerException
	{

		User user = new User();
		user.setId(1);
		user.setFirstName("vinayak");
		
		Mockito.when(repo.getUserByUsername(user.getFirstName())).thenReturn(user);
		
		assertEquals("vinayak", user.getFirstName());
	   
	}
	

	@Test
	public void delete() throws UserNotFoundException
	{

		User user = new User();
		user.setId(1);
		user.setFirstName("vinayak");
		user.setPassword("123");
		
//		doReturn(user).when(repo.findById(1));
		Mockito.when(repo.findById(1)).thenReturn(Optional.of(user));
		
		 service.delete(user.getId());
		 assertEquals("vinayak", user.getFirstName());
		 verify(repo, times(1)).save(user);
//		service.save(user);
//		 verify(repo, times(1)).findById(1).get();
//		verify(repo, times(1)).save(user);
	}
	
	@Test
	public void saveEdit()
	{

		User user = new User();
		user.setId(1);
		user.setFirstName("vinayak");
		user.setPassword("123");
		service.save(user);
		verify(repo, times(1)).save(user);
	}
	

	@Test
	public void userLogin()
	{

		User user = new User();
		user.setId(1);
		user.setUsername("vinayak");
		user.setPassword("123");
		

		Mockito.when(repo.findByUsernameAndPassword(user.getUsername(), user.getPassword())).thenReturn(user);
		User user1= service.userLogin(user.getUsername(), user.getPassword());
		assertEquals("123", user1.getPassword());
	}
}
