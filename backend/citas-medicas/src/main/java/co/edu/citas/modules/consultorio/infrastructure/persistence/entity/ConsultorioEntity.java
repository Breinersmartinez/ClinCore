package co.edu.citas.modules.consultorio.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultorios",
        uniqueConstraints = @UniqueConstraint(columnNames = {"codigo_consultorio", "id_sede"}))
@Data
@NoArgsConstructor
public class ConsultorioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_consultorio", nullable = false, length = 10)
    private String codigoConsultorio;

    @Column(name = "nombre_consultorio", nullable = false, length = 200)
    private String nombreConsultorio;

    @Column(name = "numero_piso")
    private Integer numeroPiso;

    @Column(nullable = false)
    private Integer capacidad = 1;

    @Column(name = "id_sede", nullable = false)
    private Long idSede;

    @Column(name = "nombre_sede", length = 200)
    private String nombreSede;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
