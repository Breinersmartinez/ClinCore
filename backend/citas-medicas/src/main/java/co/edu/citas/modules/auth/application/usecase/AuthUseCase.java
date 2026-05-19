package co.edu.citas.modules.auth.application.usecase;

import co.edu.citas.modules.auth.application.dto.AuthDto;
import co.edu.citas.modules.usuario.domain.model.Usuario;
import co.edu.citas.modules.usuario.domain.port.UsuarioRepositoryPort;
import co.edu.citas.shared.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthUseCase {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UsuarioRepositoryPort usuarioRepository;

    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        Usuario usuario = usuarioRepository.findByUsername(request.username()).orElseThrow();

        Map<String, Object> extraClaims = Map.of(
                "roles", usuario.getRoles(),
                "nombre", usuario.getNombreCompleto()
        );

        String token = jwtUtil.generateToken(userDetails, extraClaims);

        return new AuthDto.LoginResponse(
                token,
                usuario.getUsername(),
                usuario.getNombreCompleto(),
                usuario.getRoles(),
                86400000L
        );
    }
}
