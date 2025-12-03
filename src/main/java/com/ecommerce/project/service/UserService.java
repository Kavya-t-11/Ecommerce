package com.ecommerce.project.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.config.SecurityConfig;
import com.ecommerce.project.dto.LoginRequestDto;
import com.ecommerce.project.dto.ResponseDto;
import com.ecommerce.project.dto.UserDto;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.utils.ServiceUtils;

@Service
public class UserService {

	@Autowired
	private ServiceUtils serviceUtils;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SecurityConfig securityConfig;

	public ResponseDto newUserRegister(UserDto userDto) {
		ResponseDto responseDto = new ResponseDto();
		try {
			User user = serviceUtils.convertToEntity(userDto);

			user.setPassword(securityConfig.passwordEncoder().encode(userDto.getPassword()));

			userRepository.save(user);

			responseDto.setData(user);
			responseDto.setMessage("New User Registered Successfully");
			responseDto.setStatusCode(200);
			responseDto.setStatus("Success");

		} catch (Exception e) {
			responseDto.setMessage("Unable To Register the user");
			responseDto.setStatusCode(500);
			responseDto.setStatus("Failed");
		}
		return responseDto;
	}

	public ResponseDto userLogin(LoginRequestDto loginRequestDto) {
		ResponseDto responseDto = new ResponseDto();

		try {
			User user = userRepository.findByEmail(loginRequestDto.getEmail())
					.orElseThrow(() -> new RuntimeException("Invalid email or password"));

			if (!securityConfig.passwordEncoder().matches(loginRequestDto.getPassword(), user.getPassword())) {
				throw new RuntimeException("Invalid email or password");
			}

			// Prepare success response
			responseDto.setData(user);
			responseDto.setMessage("Login Successful");
			responseDto.setStatusCode(200);
			responseDto.setStatus("Success");

		} catch (Exception e) {
			responseDto.setMessage(e.getMessage() != null ? e.getMessage() : "Unable to process login");
			responseDto.setStatusCode(401); 
			responseDto.setStatus("Failed");
		}

		return responseDto;
	}

	public ResponseDto getUsers(String email) {
		ResponseDto responseDto = new ResponseDto();
		try {
			Optional<User> usersList = userRepository.findByEmail(email);
			if (usersList.isPresent()) {
				responseDto.setData(usersList);
				responseDto.setMessage("Users Fetched Successfully");
				responseDto.setStatusCode(200);
				responseDto.setStatus("Success");
			} else {
				responseDto.setMessage("No Users Found");
				responseDto.setStatusCode(400);
				responseDto.setStatus("Not Found");
			}
		} catch (Exception e) {
			responseDto.setMessage(e.getMessage() != null ? e.getMessage() : "Unable to fetch Users");
			responseDto.setStatusCode(500);
			responseDto.setStatus("Failed");
		}
		return responseDto;
	}
	
	public ResponseDto getUsersByRole(String role) {
		ResponseDto responseDto = new ResponseDto();
		try {
			List<User> usersList = userRepository.findByRole(role);
			if (usersList.size() != 0) {
				responseDto.setData(usersList);
				responseDto.setMessage("Users Fetched Successfully");
				responseDto.setStatusCode(200);
				responseDto.setStatus("Success");
			} else {
				responseDto.setMessage("No Users Found");
				responseDto.setStatusCode(400);
				responseDto.setStatus("Not Found");
			}
		} catch (Exception e) {
			responseDto.setMessage(e.getMessage() != null ? e.getMessage() : "Unable to fetch Users");
			responseDto.setStatusCode(500);
			responseDto.setStatus("Failed");
		}
		return responseDto;
	}

	public ResponseDto updateUser(Long id, UserDto updatedUser) {
		ResponseDto responseDto = new ResponseDto();
		try {
			Optional<User> existingUser = userRepository.findById(id);

			if (existingUser.isPresent()) {
				User userObject = existingUser.get();
				userObject.setName(updatedUser.getName());
				userObject.setEmail(updatedUser.getEmail());
				userRepository.save(userObject);

				responseDto.setData(userObject);
				responseDto.setMessage("User details updated Succesfully");
				responseDto.setStatusCode(200);
				responseDto.setStatus("Success");
			} else {
				responseDto.setMessage("User not found");
				responseDto.setStatusCode(400);
				responseDto.setStatus("Not Found");
			}
		} catch (Exception e) {
			responseDto.setMessage(e.getMessage() != null ? e.getMessage() : "Unable to update user details");
			responseDto.setStatusCode(500);
			responseDto.setStatus("Failed");
		}
		return responseDto;
	}

	public ResponseDto deleteUser(Long id, String adminKey) {
		ResponseDto responseDto = new ResponseDto();
		try {
			if (!"X_ADMIN_USER".equalsIgnoreCase(adminKey)) {
				responseDto.setMessage("Access Denied");
				responseDto.setStatusCode(401);
				responseDto.setStatus("Failed");
				return responseDto;
			}

			Optional<User> existingUser = userRepository.findById(id);

			if (existingUser.isPresent()) {
				User userObject = existingUser.get();
				userRepository.delete(userObject);

				responseDto.setData(userObject);
				responseDto.setMessage("User deleted Successfully");
				responseDto.setStatusCode(200);
				responseDto.setStatus("Success");
			} else {
				responseDto.setMessage("User does not exists");
				responseDto.setStatusCode(400);
				responseDto.setStatus("Not Found");
			}
		} catch (Exception e) {
			responseDto.setMessage(e.getMessage() != null ? e.getMessage() : "Unable to delete user details");
			responseDto.setStatusCode(500);
			responseDto.setStatus("Failed");
		}
		return responseDto;
	}

}
