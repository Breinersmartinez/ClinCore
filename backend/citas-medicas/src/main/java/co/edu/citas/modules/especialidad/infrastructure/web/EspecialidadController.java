package co.edu.citas.modules.especialidad.infrastructure.web;

import co.edu.citas.modules.especialidad.application.dto.EspecialidadDto;
import co.edu.citas.modules.especialidad.application.usecase.EspecialidadUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadUseCase especialidadUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'AUXILIAR')")
    public ResponseEntity<List<EspecialidadDto.EspecialidadResponse>> listar() {
        return ResponseEntity.ok(especialidadUseCase.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'AUXILIAR')")
    public ResponseEntity<EspecialidadDto.EspecialidadResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(especialidadUseCase.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EspecialidadDto.EspecialidadResponse> crear(@Valid @RequestBody EspecialidadDto.EspecialidadRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadUseCase.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EspecialidadDto.EspecialidadResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EspecialidadDto.EspecialidadRequest request) {
        return ResponseEntity.ok(especialidadUseCase.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        especialidadUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
