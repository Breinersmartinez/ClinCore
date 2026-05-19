package co.edu.citas.modules.especialidad.infrastructure.persistence.repository;

import co.edu.citas.modules.especialidad.domain.model.Especialidad;
import co.edu.citas.modules.especialidad.domain.port.EspecialidadRepositoryPort;
import co.edu.citas.modules.especialidad.infrastructure.persistence.entity.EspecialidadEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EspecialidadRepositoryAdapter implements EspecialidadRepositoryPort {

    private final EspecialidadJpaRepository jpaRepository;

    @Override
    public Especialidad save(Especialidad e) {
        return toDomain(jpaRepository.save(toEntity(e)));
    }

    @Override
    public Optional<Especialidad> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Especialidad> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        return jpaRepository.existsByCodigoEspecialidad(codigo);
    }

    private EspecialidadEntity toEntity(Especialidad e) {
        EspecialidadEntity entity = new EspecialidadEntity();
        entity.setId(e.getId());
        entity.setCodigoEspecialidad(e.getCodigoEspecialidad());
        entity.setNombreEspecialidad(e.getNombreEspecialidad());
        entity.setActivo(e.isActivo());
        entity.setFechaCreacion(e.getFechaCreacion());
        return entity;
    }

    private Especialidad toDomain(EspecialidadEntity e) {
        return new Especialidad(e.getId(), e.getCodigoEspecialidad(), e.getNombreEspecialidad(),
                e.isActivo(), e.getFechaCreacion());
    }
}
