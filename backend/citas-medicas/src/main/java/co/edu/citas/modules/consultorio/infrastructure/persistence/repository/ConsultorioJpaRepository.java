package co.edu.citas.modules.consultorio.infrastructure.persistence.repository;

import co.edu.citas.modules.consultorio.infrastructure.persistence.entity.ConsultorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultorioJpaRepository extends JpaRepository<ConsultorioEntity, Long> {
    List<ConsultorioEntity> findByIdSede(Long idSede);
    boolean existsByCodigoConsultorioAndIdSede(String codigo, Long idSede);
}
