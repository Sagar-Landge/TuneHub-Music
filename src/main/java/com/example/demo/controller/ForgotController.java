package com.example.demo.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Song;
import com.example.demo.entities.Users;
import com.example.demo.services.EmailService;
import com.example.demo.services.SongService;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {
	@Autowired
	EmailService emailService;
	@Autowired
	 UsersService service;
	
	@Autowired
	SongService Service;
	
	boolean success=true;
	
	
	@RequestMapping("/forgot")
	public String openMail()
	{
		return "forgotpass";
		
	}
	
	@PostMapping("/forgot")
	public String sendOtp(@RequestParam("email") String email,HttpSession session)
	{
		if(service.checkUser(email)==true)
		{
		
		 Random random = new Random();
	        int otp = random.nextInt(9000) + 1000;
	        
//	    or     SecureRandom secureRandom = new SecureRandom();
//	        int otp = secureRandom.nextInt(9000) + 1000;
	        
	        
		System.out.println("OTP "+otp);
		
		String subject="OTP From Tunehub";
		String message="OTP= "+otp+"";
		String to=email;
		boolean flag=this.emailService.sendEmail(subject,message,to);
		if(flag)
		{
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			return "verifyotp";
		}
		else
		{
			session.setAttribute("message", "check your email");
		}
		success=false;
		return "verifyotp";
		
	}
		else
		{
			return "login";
		}
	}
	//verify otp
	@PostMapping("/verify-otp")
	public String verifyotp(@RequestParam("otp") int otp, HttpSession session)
	{
		int myotp=(int)session.getAttribute("myotp");
		String email=(String)session.getAttribute("email");
		

		if(myotp==otp)
		{
			return "passwordchange";
		}
		else
		{
			session.setAttribute("message", "You have entered wrong otp");
			success=false;
			return "verifyotp";
		}
	}
	
	
	
	//change password

	@PostMapping("/change-password")

	public String changePassword (@RequestParam("email") String email ,@RequestParam("password") String newpassword, HttpSession session, Model model) {
		
		 service.changePassword(email,  newpassword);
		 String password=newpassword;
		 if(service.validateUser(email,password)==true)
			{
				String role = service.getRole(email);
				session.setAttribute("email", email);
				if(role.equals("admin"))
				{
					return "admin";
				}
				else
				{
					Users user=service.getUser(email);
					boolean userStatus=user.isPremium();
					List<Song> songList=Service.fetchAllSongs();
					model.addAttribute("song", songList);
					model.addAttribute("isPremium", userStatus);
					return "customer";
				}
			}
			else
			{
				return "login";
			}

}
}
