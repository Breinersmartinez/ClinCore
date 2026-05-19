package co.edu.citas.modules.usuario.infrastructure.persistence.repository;

import co.edu.citas.modules.usuario.domain.model.Usuario;
import co.edu.citas.modules.usuario.domain.port.UsuarioRepositoryPort;
import co.edu.citas.modules.usuario.infrastructure.persistence.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;

    @Override
    public Usuario save(Usuario usuario) {
        return toDomain(jpaRepository.save(toEntity(usuario)));
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(this::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaRepository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    private UsuarioEntity toEntity(Usuario u) {
        UsuarioEntity e = new UsuarioEntity();
        e.setId(u.getId());
        e.setUsername(u.getUsername());
        e.setPassword(u.getPassword());
        e.setEmail(u.getEmail());
        e.setNombreCompleto(u.getNombreCompleto());
        e.setActivo(u.isActivo());
        e.setRoles(u.getRoles());
        e.setFechaCreacion(u.getFechaCreacion());
        return e;
    }

    private Usuario toDomain(UsuarioEntity e) {
        return new Usuario(e.getId(), e.getUsername(), e.getPassword(), e.getEmail(),
                e.getNombreCompleto(), e.isActivo(), e.getRoles(), e.getFechaCreacion());
    }
}
