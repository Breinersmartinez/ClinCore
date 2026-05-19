package co.edu.citas.modules.rol.infrastructure.persistence.repository;

import co.edu.citas.modules.rol.domain.model.Rol;
import co.edu.citas.modules.rol.domain.port.RolRepositoryPort;
import co.edu.citas.modules.rol.infrastructure.persistence.entity.RolEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RolRepositoryAdapter implements RolRepositoryPort {

    private final RolJpaRepository jpaRepository;

    @Override
    public Rol save(Rol rol) {
        RolEntity entity = toEntity(rol);
        return toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Rol> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        return jpaRepository.findByNombre(nombre).map(this::toDomain);
    }

    @Override
    public List<Rol> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return jpaRepository.existsByNombre(nombre);
    }

    private RolEntity toEntity(Rol rol) {
        RolEntity entity = new RolEntity();
        entity.setId(rol.getId());
        entity.setNombre(rol.getNombre());
        entity.setDescripcion(rol.getDescripcion());
        entity.setActivo(rol.isActivo());
        entity.setPermisos(rol.getPermisos());
        entity.setFechaCreacion(rol.getFechaCreacion());
        return entity;
    }

    private Rol toDomain(RolEntity entity) {
        return new Rol(
                entity.getId(), entity.getNombre(), entity.getDescripcion(),
                entity.isActivo(), entity.getPermisos(), entity.getFechaCreacion()
        );
    }
}
