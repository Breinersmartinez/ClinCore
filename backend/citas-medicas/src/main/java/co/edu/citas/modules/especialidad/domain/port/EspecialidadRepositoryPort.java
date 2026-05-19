package co.edu.citas.modules.especialidad.domain.port;

import co.edu.citas.modules.especialidad.domain.model.Especialidad;
import java.util.List;
import java.util.Optional;

public interface EspecialidadRepositoryPort {
    Especialidad save(Especialidad especialidad);
    Optional<Especialidad> findById(Long id);
    List<Especialidad> findAll();
    void deleteById(Long id);
    boolean existsByCodigo(String codigo);
}
