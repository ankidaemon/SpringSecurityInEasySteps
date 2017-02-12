package com.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationSample {
	private static AuthenticationManager authenticationManager = new SampleAuthenticationManager();

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter Username: ");
		String name = sc.nextLine();
		System.out.println("Please enter Password: ");
		String password = sc.nextLine();

		try {
			Authentication request = new UsernamePasswordAuthenticationToken(name, password);
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);

		} catch (AuthenticationException e) {
			System.out.println("Authentication failed: " + e.getMessage());
			System.exit(1);
		}

		System.out.println("Successfully authenticated. Security context contains: "
				+ SecurityContextHolder.getContext().getAuthentication());
	}
}

class SampleAuthenticationManager implements AuthenticationManager {
	static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

	static {
		AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
	}

	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		if (auth.getName().equals(auth.getCredentials())) {
			return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), AUTHORITIES);
		}
		throw new BadCredentialsException("Bad Credentials");
	}
}
