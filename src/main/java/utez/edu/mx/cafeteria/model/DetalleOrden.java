package utez.edu.mx.cafeteria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_ordenes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Double precioUnitario; // Guarda el precio del catálogo al momento de pedir

    private String notas; // Ej. "sin cebolla"

    @ManyToOne
    @JoinColumn(name = "platillo_id", nullable = false)
    private Platillo platillo;

    @ManyToOne
    @JoinColumn(name = "orden_id", nullable = false)
    @JsonIgnore
    private Orden orden;
}