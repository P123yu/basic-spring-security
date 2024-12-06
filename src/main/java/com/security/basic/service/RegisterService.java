package com.security.basic.service;

import com.security.basic.model.Register;
import org.springframework.stereotype.Service;

@Service
public interface RegisterService {
    Register register(Register register);
}
