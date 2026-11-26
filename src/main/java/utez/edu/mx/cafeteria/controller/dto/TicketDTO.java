package utez.edu.mx.cafeteria.controller.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Long folioOcupacion;
    private String mesa;
    private Integer numComensales;
    private LocalDateTime horaInicio;
    private LocalDateTime horaCierre;
    private List<ItemTicketDTO> consumo;
    private Double total;
    private String formaPago;
}