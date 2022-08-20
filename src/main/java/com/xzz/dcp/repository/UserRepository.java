package com.xzz.dcp.repository;

import com.xzz.dcp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
