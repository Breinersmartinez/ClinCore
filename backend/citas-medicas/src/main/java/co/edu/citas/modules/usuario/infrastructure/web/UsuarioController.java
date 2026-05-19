package co.edu.citas.modules.usuario.infrastructure.web;

import co.edu.citas.modules.usuario.application.dto.UsuarioDto;
import co.edu.citas.modules.usuario.application.usecase.UsuarioUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDto.UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuarioUseCase.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDto.UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioUseCase.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDto.UsuarioResponse> crear(@Valid @RequestBody UsuarioDto.UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioUseCase.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDto.UsuarioResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDto.UsuarioUpdateRequest request) {
        return ResponseEntity.ok(usuarioUseCase.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
