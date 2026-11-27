package utez.edu.mx.cafeteria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocupaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ocupacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOcupacion tipo;

    @Column(nullable = false)
    private Integer numComensales;

    @Column(nullable = false)
    private LocalDateTime horaInicio;

    private LocalDateTime horaFin;

    @Column(nullable = false)
    private Boolean activa = true;

    private String formaPago;

    private Double totalPagado;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    @OneToOne
    @JoinColumn(name = "reservacion_id")
    private Reservacion reservacion;
}