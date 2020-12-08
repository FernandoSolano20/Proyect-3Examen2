package cr.ac.ucenfotec.examen.repository;

import cr.ac.ucenfotec.examen.domain.Deparment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Deparment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeparmentRepository extends JpaRepository<Deparment, Long> {
}
