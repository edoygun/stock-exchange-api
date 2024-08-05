package com.ercandoygun.stockexchange.repositories;

import com.ercandoygun.stockexchange.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
