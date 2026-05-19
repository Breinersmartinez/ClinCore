package co.edu.citas.modules.consultorio.domain.port;

import co.edu.citas.modules.consultorio.domain.model.Consultorio;

import java.util.List;
import java.util.Optional;

public interface ConsultorioRepositoryPort {
    Consultorio save(Consultorio consultorio);
    Optional<Consultorio> findById(Long id);
    List<Consultorio> findAll();
    List<Consultorio> findByIdSede(Long idSede);
    void deleteById(Long id);
    boolean existsByCodigoAndIdSede(String codigo, Long idSede);
}
