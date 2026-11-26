package utez.edu.mx.cafeteria.controller.dto;

import lombok.*;
import utez.edu.mx.cafeteria.model.EstadoMesa;
import utez.edu.mx.cafeteria.model.Orden;
import utez.edu.mx.cafeteria.model.Reservacion;
import utez.edu.mx.cafeteria.model.TipoOcupacion;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeguimientoMesaDTO {
    private Long mesaId;
    private String identificador;
    private Integer maxComensales;
    private EstadoMesa estado;

    // Datos si está OCUPADA
    private Long ocupacionId;
    private TipoOcupacion tipoOcupacion;
    private Integer numComensales;
    private LocalDateTime horaInicio;
    private List<Orden> ordenes;

    // Datos si tiene RESERVACIONES próximas
    private List<Reservacion> reservacionesPendientes;
}