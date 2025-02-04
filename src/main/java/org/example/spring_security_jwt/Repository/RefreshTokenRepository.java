package org.example.spring_security_jwt.Repository;


import org.example.spring_security_jwt.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
   Optional<RefreshToken> findByToken(String token);
   Optional<RefreshToken> findByUserId(Long userId);

}
