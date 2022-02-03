package ch.bbcag.mtgsorter.repositories;

import ch.bbcag.mtgsorter.models.Subtype;
import ch.bbcag.mtgsorter.models.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SubtypeRepository extends CrudRepository<Subtype, Integer> {
    @Query("SELECT s FROM Subtype s WHERE s.subtype LIKE CONCAT('%', :name, '%')")
    Iterable<Subtype> findByName(@Param("name") String name);

}
