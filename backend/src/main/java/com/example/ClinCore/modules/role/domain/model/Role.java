package com.example.ClinCore.modules.role.domain.model;

import java.time.LocalDateTime;

public class Role {
    private UUID id;
    private String name;
    private String description;
    private boolean active;
    private Set<String> permision;
    private LocalDateTime fechaCreacion;
    
}
