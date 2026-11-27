package utez.edu.mx.cafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.cafeteria.model.Reservacion;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
    List<Reservacion> findByMesaIdAndIniciadaFalseAndCanceladaFalseAndFechaHoraBetween(
            Long mesaId,
            LocalDateTime inicio,
            LocalDateTime fin
    );

    List<Reservacion> findByIniciadaFalseAndCanceladaFalseOrderByFechaHoraAsc();
}