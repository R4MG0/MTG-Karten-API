package ch.bbcag.mtgsorter.repositories;

import ch.bbcag.mtgsorter.models.Card;
import ch.bbcag.mtgsorter.models.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TypeRepository extends CrudRepository<Type, Integer> {

    @Query("SELECT t FROM Type t WHERE t.type LIKE CONCAT('%', :type, '%')")
    Iterable<Type> findByType(@Param("type") String type);

    @Query("SELECT c FROM Card c WHERE c.name LIKE CONCAT('%', :name, '%')")
    Iterable<Type> findByCardName(@Param("name") String name);
}

