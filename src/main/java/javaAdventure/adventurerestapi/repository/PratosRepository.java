package javaAdventure.adventurerestapi.repository;

import javaAdventure.adventurerestapi.model.Prato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PratosRepository extends JpaRepository<Prato, Integer> {
}
