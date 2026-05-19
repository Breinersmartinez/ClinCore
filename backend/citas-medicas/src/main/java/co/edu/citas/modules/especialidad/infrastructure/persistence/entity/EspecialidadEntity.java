package co.edu.citas.modules.especialidad.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "especialidades")
@Data
@NoArgsConstructor
public class EspecialidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_especialidad", nullable = false, unique = true, length = 10)
    private String codigoEspecialidad;

    @Column(name = "nombre_especialidad", nullable = false, length = 150)
    private String nombreEspecialidad;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
