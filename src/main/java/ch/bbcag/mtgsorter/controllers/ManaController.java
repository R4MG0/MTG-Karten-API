package ch.bbcag.mtgsorter.controllers;

import ch.bbcag.mtgsorter.models.Mana;
import ch.bbcag.mtgsorter.models.Type;
import ch.bbcag.mtgsorter.repositories.ManaRepository;
import org.apache.logging.log4j.util.Strings;
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
@RequestMapping("/mana")
public class ManaController {
    @Autowired
    private ManaRepository manaRepository;

    @GetMapping
    public Iterable<Mana> findByName(@RequestParam(required = false) String color) {
        if (Strings.isBlank(color)) {
            return manaRepository.findAll();
        }
        return manaRepository.findByName(color);
    }

    @GetMapping(path = "{id}")
    public Mana findById(@PathVariable Integer id) {
        try {
            return manaRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mana could not be found");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void insert(@Valid @RequestBody @NotNull Mana newMana) {
        try {
            manaRepository.save(newMana);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@Valid @RequestBody Mana mana) {
        try {
            manaRepository.save(mana);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        try {
            manaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mana could not be deleted");
        }
    }
}
