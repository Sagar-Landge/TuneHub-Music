package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Song;
import com.example.demo.entities.Users;
import com.example.demo.services.SongService;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;
//@CrossOrigin("*")
//@RestController
@Controller
public class UsersController {
@Autowired
UsersService service;

@Autowired
SongService songService;

@PostMapping("/registration")
public String addUsers(@ModelAttribute Users user)		
{
	boolean userStatus=service.emailExists(user.getEmail());
	if(userStatus==false)
	{
		service.addUser(user);
		System.out.println("User is added");
		return "customer";
	}
	else
	{
		System.out.println("user is already exists");
	}
	
	return "registration";
}
@PostMapping("/validate")
//public String validate(@RequestBody LoginData data, HttpSession session,Model model) {
//	
//	System.out.println("call received");
//
//	
//	String email=data.getEmail();
//	String password=data.getPassword();
public String validate(@RequestParam("email")String email,@RequestParam("password") String password,
		HttpSession session, Model model) {
	if(service.validateUser(email,password)==true)
	{
		String role = service.getRole(email);
		session.setAttribute("email", email);
		if(role.equals("admin"))
		{
			Users user=service.getNme(email);
			String name=user.getUsername();
			model.addAttribute("username",name);
			return "admin";
		}
		else
		{
			Users users=service.getNme(email);
			String name=users.getUsername();
			model.addAttribute("username",name);
			Users user=service.getUser(email);
			boolean userStatus=user.isPremium();
			List<Song> songList=songService.fetchAllSongs();
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


@GetMapping("/logout")
public String logout(HttpSession session)
{
	session.invalidate();
	return "login";
}

}
