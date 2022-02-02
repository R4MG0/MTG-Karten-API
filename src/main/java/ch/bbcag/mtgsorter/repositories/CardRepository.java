package ch.bbcag.mtgsorter.repositories;


import ch.bbcag.mtgsorter.models.Card;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends CrudRepository<Card, Integer> {
    @Query("SELECT c FROM Card c WHERE c.name LIKE CONCAT('%', :name, '%')")
    Iterable<Card> findByCardName(@Param("name") String name);

//    @Query("SELECT i FROM Card i " +
//            "JOIN i.linkedTags t " +
//            "WHERE t.name LIKE CONCAT('%', :tagName, '%')")
//    Iterable<Card> findByTagName(@Param("tagName") String tagName);
//
//    @Query("SELECT i FROM Card i " +
//            "JOIN i.linkedTags t " +
//            "WHERE i.name LIKE CONCAT('%', :name, '%') " +
//            "AND t.name LIKE CONCAT('%', :tagName, '%') ")
//    Iterable<Card> findByNameAndTagName(@Param("name") String name, @Param("tagName") String tagName);

}
