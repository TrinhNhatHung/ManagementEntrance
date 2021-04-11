package com.garagemanagement.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garagemanagement.entity.User;
import com.garagemanagement.service.impl.UserServiceImpl;
import com.garagemanagement.util.JwtUtil;

@CrossOrigin
@RestController
public class LoginAPI {
	
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtils;

	@PostMapping("/authorize")
	public ResponseEntity<?> checkAuthorizeToken(
			@RequestParam(value = "authorization_token") String authorizationToken) {
		boolean isAuthor = jwtUtils.validateToken(authorizationToken);
		if (!isAuthor) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		User user = userService.loadUserByUsernameAndPassword(username, password);
		Map<String, String> response = new HashMap<>();
		response.put("token", jwtUtils.generateToken(user));
		response.put("fullname", user.getFullName());
		return new ResponseEntity<>(response , HttpStatus.OK);
	}
	
}
