package com.OrderApp2.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.OrderApp2.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.OrderApp2.domain.User;
import com.OrderApp2.dto.UserDTO;
import com.OrderApp2.dto.UserSearchDTO;
import com.OrderApp2.dto.UserPageDTO;
import com.OrderApp2.service.UserService;
import com.OrderApp2.dto.common.RequestDTO;
import com.OrderApp2.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/user")
@RestController
public class UserController {

	private final static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<User> getAll() {

		List<User> users = userService.findAll();
		
		return users;	
	}

	@GetMapping(value = "/{userId}")
	@ResponseBody
	public UserDTO getUser(@PathVariable Integer userId) {
		
		return (userService.getUserDTOById(userId));
	}

 	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = userService.addUser(userDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/users")
	public ResponseEntity<UserPageDTO> getUsers(UserSearchDTO userSearchDTO) {
 
		return userService.getUsers(userSearchDTO);
	}	

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = userService.updateUser(userDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
