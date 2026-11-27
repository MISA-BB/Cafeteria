package utez.edu.mx.cafeteria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.cafeteria.model.Platillo;

@Repository
public interface PlatilloRepository extends JpaRepository<Platillo, Long> {
}