package co.edu.citas.modules.usuario.domain.port;

import co.edu.citas.modules.usuario.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByUsername(String username);
    List<Usuario> findAll();
    void deleteById(Long id);
    boolean existsByUsername(String username);
}
