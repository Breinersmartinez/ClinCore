package co.edu.citas.modules.consultorio.infrastructure.web;

import co.edu.citas.modules.consultorio.application.dto.ConsultorioDto;
import co.edu.citas.modules.consultorio.application.usecase.ConsultorioUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultorios")
@RequiredArgsConstructor
public class ConsultorioController {

    private final ConsultorioUseCase consultorioUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'AUXILIAR')")
    public ResponseEntity<List<ConsultorioDto.ConsultorioResponse>> listar() {
        return ResponseEntity.ok(consultorioUseCase.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'AUXILIAR')")
    public ResponseEntity<ConsultorioDto.ConsultorioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultorioUseCase.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AUXILIAR')")
    public ResponseEntity<ConsultorioDto.ConsultorioResponse> crear(@Valid @RequestBody ConsultorioDto.ConsultorioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultorioUseCase.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUXILIAR')")
    public ResponseEntity<ConsultorioDto.ConsultorioResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ConsultorioDto.ConsultorioRequest request) {
        return ResponseEntity.ok(consultorioUseCase.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        consultorioUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
