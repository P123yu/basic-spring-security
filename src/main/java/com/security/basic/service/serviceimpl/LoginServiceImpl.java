package com.security.basic.service.serviceimpl;

import com.security.basic.model.Login;
import com.security.basic.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Authentication login(Login login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

        // Authenticate the user using the AuthenticationManager.

        // from this line control will transfer to spring security bean configuration authentication manager instance


        // authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Authentication authentication= authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return authentication;

    }
}
