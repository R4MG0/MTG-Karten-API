package ch.bbcag.mtgsorter.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "type is required")
    @NotBlank(message = "Type can't be empty")
    @Column(name = "name")
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String name) {
        this.type = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Type)) return false;
        Type type1 = (Type) o;
        return Objects.equals(type, type1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
