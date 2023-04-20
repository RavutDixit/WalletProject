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

import com.wallet.WalletProject.Entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RoleRepositoryTest {


	@MockBean
	private RoleRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	@Test
	public void findByName() {
		
		Role role = new Role();
		role.setId(3);
		role.setName("Editor");
		
		Mockito.when(repo.findByName(role.getName())).thenReturn(role);
		Role role1 = repo.findByName(role.getName());
		assertEquals("Editor", role1.getName());
	}

}
