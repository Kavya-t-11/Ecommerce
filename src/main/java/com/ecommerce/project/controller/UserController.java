package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.dto.LoginRequestDto;
import com.ecommerce.project.dto.ResponseDto;
import com.ecommerce.project.dto.UserDto;
import com.ecommerce.project.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseDto newUserRegister(@RequestBody UserDto userDto) {
		return userService.newUserRegister(userDto);
	}

	@PostMapping("/login")
	public ResponseDto userLogin(@RequestBody LoginRequestDto loginRequestDto) {
		return userService.userLogin(loginRequestDto);
	}

	@GetMapping("/getUsers")
	public ResponseDto getUsers(@RequestParam String email) {
		return userService.getUsers(email);
	}
	
	@GetMapping("/getUsersByRole")
	public ResponseDto getUsersByRole(@RequestParam String role) {
		return userService.getUsers(role);
	}


	@PutMapping("/updateUser")
	public ResponseDto updateUser(@RequestParam Long id, @RequestBody UserDto updatedUser) {

		return userService.updateUser(id, updatedUser);
	}

	@DeleteMapping("/deleteUser")
	public ResponseDto deleteUser(@RequestParam Long id,
			@RequestHeader(value = "ADMIN_KEY", required = false) String adminKey) {
		return userService.deleteUser(id, adminKey);
	}
}
