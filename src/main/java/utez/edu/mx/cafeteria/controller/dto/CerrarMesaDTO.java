package utez.edu.mx.cafeteria.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CerrarMesaDTO {
    private String formaPago; // Opcional según DFR ("si aplica, forma de pago")
}