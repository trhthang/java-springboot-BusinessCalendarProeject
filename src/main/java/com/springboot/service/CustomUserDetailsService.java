
package com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.springboot.controller.CustomUserDetails;
import com.springboot.model.User;
import com.springboot.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repo.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User  not found");
		}

		return new CustomUserDetails(user);
	}

}
