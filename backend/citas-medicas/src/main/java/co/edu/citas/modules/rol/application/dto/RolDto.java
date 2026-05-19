package co.edu.citas.modules.rol.application.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

public class RolDto {

    public record RolRequest(
            @NotBlank(message = "El nombre del rol es requerido")
            String nombre,
            String descripcion,
            Set<String> permisos
    ) {}

    public record RolResponse(
            Long id,
            String nombre,
            String descripcion,
            boolean activo,
            Set<String> permisos,
            LocalDateTime fechaCreacion
    ) {}

    public record PermisoItem(
            String modulo,
            String accion,
            String clave // e.g. "CONSULTORIO:VER"
    ) {}
}
