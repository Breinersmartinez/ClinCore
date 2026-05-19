package co.edu.citas.modules.usuario.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String email;

    @Column(name = "nombre_completo", length = 200)
    private String nombreCompleto;

    @Column(nullable = false)
    private boolean activo = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "rol")
    private Set<String> roles = new HashSet<>();

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
