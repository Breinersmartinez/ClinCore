package co.edu.citas.modules.usuario.domain.model;

import java.time.LocalDateTime;
import java.util.Set;

public class Usuario {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String nombreCompleto;
    private boolean activo;
    private Set<String> roles;
    private LocalDateTime fechaCreacion;

    public Usuario() {}

    public Usuario(Long id, String username, String password, String email,
                   String nombreCompleto, boolean activo, Set<String> roles, LocalDateTime fechaCreacion) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nombreCompleto = nombreCompleto;
        this.activo = activo;
        this.roles = roles;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
