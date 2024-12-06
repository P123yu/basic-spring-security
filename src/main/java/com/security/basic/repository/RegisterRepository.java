package com.security.basic.repository;

import com.security.basic.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends JpaRepository<Register,Long> {
    Register findByUserName(String userName);
}
