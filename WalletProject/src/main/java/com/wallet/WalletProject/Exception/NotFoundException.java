package com.wallet.WalletProject.Exception;

public class NotFoundException extends RuntimeException {

	public NotFoundException()
	{
		super();
	}
	
	public NotFoundException(String message)
	{
		super(message);
	}
}
