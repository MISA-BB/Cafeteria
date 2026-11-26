package utez.edu.mx.cafeteria.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlatilloDTO {
    @NotBlank(message = "El nombre del platillo es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;

    private Boolean disponible = true;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;
}