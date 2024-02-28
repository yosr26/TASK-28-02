package com.yosrabroug.banking.services;

import com.yosrabroug.banking.dto.AuthenticationRequest;
import com.yosrabroug.banking.dto.AuthenticationResponse;
import com.yosrabroug.banking.dto.UserDto;

public interface UserService extends AbstractServices<UserDto>{

    Integer ValidateAccount(Integer id);
    Integer InvalidateAccount(Integer id);

    AuthenticationResponse register(UserDto user);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
