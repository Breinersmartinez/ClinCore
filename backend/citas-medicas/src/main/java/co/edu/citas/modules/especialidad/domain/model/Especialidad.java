package co.edu.citas.modules.especialidad.domain.model;

import java.time.LocalDateTime;

public class Especialidad {
    private Long id;
    private String codigoEspecialidad;
    private String nombreEspecialidad;
    private boolean activo;
    private LocalDateTime fechaCreacion;

    public Especialidad() {}

    public Especialidad(Long id, String codigoEspecialidad, String nombreEspecialidad,
                        boolean activo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.codigoEspecialidad = codigoEspecialidad;
        this.nombreEspecialidad = nombreEspecialidad;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoEspecialidad() { return codigoEspecialidad; }
    public void setCodigoEspecialidad(String c) { this.codigoEspecialidad = c; }
    public String getNombreEspecialidad() { return nombreEspecialidad; }
    public void setNombreEspecialidad(String n) { this.nombreEspecialidad = n; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime f) { this.fechaCreacion = f; }
}
