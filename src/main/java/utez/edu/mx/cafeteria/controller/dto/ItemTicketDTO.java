package utez.edu.mx.cafeteria.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemTicketDTO {
    private String platillo;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private String notas;
}