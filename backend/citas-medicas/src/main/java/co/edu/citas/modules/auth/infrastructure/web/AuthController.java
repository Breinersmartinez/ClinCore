package co.edu.citas.modules.auth.infrastructure.web;

import co.edu.citas.modules.auth.application.dto.AuthDto;
import co.edu.citas.modules.auth.application.usecase.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    public ResponseEntity<AuthDto.LoginResponse> login(@Valid @RequestBody AuthDto.LoginRequest request) {
        return ResponseEntity.ok(authUseCase.login(request));
    }
}
