package ch.bbcag.mtgsorter.repositories;

import ch.bbcag.mtgsorter.models.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TypeRepository extends CrudRepository<Type, Integer> {

    @Query("SELECT t FROM Type t WHERE t.name LIKE CONCAT('%', :name, '%')")
    Iterable<Type> findByName(@Param("name") String name);

}

