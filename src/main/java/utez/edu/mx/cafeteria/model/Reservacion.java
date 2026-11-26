package utez.edu.mx.cafeteria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private Integer numComensales;

    @Column(nullable = false)
    private String nombreCliente;

    @Column(nullable = false)
    private String telefonoContacto;

    @Column(nullable = false)
    private Boolean iniciada = false;

    @Column(nullable = false)
    private Boolean cancelada = false;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;
}