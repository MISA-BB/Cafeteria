package utez.edu.mx.cafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.cafeteria.model.EstadoMesa;
import utez.edu.mx.cafeteria.model.Mesa;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    boolean existsByIdentificadorIgnoreCase(String identificador);
    List<Mesa> findByEstado(EstadoMesa estado);
}