package utez.edu.mx.cafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.cafeteria.model.EstadoOrden;
import utez.edu.mx.cafeteria.model.Orden;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    // Todas las órdenes de una ocupación específica (para armar el ticket al cerrar mesa)
    List<Orden> findByOcupacionId(Long ocupacionId);

    // Órdenes pendientes en cocina (excluye mesas ya cerradas y órdenes ya entregadas)
    List<Orden> findByOcupacionActivaTrueAndEstadoNotOrderByFechaHoraAsc(EstadoOrden estado);
}