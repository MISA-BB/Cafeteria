package utez.edu.mx.cafeteria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "platillos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Platillo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Boolean disponible = true;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
}