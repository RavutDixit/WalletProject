package com.wallet.WalletProject.Service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.wallet.WalletProject.Entity.Wallet;
import com.wallet.WalletProject.Exception.WalletNotFoundException;
import com.wallet.WalletProject.Repository.WalletRepository;

@Service
public class WalletService {
	
	@Autowired
	WalletRepository repo;

	@Transactional
	public Wallet createWallet() {
		Wallet wallet = null;
		int x = 0;
		//int y = 1;
		//repo.save(wallet);
		wallet = repo.createWallet(x);
		//Integer id = wallet.getWalletId();
//		if(wallet != null)
//		{
//			repo.updateStatus(y, id);
//		}
	
		return wallet;
	}
//	@Transactional
//	public void updateStatus(Integer id)
//	{
//		
//		int y = 1;
//		 repo.updateStatus(y, id);
//		
//		
//		
//	}
	public Wallet get(Integer id) throws WalletNotFoundException
	{
		
		return repo.findById(id).orElseThrow(()-> new WalletNotFoundException("Wallet not found with id :" + id));
		
	}

	public void save(Wallet wallet) 
	{
		//wallet.setWalletBalance(0);
		wallet.setStatus(1);
		repo.save(wallet);
	}

	public void delete(Integer id)
	{
		repo.deleteById(id);
	}
}
