package co.edu.citas.modules.usuario.application.usecase;

import co.edu.citas.modules.usuario.application.dto.UsuarioDto;
import co.edu.citas.modules.usuario.domain.model.Usuario;
import co.edu.citas.modules.usuario.domain.port.UsuarioRepositoryPort;
import co.edu.citas.shared.exception.BusinessException;
import co.edu.citas.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsuarioDto.UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDto.UsuarioResponse buscarPorId(Long id) {
        return toResponse(usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id)));
    }

    @Transactional
    public UsuarioDto.UsuarioResponse crear(UsuarioDto.UsuarioRequest request) {
        if (usuarioRepository.existsByUsername(request.username())) {
            throw new BusinessException("El username '" + request.username() + "' ya está en uso");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setEmail(request.email());
        usuario.setNombreCompleto(request.nombreCompleto());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setActivo(true);
        usuario.setRoles(request.roles() != null ? request.roles() : java.util.Set.of("ROLE_MEDICO"));
        usuario.setFechaCreacion(LocalDateTime.now());
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioDto.UsuarioResponse actualizar(Long id, UsuarioDto.UsuarioUpdateRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        usuario.setEmail(request.email());
        usuario.setNombreCompleto(request.nombreCompleto());
        usuario.setActivo(request.activo());
        if (request.roles() != null && !request.roles().isEmpty()) {
            usuario.setRoles(request.roles());
        }
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        usuarioRepository.deleteById(id);
    }

    private UsuarioDto.UsuarioResponse toResponse(Usuario u) {
        return new UsuarioDto.UsuarioResponse(
                u.getId(), u.getUsername(), u.getEmail(),
                u.getNombreCompleto(), u.isActivo(), u.getRoles(), u.getFechaCreacion()
        );
    }
}
