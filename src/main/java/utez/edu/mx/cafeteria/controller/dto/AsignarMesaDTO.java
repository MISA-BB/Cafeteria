package utez.edu.mx.cafeteria.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsignarMesaDTO {
    @NotNull(message = "El id de la mesa es obligatorio")
    private Long mesaId;

    @NotNull(message = "El número de comensales es obligatorio")
    @Positive(message = "Debe haber al menos 1 comensal")
    private Integer numComensales;
}