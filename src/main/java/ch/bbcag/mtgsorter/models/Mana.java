package ch.bbcag.mtgsorter.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Mana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "color is required")
    @NotBlank(message = "Card name can't be empty")
    private String color;


    @OneToOne
    @JoinColumn(name = "id")
    private Card cards;

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
}
