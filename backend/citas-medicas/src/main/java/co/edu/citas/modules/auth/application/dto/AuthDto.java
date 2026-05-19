package co.edu.citas.modules.auth.application.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public class AuthDto {

    public record LoginRequest(
            @NotBlank(message = "El username es requerido")
            String username,
            @NotBlank(message = "La contraseña es requerida")
            String password
    ) {}

    public record LoginResponse(
            String token,
            String username,
            String nombreCompleto,
            Set<String> roles,
            long expiresIn
    ) {}
}
