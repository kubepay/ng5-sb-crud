package com.kubepay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kubepay.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);

}
