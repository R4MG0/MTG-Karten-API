package ch.bbcag.mtgsorter.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
public class Subtype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "name is required")
    @NotBlank(message = "Card name can't be empty")
    @Column(name = "name")
    private String subtype;

    @OneToMany
    @JoinColumn(name = "id")
    @JsonBackReference
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return subtype;
    }

    public void setName(String name) {
        this.subtype = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtype)) return false;
        Subtype subtype1 = (Subtype) o;
        return Objects.equals(id, subtype1.id) && Objects.equals(subtype, subtype1.subtype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subtype);
    }
}
