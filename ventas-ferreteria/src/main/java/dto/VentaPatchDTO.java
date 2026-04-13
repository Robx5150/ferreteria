package dto;

import jakarta.validation.constraints.NotNull;

public record VentaPatchDTO (@NotNull(message = "El estado no puede ser nulo") String estado){
    
}
