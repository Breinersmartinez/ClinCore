package co.edu.citas.modules.especialidad.infrastructure.persistence.repository;

import co.edu.citas.modules.especialidad.infrastructure.persistence.entity.EspecialidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadJpaRepository extends JpaRepository<EspecialidadEntity, Long> {
    boolean existsByCodigoEspecialidad(String codigo);
}
