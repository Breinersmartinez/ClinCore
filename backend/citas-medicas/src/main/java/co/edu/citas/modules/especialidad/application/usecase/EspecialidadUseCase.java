package co.edu.citas.modules.especialidad.application.usecase;

import co.edu.citas.modules.especialidad.application.dto.EspecialidadDto;
import co.edu.citas.modules.especialidad.domain.model.Especialidad;
import co.edu.citas.modules.especialidad.domain.port.EspecialidadRepositoryPort;
import co.edu.citas.shared.exception.BusinessException;
import co.edu.citas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EspecialidadUseCase {

    private final EspecialidadRepositoryPort especialidadRepository;

    @Transactional(readOnly = true)
    public List<EspecialidadDto.EspecialidadResponse> listarTodos() {
        return especialidadRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EspecialidadDto.EspecialidadResponse buscarPorId(Long id) {
        return toResponse(especialidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad", id)));
    }

    @Transactional
    public EspecialidadDto.EspecialidadResponse crear(EspecialidadDto.EspecialidadRequest request) {
        if (especialidadRepository.existsByCodigo(request.codigoEspecialidad())) {
            throw new BusinessException("Ya existe una especialidad con el código: " + request.codigoEspecialidad());
        }
        Especialidad e = new Especialidad();
        e.setCodigoEspecialidad(request.codigoEspecialidad().toUpperCase());
        e.setNombreEspecialidad(request.nombreEspecialidad());
        e.setActivo(true);
        e.setFechaCreacion(LocalDateTime.now());
        return toResponse(especialidadRepository.save(e));
    }

    @Transactional
    public EspecialidadDto.EspecialidadResponse actualizar(Long id, EspecialidadDto.EspecialidadRequest request) {
        Especialidad e = especialidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad", id));
        e.setCodigoEspecialidad(request.codigoEspecialidad().toUpperCase());
        e.setNombreEspecialidad(request.nombreEspecialidad());
        return toResponse(especialidadRepository.save(e));
    }

    @Transactional
    public void eliminar(Long id) {
        especialidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad", id));
        especialidadRepository.deleteById(id);
    }

    private EspecialidadDto.EspecialidadResponse toResponse(Especialidad e) {
        return new EspecialidadDto.EspecialidadResponse(
                e.getId(), e.getCodigoEspecialidad(), e.getNombreEspecialidad(),
                e.isActivo(), e.getFechaCreacion()
        );
    }
}
