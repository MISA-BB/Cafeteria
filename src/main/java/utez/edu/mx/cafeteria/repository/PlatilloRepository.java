package utez.edu.mx.cafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.cafeteria.model.Platillo;

import java.util.List;

@Repository
public interface PlatilloRepository extends JpaRepository<Platillo, Long> {
    List<Platillo> findByCategoriaId(Long categoriaId);
    List<Platillo> findByDisponibleTrue();
}