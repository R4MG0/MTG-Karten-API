package ch.bbcag.mtgsorter.models;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.persistence.GenerationType;
import javax.validation.constraints.*;
import java.util.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "name is required")
    @NotBlank(message = "Card name can't be empty")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @OneToOne
    @JoinColumn(name = "Mana_id")
    private Mana mana;

    @ManyToOne
    @JoinColumn(name = "subtype_id")
    private Subtype subtype;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(name, card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
