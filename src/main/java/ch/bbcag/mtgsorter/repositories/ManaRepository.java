package ch.bbcag.mtgsorter.repositories;

import ch.bbcag.mtgsorter.models.Mana;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ManaRepository extends CrudRepository<Mana, Integer> {

    @Query("SELECT m FROM Mana m WHERE m.color LIKE CONCAT('%', :color, '%')")
    Iterable<Mana> findByManaColor(@Param("color") String color);
}
