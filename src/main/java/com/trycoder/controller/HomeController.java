package com.trycoder.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trycoder.model.UserDtls;
import com.trycoder.repository.UserRepository;
import com.trycoder.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/position")
	public String position() {
		return "showPosition";
	}
	
	@GetMapping("/parking")
	public String parking() {
		return "showParking";
	}
	
	@GetMapping("/UserDetails")
	public String UserDetails() {
		return "userDetails";
	}
	
	@GetMapping("/EditUser")
	public String EditUser() {
		return "editUser";
	}
	
	@GetMapping("/ConfirmBooking")
	public String confirmBooking() {
		return "confirmBooking";
	}
	
	
	@GetMapping("/AddPosition")
	public String addPosition() {
		return "addPosition";
	}
	
	@GetMapping("/UserManagerment")
	public String userManagerment() {
		return "userManagerment";
	}
	
	@GetMapping("/TicketSaleReport")
	public String ticketSaleReport() {
		return "ticketSaleReport";
	}
	
	@GetMapping("/ProfileAdmin")
	public String ProfileAdmin() {
		return "profileAdmin";
	}
	
	@GetMapping("/ChangePassword")
	public String ChangePassword() {
		return "changePassword";
	}
	
	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/categories")
	public String categories() {
		return "categories";
	}
	
	@GetMapping("/products")
	public String products() {
		return "products";
	}
	
	@GetMapping("/tables")
	public String tables() {
		return "tables";
	}
	
	@GetMapping("/charts")
	public String charts() {
		return "charts";
	}
	
	@GetMapping("/forgotPassword")
	public String forgotPassword() {
		return "forgot-password";
	}
	
	@GetMapping("/cards")
	public String cards() {
		return "cards";
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		if (p != null) {
			String email = p.getName();
			UserDtls user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}
	}
	
	@PostMapping("/createUser")
	public String createuser(@RequestParam("password") String password, @RequestParam("password") String repeatPassword, @ModelAttribute UserDtls user, HttpSession session,  Model model) {

		// System.out.println(user);

		boolean f = userService.checkEmail(user.getEmail());

		if (f) {
			session.setAttribute("msg", "Email Id alreday exists");
		}

		else {
			UserDtls userDtls = userService.createUser(password, repeatPassword, user);
			if (userDtls != null) {
				System.out.println("Register Successfully"); 
				session.setAttribute("msg", "Register Successfully");
				model.addAttribute("user", userDtls); // truyền userDtls vào mô hình tham số
			} else {
				session.setAttribute("msg", "Password and Repeat Password do not match");
				System.out.println("Register Failed"); 
			}
		}

		return "redirect:/register";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(Principal p, @RequestParam("oldPass") String oldPass, 
			@RequestParam("newPass") String newPass, 
			@RequestParam("reNewPass") String reNewPass, HttpSession session) {
		
		String email = p.getName();
		
		UserDtls loginUser = userRepo.findByEmail(email);
		boolean f = passwordEncoder.matches(oldPass, loginUser.getPassword());
		
		if (f) {
			 if (newPass.equals(reNewPass)) {
				 loginUser.setPassword(passwordEncoder.encode(newPass));
			      UserDtls updatePassUser = userRepo.save(loginUser);
			      if (updatePassUser != null) {
			    	  session.setAttribute("msg", "Change Password Successfully");
			      } else {
			    	  session.setAttribute("msg", "Change Password Failled");
			      }
			    } else {
			    	System.out.println("Nhập lại mật khẩu mới bị sai");
			    }
			System.out.println("Mật khẩu cũ đúng");
		}
			
		else {
			session.setAttribute("msg", "Old Password Incorrect");
			System.out.println("Mật khẩu cũ sai");
		}
			
		return "redirect:/ChangePassword";
	}
	
}
