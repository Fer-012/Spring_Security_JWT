package org.example.spring_security_jwt.Repository;

import org.example.spring_security_jwt.models.Role;
import org.example.spring_security_jwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long Id);
    void deleteById(Long Id);
    List<User> findByRoles(Role roles);
    long countByRolesAndDoctorId(Role roles, Long doctorId);



}
