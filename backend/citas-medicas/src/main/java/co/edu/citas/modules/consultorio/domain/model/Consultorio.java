package co.edu.citas.modules.consultorio.domain.model;

import java.time.LocalDateTime;

public class Consultorio {
    private Long id;
    private String codigoConsultorio;
    private String nombreConsultorio;
    private Integer numeroPiso;
    private Integer capacidad;
    private Long idSede;
    private String nombreSede;
    private boolean activo;
    private LocalDateTime fechaCreacion;

    public Consultorio() {}

    public Consultorio(Long id, String codigoConsultorio, String nombreConsultorio,
                       Integer numeroPiso, Integer capacidad, Long idSede, String nombreSede,
                       boolean activo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.codigoConsultorio = codigoConsultorio;
        this.nombreConsultorio = nombreConsultorio;
        this.numeroPiso = numeroPiso;
        this.capacidad = capacidad;
        this.idSede = idSede;
        this.nombreSede = nombreSede;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoConsultorio() { return codigoConsultorio; }
    public void setCodigoConsultorio(String codigoConsultorio) { this.codigoConsultorio = codigoConsultorio; }
    public String getNombreConsultorio() { return nombreConsultorio; }
    public void setNombreConsultorio(String nombreConsultorio) { this.nombreConsultorio = nombreConsultorio; }
    public Integer getNumeroPiso() { return numeroPiso; }
    public void setNumeroPiso(Integer numeroPiso) { this.numeroPiso = numeroPiso; }
    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }
    public Long getIdSede() { return idSede; }
    public void setIdSede(Long idSede) { this.idSede = idSede; }
    public String getNombreSede() { return nombreSede; }
    public void setNombreSede(String nombreSede) { this.nombreSede = nombreSede; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
