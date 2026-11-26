package utez.edu.mx.cafeteria.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MesaDTO {
    @NotBlank(message = "El identificador de la mesa es obligatorio")
    private String identificador;

    @NotNull(message = "El máximo de comensales es obligatorio")
    @Positive(message = "El máximo de comensales debe ser mayor a 0")
    private Integer maxComensales;
}