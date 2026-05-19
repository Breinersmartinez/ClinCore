package co.edu.citas.modules.rol.application.usecase;

import co.edu.citas.modules.rol.application.dto.RolDto;
import co.edu.citas.modules.rol.domain.model.Rol;
import co.edu.citas.modules.rol.domain.port.RolRepositoryPort;
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
public class RolUseCase {

    private final RolRepositoryPort rolRepository;

    @Transactional(readOnly = true)
    public List<RolDto.RolResponse> listarTodos() {
        return rolRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RolDto.RolResponse buscarPorId(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol", id));
        return toResponse(rol);
    }

    @Transactional
    public RolDto.RolResponse crear(RolDto.RolRequest request) {
        if (rolRepository.existsByNombre(request.nombre())) {
            throw new BusinessException("Ya existe un rol con el nombre: " + request.nombre());
        }
        Rol rol = new Rol();
        rol.setNombre(request.nombre().toUpperCase());
        rol.setDescripcion(request.descripcion());
        rol.setActivo(true);
        rol.setPermisos(request.permisos());
        rol.setFechaCreacion(LocalDateTime.now());
        return toResponse(rolRepository.save(rol));
    }

    @Transactional
    public RolDto.RolResponse actualizar(Long id, RolDto.RolRequest request) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol", id));
        rolRepository.findByNombre(request.nombre().toUpperCase()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new BusinessException("Ya existe un rol con el nombre: " + request.nombre());
            }
        });
        rol.setNombre(request.nombre().toUpperCase());
        rol.setDescripcion(request.descripcion());
        rol.setPermisos(request.permisos());
        return toResponse(rolRepository.save(rol));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!rolRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Rol", id);
        }
        rolRepository.deleteById(id);
    }

    private RolDto.RolResponse toResponse(Rol rol) {
        return new RolDto.RolResponse(
                rol.getId(), rol.getNombre(), rol.getDescripcion(),
                rol.isActivo(), rol.getPermisos(), rol.getFechaCreacion()
        );
    }
}
