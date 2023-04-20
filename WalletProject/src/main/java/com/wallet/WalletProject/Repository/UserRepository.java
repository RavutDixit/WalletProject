package com.wallet.WalletProject.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wallet.WalletProject.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);
	
	@Query(value ="SELECT u FROM User u WHERE u.username = :username AND u.password = :password", nativeQuery = true)
    public User findByUsernameAndPassword(String username, String password);
}
