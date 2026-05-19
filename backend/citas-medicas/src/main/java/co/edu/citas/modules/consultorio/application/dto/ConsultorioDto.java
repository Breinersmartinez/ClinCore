package co.edu.citas.modules.consultorio.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ConsultorioDto {

    public record ConsultorioRequest(
            @NotBlank(message = "El código del consultorio es requerido")
            String codigoConsultorio,

            @NotBlank(message = "El nombre del consultorio es requerido")
            String nombreConsultorio,

            Integer numeroPiso,

            @Min(value = 1, message = "La capacidad mínima es 1")
            Integer capacidad,

            @NotNull(message = "La sede es requerida")
            Long idSede
    ) {}

    public record ConsultorioResponse(
            Long id,
            String codigoConsultorio,
            String nombreConsultorio,
            Integer numeroPiso,
            Integer capacidad,
            Long idSede,
            String nombreSede,
            boolean activo,
            LocalDateTime fechaCreacion
    ) {}
}
