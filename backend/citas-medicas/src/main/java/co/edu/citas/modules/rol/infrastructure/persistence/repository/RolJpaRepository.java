package co.edu.citas.modules.rol.infrastructure.persistence.repository;

import co.edu.citas.modules.rol.infrastructure.persistence.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolJpaRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
