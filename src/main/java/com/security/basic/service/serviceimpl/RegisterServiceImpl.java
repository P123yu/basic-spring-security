package com.security.basic.service.serviceimpl;

import com.security.basic.model.Register;
import com.security.basic.repository.RegisterRepository;
import com.security.basic.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Register register(Register register) {
        register.setUserPassword(passwordEncoder.encode(register.getUserPassword()));
        return registerRepository.save(register);
    }
}
