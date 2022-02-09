package ch.bbcag.mtgsorter.models;


import javax.persistence.*;
import javax.persistence.GenerationType;
import javax.validation.constraints.*;
import java.util.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Mana getMana() {
        return mana;
    }

    public void setMana(Mana mana) {
        this.mana = mana;
    }

    public Subtype getSubtype() {
        return subtype;
    }

    public void setSubtype(Subtype subtype) {
        this.subtype = subtype;
    }

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
