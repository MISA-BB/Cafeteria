package utez.edu.mx.cafeteria.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearOrdenDTO {
    @NotNull(message = "El id de la mesa es obligatorio")
    private Long mesaId;

    @NotEmpty(message = "La orden debe contener al menos un platillo")
    @Valid
    private List<ItemOrdenDTO> items;
}