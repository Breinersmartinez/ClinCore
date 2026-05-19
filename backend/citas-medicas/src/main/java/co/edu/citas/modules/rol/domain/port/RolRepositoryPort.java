package co.edu.citas.modules.rol.domain.port;

import co.edu.citas.modules.rol.domain.model.Rol;

import java.util.List;
import java.util.Optional;

public interface RolRepositoryPort {
    Rol save(Rol rol);
    Optional<Rol> findById(Long id);
    Optional<Rol> findByNombre(String nombre);
    List<Rol> findAll();
    void deleteById(Long id);
    boolean existsByNombre(String nombre);
}
