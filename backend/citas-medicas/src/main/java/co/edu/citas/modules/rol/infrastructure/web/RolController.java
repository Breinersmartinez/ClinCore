package co.edu.citas.modules.rol.infrastructure.web;

import co.edu.citas.modules.rol.application.dto.RolDto;
import co.edu.citas.modules.rol.application.usecase.RolUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolUseCase rolUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RolDto.RolResponse>> listar() {
        return ResponseEntity.ok(rolUseCase.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolDto.RolResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rolUseCase.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolDto.RolResponse> crear(@Valid @RequestBody RolDto.RolRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolUseCase.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolDto.RolResponse> actualizar(@PathVariable Long id, @Valid @RequestBody RolDto.RolRequest request) {
        return ResponseEntity.ok(rolUseCase.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rolUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/permisos-disponibles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RolDto.PermisoItem>> permisosDisponibles() {
        List<RolDto.PermisoItem> permisos = List.of(
            new RolDto.PermisoItem("CONSULTORIO", "VER", "CONSULTORIO:VER"),
            new RolDto.PermisoItem("CONSULTORIO", "CREAR", "CONSULTORIO:CREAR"),
            new RolDto.PermisoItem("CONSULTORIO", "EDITAR", "CONSULTORIO:EDITAR"),
            new RolDto.PermisoItem("CONSULTORIO", "ELIMINAR", "CONSULTORIO:ELIMINAR"),
            new RolDto.PermisoItem("ESPECIALIDAD", "VER", "ESPECIALIDAD:VER"),
            new RolDto.PermisoItem("ESPECIALIDAD", "CREAR", "ESPECIALIDAD:CREAR"),
            new RolDto.PermisoItem("ESPECIALIDAD", "EDITAR", "ESPECIALIDAD:EDITAR"),
            new RolDto.PermisoItem("ESPECIALIDAD", "ELIMINAR", "ESPECIALIDAD:ELIMINAR"),
            new RolDto.PermisoItem("USUARIO", "VER", "USUARIO:VER"),
            new RolDto.PermisoItem("USUARIO", "CREAR", "USUARIO:CREAR"),
            new RolDto.PermisoItem("USUARIO", "EDITAR", "USUARIO:EDITAR"),
            new RolDto.PermisoItem("USUARIO", "ELIMINAR", "USUARIO:ELIMINAR")
        );
        return ResponseEntity.ok(permisos);
    }
}
