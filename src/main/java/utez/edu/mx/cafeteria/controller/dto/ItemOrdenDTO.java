package utez.edu.mx.cafeteria.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrdenDTO {
    @NotNull(message = "El id del platillo es obligatorio")
    private Long platilloId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    private String notas;
}