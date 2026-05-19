package co.edu.citas.modules.especialidad.application.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class EspecialidadDto {

    public record EspecialidadRequest(
            @NotBlank(message = "El código es requerido")
            String codigoEspecialidad,
            @NotBlank(message = "El nombre de la especialidad es requerido")
            String nombreEspecialidad
    ) {}

    public record EspecialidadResponse(
            Long id,
            String codigoEspecialidad,
            String nombreEspecialidad,
            boolean activo,
            LocalDateTime fechaCreacion
    ) {}
}
