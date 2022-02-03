package ch.bbcag.mtgsorter.repositories;


import ch.bbcag.mtgsorter.models.Card;
import ch.bbcag.mtgsorter.models.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends CrudRepository<Card, Integer> {
    @Query("SELECT c FROM Card c WHERE c.name LIKE CONCAT('%', :name, '%')")
    Iterable<Card> findByCardName(@Param("name") String name);

    @Query("SELECT c FROM Card c " +
            "JOIN c.mana m " +
            "WHERE m.color LIKE CONCAT('%', :manaColor, '%')")
    Iterable<Card> findByManaColor(@Param("manaColor") String manaColor);

    @Query("SELECT c FROM Card c " +
            "JOIN c.mana m " +
            "WHERE c.name LIKE CONCAT('%', :name, '%') " +
            "AND m.color LIKE CONCAT('%', :color, '%') ")
    Iterable<Card> findByManaColorAndCardName(@Param("name") String name, @Param("color") String color);

    @Query("SELECT c FROM Card c " +
            "JOIN c.type t " +
            "WHERE c.name LIKE CONCAT('%', :name, '%') " +
            "AND t.type LIKE CONCAT('%', :type, '%') ")
    Iterable<Card> findByTypeAndCardName(@Param("name") String name, @Param("type") String type);

    @Query("SELECT c FROM Card c " +
            "JOIN c.subtype s " +
            "WHERE c.name LIKE CONCAT('%', :name, '%') " +
            "AND s.subtype LIKE CONCAT('%', :subtype, '%') ")
    Iterable<Card> findBySubtypeAndCardName(@Param("name") String name, @Param("subtype") String subtype);

}
