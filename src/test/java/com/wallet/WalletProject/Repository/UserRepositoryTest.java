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

import com.wallet.WalletProject.Entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

	@MockBean
	private UserRepository repo;
	
	
	
	@Test
	public void getUserByUsername() {
		User user = new User();
		user.setId(1);
		user.setFirstName("vinayak");
		
		Mockito.when(repo.getUserByUsername(user.getFirstName())).thenReturn(user);
		User user1 = repo.getUserByUsername(user.getFirstName());
		assertEquals("vinayak", user1.getFirstName());
	}
	
	

}
