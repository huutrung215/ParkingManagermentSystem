package com.trycoder.service;

import com.trycoder.model.UserDtls;

public interface UserService {
	public UserDtls createUser(String password, String reapeatPassord, UserDtls user);

	public boolean checkEmail(String email);
}
