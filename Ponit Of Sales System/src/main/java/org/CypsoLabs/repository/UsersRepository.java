package org.CypsoLabs.repository;

import org.CypsoLabs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User,Long> {
    Optional<User>findByUsername(String username);
    Boolean existsByUsername(String username);
}
