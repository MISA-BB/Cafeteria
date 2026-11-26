package utez.edu.mx.cafeteria.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import utez.edu.mx.cafeteria.model.EstadoOrden;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoOrdenDTO {
    @NotNull(message = "El nuevo estado es obligatorio")
    private EstadoOrden estado;
}