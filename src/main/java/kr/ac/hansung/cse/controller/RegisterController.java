package kr.ac.hansung.cse.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.model.ShippingAddress;
import kr.ac.hansung.cse.model.User;
import kr.ac.hansung.cse.service.UserService;

@Controller
public class RegisterController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/register")
	public String registerUser(Model model) {
		
		//양방향
		User user = new User();
		ShippingAddress shippingAddress = new ShippingAddress();
		
		user.setShippingAddress(shippingAddress);
		shippingAddress.setUser(user);
		user.setUsername("Hello");
		
		model.addAttribute("user", user);
		
		return "registerUser";
	}
	
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public String registerUserPost(@Valid User user, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "registerUser"; // 에러가 있으면 registerUser.jsp가 다시보인다.
		}
		
		List<User> userList = userService.getAllUsers();
		for(int i= 0; i < userList.size(); i++) {
			if(user.getUsername().equals(userList.get(i).getUsername())) {
				model.addAttribute("usernameMsg", "Username already exists");
				
				return "registerUser";
			}
		}
		
		//제대로 입력했다면
		
		user.setEnabled(true);
		
		if(user.getUsername().equals("admin"))
			user.setAuthority("ROLE_ADMIN");  //spring security에서 auth 설정 바꾼거 참고
		else
			user.setAuthority("ROLE_USER");
		
		user.getShippingAddress().setUser(user); //양방향이므로 설정해줘야함.ㄴ
		
		userService.addUser(user);
		
		return "registerUserSuccess";
	}
}
