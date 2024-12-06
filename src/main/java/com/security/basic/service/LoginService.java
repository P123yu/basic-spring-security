package com.security.basic.service;

import com.security.basic.model.Login;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
public interface LoginService {
    Authentication login(Login login);
}
