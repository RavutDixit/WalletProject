package com.wallet.WalletProject.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wallet.WalletProject.Entity.Role;
import com.wallet.WalletProject.Entity.User;
import com.wallet.WalletProject.Exception.NullPointerException;
import com.wallet.WalletProject.Exception.UserNotFoundException;
import com.wallet.WalletProject.Repository.RoleRepository;
import com.wallet.WalletProject.Repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private WebSecurityConfig webConfig;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	
	public List<User> listAll()
	{
		return repo.findAll();
	}
	
	public void save(User user)
	{
		String encryptedPassword = webConfig.passwordEncoder().encode(user.getPassword());
		user.setPassword(encryptedPassword);
		Role role = roleRepository.findByName("USER");
		user.addRole(role);
		user.setStatus("ACTIVE");
		repo.save(user);
	}
	
	public void saveEdit(User user)
	{
		Role role = roleRepository.findByName("USER");
		user.addRole(role);
		repo.save(user);
	}
	

	public User get(Integer id) throws UserNotFoundException
	{
		return repo.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User Not Found with ID :" + id));
	}
	
	public void delete(Integer id) throws UserNotFoundException
	{
		User user = repo.findById(id).get();
//		Role role = new Role(1);
//		user.removeRole(role);
		user.setStatus("NON-ACTIVE");
		repo.save(user);
		
	}
	
	
	public User getCurrentUser() throws NullPointerException
	{
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
			String username = auth.getName();
			
			User loggedInuser= repo.getUserByUsername(username);
			
			if(loggedInuser == null)
			{
				throw new NullPointerException("No User is Logged In");
			}
			
			return loggedInuser;
	}

	public User userLogin(String username, String password) {
		User user = repo.findByUsernameAndPassword(username, password);
		return user;
	}

}
