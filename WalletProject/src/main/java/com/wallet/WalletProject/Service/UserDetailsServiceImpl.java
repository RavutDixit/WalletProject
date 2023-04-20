package com.wallet.WalletProject.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.wallet.WalletProject.Entity.User;
import com.wallet.WalletProject.Repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		LOGGER.trace("Entering method loadUserByUsername");
		LOGGER.debug("Authenticating user with username: "+username);
		
		User user = userRepository.getUserByUsername(username);
		if(user == null)
		{
			LOGGER.error("User not found");
			throw new UsernameNotFoundException("Could not find user");
		}
		LOGGER.warn("Testing logging with springboot....");
		LOGGER.info("User authenticated successfully");
		
		return new MyUserDetails(user);
	}

}
