package com.wallet.WalletProject.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Component
@RequestMapping(value="/", method=RequestMethod.GET)
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String defaultPage()
	{
		LOGGER.info("Showing the default page to Login or Register");
		return "defaultpage";
	}
}
