package com.nube.sistema_hoteles.repository.seguridad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nube.sistema_hoteles.entity.seguridad.PasswordResetToken;

@Repository
public interface  PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String token);

}
