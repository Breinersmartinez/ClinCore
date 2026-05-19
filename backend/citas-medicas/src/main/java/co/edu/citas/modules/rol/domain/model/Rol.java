package co.edu.citas.modules.rol.domain.model;

import java.time.LocalDateTime;
import java.util.Set;

public class Rol {
    private Long id;
    private String nombre;
    private String descripcion;
    private boolean activo;
    private Set<String> permisos; // e.g. "CONSULTORIO:VER", "CONSULTORIO:CREAR"
    private LocalDateTime fechaCreacion;

    public Rol() {}

    public Rol(Long id, String nombre, String descripcion, boolean activo, Set<String> permisos, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.permisos = permisos;
        this.fechaCreacion = fechaCreacion;
    }

    public boolean tienePermiso(String permiso) {
        return permisos != null && permisos.contains(permiso);
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public Set<String> getPermisos() { return permisos; }
    public void setPermisos(Set<String> permisos) { this.permisos = permisos; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
