package com.wallet.WalletProject.Service;

import static org.junit.jupiter.api.Assertions.*;
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

import com.wallet.WalletProject.Entity.Role;
import com.wallet.WalletProject.Entity.Wallet;
import com.wallet.WalletProject.Repository.RoleRepository;


@SpringBootTest
class RoleServiceTest {
	
	@MockBean
	private RoleRepository repo;
	
	@Autowired
	private RoleService service;

	@Test
	public void listAll()
	{
		
		Role role = new Role();
		role.setId(3);
		role.setName("Editor");
		
		Role role1 = new Role();
		role1.setId(4);
		role1.setName("Admin");
		
		
		List<Role> list = new ArrayList<Role>();
		list.add(role);
		list.add(role1);
		
		Mockito.when(service.listAll()).thenReturn(list);
		List<Role> list1 =repo.findAll();
		assertEquals(2, list1.size());
		
	}
	
	@Test
	public void save()
	{
		Role role = new Role();
		role.setId(3);
		role.setName("Editor");
		
		service.save(role);
		verify(repo, times(1)).save(role);
	}

	@Test
	public void delete()
	{
		Role role = new Role();
		role.setId(3);
		role.setName("Editor");
		
		service.delete(3);;
		verify(repo, times(1)).deleteById(3);
	}
	
	@Test
	public void get()
	{
		Role role = new Role();
		role.setId(3);
		role.setName("Editor");
		
		Mockito.when(repo.findById(3)).thenReturn(Optional.of(role));
		Role role1 = service.get(3);
		assertEquals("Editor", role1.getName());

	}
}
