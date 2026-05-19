package co.edu.citas.modules.usuario.infrastructure.persistence.repository;

import co.edu.citas.modules.usuario.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
