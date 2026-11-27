package utez.edu.mx.cafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.cafeteria.model.EstadoOrden;
import utez.edu.mx.cafeteria.model.Orden;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    List<Orden> findByOcupacionId(Long ocupacionId);

    List<Orden> findByOcupacionActivaTrueAndEstadoNotOrderByFechaHoraAsc(EstadoOrden estado);
}