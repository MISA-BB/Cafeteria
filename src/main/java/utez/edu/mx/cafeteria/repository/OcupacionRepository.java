package utez.edu.mx.cafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.cafeteria.model.Ocupacion;

import java.util.List;
import java.util.Optional;

@Repository
public interface OcupacionRepository extends JpaRepository<Ocupacion, Long> {
    Optional<Ocupacion> findByMesaIdAndActivaTrue(Long mesaId);
    List<Ocupacion> findByActivaTrue();
}