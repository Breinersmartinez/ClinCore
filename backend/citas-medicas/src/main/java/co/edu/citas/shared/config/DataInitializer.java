package co.edu.citas.shared.config;

import co.edu.citas.modules.consultorio.domain.model.Consultorio;
import co.edu.citas.modules.consultorio.domain.port.ConsultorioRepositoryPort;
import co.edu.citas.modules.especialidad.domain.model.Especialidad;
import co.edu.citas.modules.especialidad.domain.port.EspecialidadRepositoryPort;
import co.edu.citas.modules.rol.domain.model.Rol;
import co.edu.citas.modules.rol.domain.port.RolRepositoryPort;
import co.edu.citas.modules.usuario.domain.model.Usuario;
import co.edu.citas.modules.usuario.domain.port.UsuarioRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepositoryPort usuarioRepository;
    private final RolRepositoryPort rolRepository;
    private final EspecialidadRepositoryPort especialidadRepository;
    private final ConsultorioRepositoryPort consultorioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("=== Inicializando datos del sistema ===");
        initRoles();
        initUsuarios();
        initEspecialidades();
        initConsultorios();
        log.info("=== Datos inicializados exitosamente ===");
    }

    private void initRoles() {
        if (rolRepository.findAll().isEmpty()) {
            Set<String> permisosAdmin = Set.of(
                    "CONSULTORIO:VER", "CONSULTORIO:CREAR", "CONSULTORIO:EDITAR", "CONSULTORIO:ELIMINAR",
                    "ESPECIALIDAD:VER", "ESPECIALIDAD:CREAR", "ESPECIALIDAD:EDITAR", "ESPECIALIDAD:ELIMINAR",
                    "USUARIO:VER", "USUARIO:CREAR", "USUARIO:EDITAR", "USUARIO:ELIMINAR"
            );
            Set<String> permisosMedico = Set.of(
                    "CONSULTORIO:VER", "ESPECIALIDAD:VER"
            );
            Set<String> permisosAuxiliar = Set.of(
                    "CONSULTORIO:VER", "CONSULTORIO:CREAR", "CONSULTORIO:EDITAR",
                    "ESPECIALIDAD:VER", "USUARIO:VER"
            );

            Rol admin = new Rol(null, "ROLE_ADMIN", "Administrador del sistema con acceso total",
                    true, permisosAdmin, LocalDateTime.now());
            Rol medico = new Rol(null, "ROLE_MEDICO", "Médico del sistema con acceso de lectura",
                    true, permisosMedico, LocalDateTime.now());
            Rol auxiliar = new Rol(null, "ROLE_AUXILIAR", "Personal auxiliar médico",
                    true, permisosAuxiliar, LocalDateTime.now());

            rolRepository.save(admin);
            rolRepository.save(medico);
            rolRepository.save(auxiliar);
            log.info("Roles creados: ADMIN, MEDICO, AUXILIAR");
        }
    }

    private void initUsuarios() {
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Admin2024#"));
            admin.setEmail("admin@citasmedicas.co");
            admin.setNombreCompleto("Administrador del Sistema");
            admin.setActivo(true);
            admin.setRoles(Set.of("ROLE_ADMIN"));
            admin.setFechaCreacion(LocalDateTime.now());
            usuarioRepository.save(admin);
            log.info("Usuario admin creado → username: admin / password: Admin2024#");
        }

        if (!usuarioRepository.existsByUsername("medico1")) {
            Usuario medico = new Usuario();
            medico.setUsername("medico1");
            medico.setPassword(passwordEncoder.encode("Medico2024#"));
            medico.setEmail("medico1@citasmedicas.co");
            medico.setNombreCompleto("Dr. Carlos García López");
            medico.setActivo(true);
            medico.setRoles(Set.of("ROLE_MEDICO"));
            medico.setFechaCreacion(LocalDateTime.now());
            usuarioRepository.save(medico);
            log.info("Usuario médico creado → username: medico1 / password: Medico2024#");
        }

        if (!usuarioRepository.existsByUsername("auxiliar1")) {
            Usuario auxiliar = new Usuario();
            auxiliar.setUsername("auxiliar1");
            auxiliar.setPassword(passwordEncoder.encode("Auxiliar2024#"));
            auxiliar.setEmail("auxiliar1@citasmedicas.co");
            auxiliar.setNombreCompleto("María Rodríguez Pérez");
            auxiliar.setActivo(true);
            auxiliar.setRoles(Set.of("ROLE_AUXILIAR"));
            auxiliar.setFechaCreacion(LocalDateTime.now());
            usuarioRepository.save(auxiliar);
            log.info("Usuario auxiliar creado → username: auxiliar1 / password: Auxiliar2024#");
        }
    }

    private void initEspecialidades() {
        if (especialidadRepository.findAll().isEmpty()) {
            String[][] especialidades = {
                    {"CARD", "Cardiología"},
                    {"PED", "Pediatría"},
                    {"DERM", "Dermatología"},
                    {"OFT", "Oftalmología"},
                    {"NEUR", "Neurología"},
                    {"ORT", "Ortopedia"},
                    {"GIN", "Ginecología"},
                    {"MED_GEN", "Medicina General"}
            };
            for (String[] esp : especialidades) {
                Especialidad e = new Especialidad();
                e.setCodigoEspecialidad(esp[0]);
                e.setNombreEspecialidad(esp[1]);
                e.setActivo(true);
                e.setFechaCreacion(LocalDateTime.now());
                especialidadRepository.save(e);
            }
            log.info("Especialidades médicas inicializadas: {} registros", especialidades.length);
        }
    }

    private void initConsultorios() {
        if (consultorioRepository.findAll().isEmpty()) {
            Object[][] consultorios = {
                    {"C-101", "Consultorio de Medicina General 1", 1, 1, 1L, "Sede Principal Bogotá"},
                    {"C-102", "Consultorio de Medicina General 2", 1, 1, 1L, "Sede Principal Bogotá"},
                    {"C-201", "Consultorio de Cardiología", 2, 1, 1L, "Sede Principal Bogotá"},
                    {"C-202", "Consultorio de Pediatría", 2, 1, 1L, "Sede Principal Bogotá"},
                    {"C-301", "Consultorio de Dermatología", 3, 1, 2L, "Sede Norte"},
                    {"C-302", "Consultorio de Oftalmología", 3, 1, 2L, "Sede Norte"},
            };
            for (Object[] c : consultorios) {
                Consultorio con = new Consultorio();
                con.setCodigoConsultorio((String) c[0]);
                con.setNombreConsultorio((String) c[1]);
                con.setNumeroPiso((Integer) c[2]);
                con.setCapacidad((Integer) c[3]);
                con.setIdSede((Long) c[4]);
                con.setNombreSede((String) c[5]);
                con.setActivo(true);
                con.setFechaCreacion(LocalDateTime.now());
                consultorioRepository.save(con);
            }
            log.info("Consultorios inicializados: {} registros", consultorios.length);
        }
    }
}
