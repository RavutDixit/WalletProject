package com.wallet.WalletProject.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.WalletProject.Entity.Role;
import com.wallet.WalletProject.Repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository repo;

	public void save(Role roles) {
		repo.save(roles);
	}

	public List<Role> listAll() {
		return repo.findAll();
	}

	public Role get(int id) {
		return repo.findById(id).get();
	}

	public void delete(int id) {
		repo.deleteById(id);
	}
}
