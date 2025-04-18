package com.foodsphere.repository;

import com.foodsphere.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    public Users findByEmail(String username);
}
