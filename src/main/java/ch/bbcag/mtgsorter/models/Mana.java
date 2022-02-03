package ch.bbcag.mtgsorter.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Mana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "color is required")
    @NotBlank(message = "Card name can't be empty")
    @Column(name = "color")
    private String color;


    @OneToOne
    @JoinColumn(name = "id")
    @JsonBackReference
    private Card cards;

    public Card getCards() {
        return cards;
    }

    public void setCards(Card cards) {
        this.cards = cards;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String name) {
        this.color = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mana)) return false;
        Mana mana = (Mana) o;
        return Objects.equals(id, mana.id) && Objects.equals(color, mana.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color);
    }
}
