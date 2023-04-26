package com.trycoder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.trycoder.model.UserDtls;
import com.trycoder.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncode;

	@Override
	public UserDtls createUser(String password, String reapeatPassord, UserDtls user) {

		if(password.equals(reapeatPassord)) {
			user.setPassword(passwordEncode.encode(user.getPassword()));
			user.setRole("ROLE_USER");
			return userRepo.save(user);
		}
		return null;
	}

	@Override
	public boolean checkEmail(String email) {

		return userRepo.existsByEmail(email);
	}
}
