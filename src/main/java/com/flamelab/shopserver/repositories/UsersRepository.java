package com.flamelab.shopserver.repositories;

import com.flamelab.shopserver.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UsersRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Set<User> findAllByUsernameContaining(String username);

    Set<User> findAllByEmailContaining(String email);

    Set<User> findAllByRoleContaining(String role);

    boolean existsByEmail(String email);

}
