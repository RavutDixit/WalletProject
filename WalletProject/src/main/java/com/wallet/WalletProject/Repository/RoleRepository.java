package com.wallet.WalletProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wallet.WalletProject.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query("SELECT r from Role r where r.name = :name")
	public Role findByName(String name);
}
