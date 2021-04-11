package com.garagemanagement.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.garagemanagement.entity.User;
import com.garagemanagement.service.impl.UserServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	private static String secret = "123456";
	
	@Autowired
	private UserServiceImpl userService;
	
	public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("username", String.class));
    }
	
	public String getPasswordFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("password", String.class));
    }
	
	public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
	
	private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
	
	private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
	
	public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("password", user.getPassword());
        return doGenerateToken(claims);
    }
	
	private String doGenerateToken(Map<String, Object> claims) {

        return Jwts.builder().setClaims(claims).setSubject("authorization").setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = null;
		String password = null;
		try {
			username = getUsernameFromToken(token);
			password = getPasswordFromToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
        return username.equals(userDetails.getUsername()) && password.equals(userDetails.getPassword());
    }
	
	public Boolean validateToken (String token) {
		User user = null;
		try {
			String username = getUsernameFromToken(token);
			String password = getPasswordFromToken(token);
			user = userService.loadUserByUsernameAndPassword(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return user == null ? false : true;
	}
}
