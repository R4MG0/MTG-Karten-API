package ch.bbcag.mtgsorter.repositories;

import ch.bbcag.mtgsorter.models.Mana;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ManaRepository extends CrudRepository<Mana, Integer> {
    @Query("SELECT s FROM Subtype s WHERE s.name LIKE CONCAT('%', :name, '%')")
    Iterable<Mana> findByName(@Param("name") String name);
}
