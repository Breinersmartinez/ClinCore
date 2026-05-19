package co.edu.citas.modules.consultorio.application.usecase;

import co.edu.citas.modules.consultorio.application.dto.ConsultorioDto;
import co.edu.citas.modules.consultorio.domain.model.Consultorio;
import co.edu.citas.modules.consultorio.domain.port.ConsultorioRepositoryPort;
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
public class ConsultorioUseCase {

    private final ConsultorioRepositoryPort consultorioRepository;

    @Transactional(readOnly = true)
    public List<ConsultorioDto.ConsultorioResponse> listarTodos() {
        return consultorioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConsultorioDto.ConsultorioResponse buscarPorId(Long id) {
        return toResponse(consultorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultorio", id)));
    }

    @Transactional
    public ConsultorioDto.ConsultorioResponse crear(ConsultorioDto.ConsultorioRequest request) {
        if (consultorioRepository.existsByCodigoAndIdSede(request.codigoConsultorio(), request.idSede())) {
            throw new BusinessException("Ya existe un consultorio con el código '"
                    + request.codigoConsultorio() + "' en esa sede");
        }
        Consultorio c = new Consultorio();
        c.setCodigoConsultorio(request.codigoConsultorio());
        c.setNombreConsultorio(request.nombreConsultorio());
        c.setNumeroPiso(request.numeroPiso());
        c.setCapacidad(request.capacidad() != null ? request.capacidad() : 1);
        c.setIdSede(request.idSede());
        c.setActivo(true);
        c.setFechaCreacion(LocalDateTime.now());
        return toResponse(consultorioRepository.save(c));
    }

    @Transactional
    public ConsultorioDto.ConsultorioResponse actualizar(Long id, ConsultorioDto.ConsultorioRequest request) {
        Consultorio c = consultorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultorio", id));
        c.setCodigoConsultorio(request.codigoConsultorio());
        c.setNombreConsultorio(request.nombreConsultorio());
        c.setNumeroPiso(request.numeroPiso());
        c.setCapacidad(request.capacidad() != null ? request.capacidad() : c.getCapacidad());
        c.setIdSede(request.idSede());
        return toResponse(consultorioRepository.save(c));
    }

    @Transactional
    public void eliminar(Long id) {
        consultorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultorio", id));
        consultorioRepository.deleteById(id);
    }

    private ConsultorioDto.ConsultorioResponse toResponse(Consultorio c) {
        return new ConsultorioDto.ConsultorioResponse(
                c.getId(), c.getCodigoConsultorio(), c.getNombreConsultorio(),
                c.getNumeroPiso(), c.getCapacidad(), c.getIdSede(), c.getNombreSede(),
                c.isActivo(), c.getFechaCreacion()
        );
    }
}
