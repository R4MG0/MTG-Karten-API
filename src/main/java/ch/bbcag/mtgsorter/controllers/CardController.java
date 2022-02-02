package ch.bbcag.mtgsorter.controllers;


import ch.bbcag.mtgsorter.models.Card;
import ch.bbcag.mtgsorter.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardRepository cardRepository;

    @GetMapping(path = "{id}")
    public Card findById(@PathVariable Integer id) {
        try {
            return cardRepository.findById(id).orElseThrow();
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card couldn't be found");
        }
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void insert(@Valid @RequestBody @NotNull Card newCard) {
        try {
            cardRepository.save(newCard);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @PutMapping(consumes = "application/json")
    public void update(@Valid @RequestBody @NotNull Card card) {
        try {
            cardRepository.save(card);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        try {
            cardRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card could not be deleted");
        }
    }
}
