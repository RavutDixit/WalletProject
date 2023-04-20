package com.wallet.WalletProject.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wallet.WalletProject.Entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {



	@Query(value="select * from wallet w where w.status = :x ORDER BY w.status DESC LIMIT 1", nativeQuery = true)
	Wallet createWallet(Integer x);

//	@Modifying
//	@Query(value="update wallet w SET w.status= :y WHERE w.wallet_id=:walletId", nativeQuery = true)
//	void updateStatus(@Param("y") Integer y, @Param("walletId") Integer walletId);
	
	
}
