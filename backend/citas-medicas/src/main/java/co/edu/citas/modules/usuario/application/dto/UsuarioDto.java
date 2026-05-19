package co.edu.citas.modules.usuario.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

public class UsuarioDto {

    public record UsuarioRequest(
            @NotBlank(message = "El username es requerido")
            @Size(min = 4, max = 50, message = "Username debe tener entre 4 y 50 caracteres")
            String username,

            @NotBlank(message = "El email es requerido")
            @Email(message = "Email inválido")
            String email,

            @NotBlank(message = "El nombre completo es requerido")
            String nombreCompleto,

            @NotBlank(message = "La contraseña es requerida")
            @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
            String password,

            Set<String> roles
    ) {}

    public record UsuarioUpdateRequest(
            @NotBlank(message = "El email es requerido")
            @Email(message = "Email inválido")
            String email,

            @NotBlank(message = "El nombre completo es requerido")
            String nombreCompleto,

            Set<String> roles,
            boolean activo
    ) {}

    public record UsuarioResponse(
            Long id,
            String username,
            String email,
            String nombreCompleto,
            boolean activo,
            Set<String> roles,
            LocalDateTime fechaCreacion
    ) {}
}
