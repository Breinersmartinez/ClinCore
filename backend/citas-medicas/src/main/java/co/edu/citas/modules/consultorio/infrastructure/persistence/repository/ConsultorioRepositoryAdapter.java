package co.edu.citas.modules.consultorio.infrastructure.persistence.repository;

import co.edu.citas.modules.consultorio.domain.model.Consultorio;
import co.edu.citas.modules.consultorio.domain.port.ConsultorioRepositoryPort;
import co.edu.citas.modules.consultorio.infrastructure.persistence.entity.ConsultorioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConsultorioRepositoryAdapter implements ConsultorioRepositoryPort {

    private final ConsultorioJpaRepository jpaRepository;

    @Override
    public Consultorio save(Consultorio c) {
        return toDomain(jpaRepository.save(toEntity(c)));
    }

    @Override
    public Optional<Consultorio> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Consultorio> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Consultorio> findByIdSede(Long idSede) {
        return jpaRepository.findByIdSede(idSede).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByCodigoAndIdSede(String codigo, Long idSede) {
        return jpaRepository.existsByCodigoConsultorioAndIdSede(codigo, idSede);
    }

    private ConsultorioEntity toEntity(Consultorio c) {
        ConsultorioEntity e = new ConsultorioEntity();
        e.setId(c.getId());
        e.setCodigoConsultorio(c.getCodigoConsultorio());
        e.setNombreConsultorio(c.getNombreConsultorio());
        e.setNumeroPiso(c.getNumeroPiso());
        e.setCapacidad(c.getCapacidad());
        e.setIdSede(c.getIdSede());
        e.setNombreSede(c.getNombreSede());
        e.setActivo(c.isActivo());
        e.setFechaCreacion(c.getFechaCreacion());
        return e;
    }

    private Consultorio toDomain(ConsultorioEntity e) {
        return new Consultorio(e.getId(), e.getCodigoConsultorio(), e.getNombreConsultorio(),
                e.getNumeroPiso(), e.getCapacidad(), e.getIdSede(), e.getNombreSede(),
                e.isActivo(), e.getFechaCreacion());
    }
}
