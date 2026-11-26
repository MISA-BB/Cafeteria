package utez.edu.mx.cafeteria.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarReservacionDTO {
    @NotNull(message = "El id de la mesa es obligatorio")
    private Long mesaId;

    @NotNull(message = "La fecha y hora de la reservación son obligatorias")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaHora;

    @NotNull(message = "El número de comensales es obligatorio")
    @Positive(message = "Debe haber al menos 1 comensal")
    private Integer numComensales;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @NotBlank(message = "El teléfono de contacto es obligatorio")
    private String telefonoContacto;
}