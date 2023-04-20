package com.wallet.WalletProject.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wallet.WalletProject.Entity.FromToDate;
import com.wallet.WalletProject.Entity.Role;
import com.wallet.WalletProject.Entity.Transaction;
import com.wallet.WalletProject.Entity.User;
import com.wallet.WalletProject.Entity.UserExcelExporter;
import com.wallet.WalletProject.Entity.Wallet;
import com.wallet.WalletProject.Exception.NullPointerException;
import com.wallet.WalletProject.Exception.UserNotFoundException;
import com.wallet.WalletProject.Exception.WalletNotFoundException;
import com.wallet.WalletProject.Service.TransactionService;
import com.wallet.WalletProject.Service.UserService;
import com.wallet.WalletProject.Service.WalletService;

@Controller
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService service;

	@Autowired
	private WalletService walser;
	
	@Autowired
	private TransactionService transactionService;
	
	
	@RequestMapping("/index")
	public String viewHomePage(Model model) {
		
//    List<User> listUsers = service.listAll();
//	model.addAttribute("listUsers", listUsers);
	LOGGER.info("Showing Index page");
		return "index";
	}
	
	@GetMapping("/userlist")
	public String viewUserList(Model model)
	{
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);
		return "userlist";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage(Model model)
	{
		model.addAttribute("user", new User());
		LOGGER.info("Showing User Login form");
		return "login";
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String viewLoginPage(Model model)
	{
		User user = new User();
		model.addAttribute("user", user);
	
		User authUser = service.userLogin(user.getUsername(), user.getPassword());
		
		if(Objects.nonNull(authUser))
		{
		LOGGER.info("User Logged in successfully with username: "+authUser.getUsername());
		return "redirect:/index";
		}
		else {
			LOGGER.error("Invalid Username or Password");
			return "redirect:/login?error";
		}
		
	}
	
	@GetMapping("/register")
	public String showNewRegisterForm(Model model) {

		model.addAttribute("user", new User());
		LOGGER.info("Showing the User Register form");
		return "register";
	}
	
	@RequestMapping(value = "/saveregister", method = RequestMethod.POST)
	public String saveRegister(@ModelAttribute("user") User user, Wallet wallet) {
//		Wallet wallet = null;
//		wallet = walser.createWallet();
	    walser.save(wallet);
	    
		user.setWallet(wallet);
	
		wallet.setWalletBalance(0);
//		wallet.setStatus(1);
		
		service.save(user);
		LOGGER.info("User registered successfully");
		return "redirect:/register?success";
	}
	
	@RequestMapping("/new")
	public String showNewUserForm(Model model) throws NullPointerException {
		User currentUser = service.getCurrentUser();
		Integer id = currentUser.getId();
		Set<Role> roleName =currentUser.getRoles();
		for (Role role : roleName) {
			//System.out.println("ROle name of admin: " + role);
			String s = String.valueOf(role);
			//System.out.println("Role of admin: "+s);
		
		if(s.equals("ADMIN"))
		{
		User user = new User();

		model.addAttribute("user", user);
        LOGGER.info("Creating New User");
		return "new_user";
		}
		else {
			LOGGER.error("Access Denied, Please login as ADMIN");
			return "redirect:/login";
		}
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") User user, Wallet wallet) {
//		Wallet wallet = null;
//		wallet = walser.createWallet();
		
		walser.save(wallet);
		
		user.setWallet(wallet);
	
		wallet.setWalletBalance(0);
//		wallet.setStatus(1);
		
		service.save(user);
		LOGGER.info("User created successfully");
		return "redirect:/new?success";
	}
	
	@RequestMapping(value = "/saveedit", method = RequestMethod.POST)
	public String saveEditUser(@ModelAttribute("user") User user) throws WalletNotFoundException
	{
		Integer i = user.getId();
	Wallet wallet = walser.get(i);
//	System.out.println("Wallet while editing : "+wallet);

				user.setWallet(wallet);
				
		service.saveEdit(user);
		 LOGGER.info("User with Id: "+i+ " edited successfully");
//		System.out.println("User after editing : "+user);
		return "redirect:/index";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditUserForm(@PathVariable(name = "id") Integer id) throws NullPointerException {
		ModelAndView mav = new ModelAndView("edit_user");
		ModelAndView mav1 = new ModelAndView("login");
        User currentUser = service.getCurrentUser();
		Integer cId = currentUser.getId();
		if(cId == 3)
		{
		try {
		User user = service.get(id);
		mav.addObject("user", user);
        LOGGER.info("Form to edit the User with Id: "+id);
		return mav;
		}
		catch(UserNotFoundException ex)
		{
			mav.setViewName("user_not_found");
			LOGGER.error("User Not Found");
			return mav;
		}
		}
		else {
			LOGGER.error("Access Denied, Please login as ADMIN");
			return mav1;
		}
	}

	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id) throws UserNotFoundException, NullPointerException 
	{
		 User currentUser = service.getCurrentUser();
			Integer cId = currentUser.getId();
			if(cId == 3)
			{
			
		service.delete(id);
        LOGGER.info("User with Id: "+id+" deleted successfully");
		return "redirect:/index";
			}
			else {
				LOGGER.error("Access Denied, Please login as ADMIN");
				return "redirect:/login";
			}
	}

	@GetMapping("/wallet/{id}")
	public String showWallet(@PathVariable(name = "id") Integer id, Model model) throws UserNotFoundException, WalletNotFoundException, NullPointerException {
		User currentUser = service.getCurrentUser();
		//System.out.println(currentUser);

		int uId = currentUser.getId();
		User user = service.get(uId);
	
		Wallet wallet = walser.get(id);
		 if(id == uId)
	        {
		

		wallet.setUser(currentUser);
		user.setWallet(wallet);

		Integer i = wallet.getWalletBalance();
		model.addAttribute("walletBalance", i);
//		System.out.println("Wallet Balance of current user= "+i);
//		System.out.println("Current User Wallet Id : " + wallet.getWalletId());
		LOGGER.info("Showing the wallet of User with Id: "+id);
		LOGGER.info("Wallet Balance of current user= "+i);
		return "wallet";
        }
        else {
        	LOGGER.error("Wallet Id does not match with current user Id, Please Login");
        	return "redirect:/login";
        }
	}

	@GetMapping("/addmoney/{id}")
	public ModelAndView showAddMoney(@ModelAttribute("wallet") Wallet wallet, @PathVariable(name = "id") Integer id) throws WalletNotFoundException, NullPointerException {
		ModelAndView view = new ModelAndView("addmoney");
		ModelAndView mav1 = new ModelAndView("redirect:/login");
		Wallet wallet1 = walser.get(id);
		User currentUser = service.getCurrentUser();
		int uId = currentUser.getId();
		if(id == uId)
		{
		view.addObject("wallet", new Wallet());
		//view.setViewName("addmoney");
		LOGGER.info("Showing add money page");
		return view;
		}
		else {
			//view.setViewName("login");
			LOGGER.error("Wallet Id does not match with current user Id, Please Login");
        	return mav1;
		}
	}

	@PostMapping("/saveamount/{id}")
	public String addMoney(@ModelAttribute("wallet") Wallet wallet, @PathVariable(name = "id") Integer id) 
			throws UserNotFoundException, WalletNotFoundException, NullPointerException

	{
		
		Integer a = wallet.getWalletBalance();
		User currentUser = service.getCurrentUser();
		// System.out.println(currentUser);

		int uId = currentUser.getId();
		User user = service.get(uId);

		Wallet walletCUser = walser.get(id);
		walletCUser.setUser(currentUser);
		user.setWallet(walletCUser);
		//System.out.println(walletCUser.getWalletId());
		Integer i = walletCUser.getWalletBalance();

		walletCUser.setWalletBalance(a + i);

		Integer b = walletCUser.getWalletBalance();

		//System.out.println("Wallet Balance is : " + b);
		Integer wId = walletCUser.getWalletId();
		Transaction t = new Transaction();
		t.setFromWallet(wId);
		t.setToWallet(wId);
		t.setAmount(a);
		t.setCredit("credited");
		t.setDebit("-");
		
		LocalDate currentTime = LocalDate.now();
		
		t.setTransactionDate(currentTime);
		transactionService.saveTransaction(t);
		
		walser.save(walletCUser);
		LOGGER.info("Money added successfully to wallet Id: "+wId);
		LOGGER.info("Amount added is: "+a);
		return "redirect:/addmoney/{id}?success";
	}

	@GetMapping("/withdrawmoney/{id}")
	public ModelAndView showWithdrawMoney(@ModelAttribute("wallet") Wallet wallet, @PathVariable(name = "id") Integer id) throws WalletNotFoundException, NullPointerException {
		ModelAndView view = new ModelAndView("withdrawmoney");
		ModelAndView mav1 = new ModelAndView("redirect:/login");
		Wallet wallet1 = walser.get(id);
		User currentUser = service.getCurrentUser();
		int uId = currentUser.getId();
		if(id == uId)
		{
		view.addObject("wallet", new Wallet());
		//view.setViewName("withdrawmoney");
		LOGGER.info("Showing withdraw money page");
		return view;
		}
		else {
			LOGGER.error("Wallet Id does not match with current user Id, Please Login");
			return mav1;
		}
	}

	@PostMapping("/withdrawamount/{id}")
	public String withdrawMoney(@ModelAttribute("wallet") Wallet wallet, @PathVariable(name = "id") Integer id) throws UserNotFoundException, WalletNotFoundException, NullPointerException

	{
		Integer a = wallet.getWalletBalance();
		User currentUser = service.getCurrentUser();

		int uId = currentUser.getId();
		User user = service.get(uId);

		Wallet walletCUser = walser.get(id);
		walletCUser.setUser(currentUser);
		user.setWallet(walletCUser);
		//System.out.println(walletCUser.getWalletId());
		Integer wId = walletCUser.getWalletId();
		Integer i = walletCUser.getWalletBalance();

		if(a < i)
		{
		walletCUser.setWalletBalance(i - a);

		Integer b = walletCUser.getWalletBalance();

		//System.out.println("Wallet Balance is : " + b);
		
		Transaction t = new Transaction();
		t.setFromWallet(wId);
		t.setToWallet(wId);
		t.setAmount(a);
		t.setDebit("debitted");
		t.setCredit("-");
		
		LocalDate currentTime = LocalDate.now();
		
		t.setTransactionDate(currentTime);
		transactionService.saveTransaction(t);
		
		walser.save(walletCUser);
		LOGGER.info("Money withdrawn successfully from wallet Id: "+wId);
		LOGGER.info("Amount withdrawn is: "+a);
		return "redirect:/withdrawmoney/{id}?success";
		}
		else {
			LOGGER.error("Insufficient Balance in Wallet with Id: "+wId);
			return "redirect:/withdrawmoney/{id}?error";
		}
	}

	@GetMapping("/sendmoney")
	public ModelAndView showSendMoney(@ModelAttribute("wallet") Wallet wallet) {
		ModelAndView view = new ModelAndView();
	
		view.addObject("wallet", new Wallet());
		view.setViewName("sendmoney");
		LOGGER.info("Showing send money page");
		return view;
	}

	@PostMapping("/sendamount")
	public String sendMoney(@ModelAttribute("wallet") Wallet wallet) throws UserNotFoundException, WalletNotFoundException, NullPointerException
	{
		Integer i = wallet.getWalletId();
		Integer a = wallet.getWalletBalance();
		
		User currentUser = service.getCurrentUser();
		int uId = currentUser.getId();
		User userC = service.get(uId);
		Wallet walletC = walser.get(uId);
		
		walletC.setUser(userC);
		userC.setWallet(walletC);
		
		Integer c = walletC.getWalletBalance();
		if(a < c)
		{
		walletC.setWalletBalance(c - a);
		
		User user = service.get(i);
		Wallet walletUser = walser.get(i);
		walletUser.setUser(user);
		user.setWallet(walletUser);
		
		Integer b = walletUser.getWalletBalance();
		
		walletUser.setWalletBalance(b+a);
		
		
		//System.out.println("Wallet Balance of reciever= "+walletUser.getWalletBalance());
		LOGGER.info("Wallet Balance of reciever= "+b);
		
		
		Transaction t = new Transaction();
		t.setFromWallet(uId);
		t.setToWallet(i);
		t.setAmount(a);
		t.setDebit("debitted");
		t.setCredit("-");
		LocalDate currentTime = LocalDate.now();
		t.setTransactionDate(currentTime);
		transactionService.saveTransaction(t);
		
		Transaction t1 = new Transaction();
		t1.setFromWallet(uId);
		t1.setToWallet(i);
		t1.setAmount(a);
		t1.setDebit("-");
		t1.setCredit("credited");
		LocalDate currentTime1 = LocalDate.now();
		t1.setTransactionDate(currentTime1);
		transactionService.saveTransaction(t1);
		
		//System.out.println("Wallet Balance of sender= "+walletC.getWalletBalance());
		LOGGER.info("Wallet Balance of sender= "+walletC.getWalletBalance());
		walser.save(walletC);
		LOGGER.info("Money sent successfully to ID: "+i);
		LOGGER.info("Money debited from wallet is "+a);
		return "redirect:/sendmoney?success";
		}
		else {
			LOGGER.error("Insufficient Balance in wallet ID: "+uId);
			return "redirect:/sendmoney?error";
		}
		
	}
	
//	@RequestMapping("/transaction/{id}")
//	public String goToTransaction(@PathVariable(value="id") Integer id, Model model) throws UserNotFoundException
//	{
//		return viewTransactionHistrory(1, id, model);
//	}
	@RequestMapping("/transaction/{id}")
	public String viewTransactionHistrory(@PathVariable(name="id") Integer id,
			    Model model) throws UserNotFoundException, NullPointerException, WalletNotFoundException
	{
		User currentUser = service.getCurrentUser();
		int uId = currentUser.getId();
		Wallet wallet1 = walser.get(id);
		if(uId == id || uId == 3)
		{
		LOGGER.info("Transaction history of User with ID: "+id);
		User user = service.get(id);
		int walletId = user.getWallet().getWalletId();
		List<Transaction> listTransactions = transactionService.listAllTransactions(walletId) ;
		model.addAttribute("listTransactions", listTransactions);
		return "transaction";
		}
		else {
			return "redirect:/login";
		}
		
	}
	
	@GetMapping("/datewisetransactions")
	public ModelAndView datewiseTransactions()
	{
		LOGGER.info("Selecting dates to get the transaction history");
		ModelAndView mav = new ModelAndView();
		mav.addObject("date",new FromToDate());
		mav.setViewName("datewisetransactions");
		return mav;
	}
	
	@GetMapping("/transactions/export")
	public void exportToExcel(HttpServletResponse response, @ModelAttribute("date") FromToDate date) throws IOException, NullPointerException
	{
		String from =date.getFromDate();
		String to = date.getToDate();
		LOGGER.info("Exporting the history to excel sheet");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fromDate = null;
		LocalDate toDate = null;
		try {
		
		fromDate = LocalDate.parse(from, formatter);
			
			
		toDate = LocalDate.parse(to, formatter);
			
			
		} catch (DateTimeParseException e) {
			
			e.printStackTrace();
		}
		
		//System.out.println(fromDate);
		//System.out.println(toDate);
			
		
		
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());
		String fileName = "transactions_" + currentDateTime + ".xlsx";
		String headerValue = "attachment; filename=" + fileName;
		
		response.setHeader(headerKey, headerValue);
		
		Integer id = service.getCurrentUser().getId();
		
		List<Transaction> transactions = transactionService.listAllTransactions(id,fromDate,toDate);
		//System.out.println(transactions.toString());
		LOGGER.info(transactions.toString());
		UserExcelExporter excelExporter = new UserExcelExporter(transactions);
		excelExporter.export(response);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public String handleUserNotFoundException(Exception ex)
	{
		LOGGER.error(ex.getMessage(), ex);
		return "user_not_found";
	}
	
	@ExceptionHandler(WalletNotFoundException.class)
	public String handleWalletNotFoundException(Exception ex)
	{
		LOGGER.error(ex.getMessage(), ex);
		return "wallet_not_found";
	}
	
	@ExceptionHandler(NullPointerException.class)
	public String handleNullPointerException(Exception ex)
	{
		LOGGER.error(ex.getMessage(), ex);
		return "null_pointer_exception";
	}
}
