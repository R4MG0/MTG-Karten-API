package ch.bbcag.mtgsorter.controllers;

import ch.bbcag.mtgsorter.models.Mana;
import ch.bbcag.mtgsorter.models.Subtype;
import ch.bbcag.mtgsorter.models.Type;
import ch.bbcag.mtgsorter.repositories.SubtypeRepository;
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
@RequestMapping("/subtype")
public class SubtypeConroller {
    @Autowired
    private SubtypeRepository subtypeRepository;

    @GetMapping
    public Iterable<Subtype> findByName(@RequestParam(required = false) String color) {
        if (Strings.isBlank(color)) {
            return subtypeRepository.findAll();
        }
        return subtypeRepository.findByName(color);
    }

    @GetMapping(path = "{id}")
    public Subtype findById(@PathVariable Integer id) {
        try {
            return subtypeRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mana could not be found");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void insert(@Valid @RequestBody @NotNull Subtype newSubtype) {
        try {
            subtypeRepository.save(newSubtype);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@Valid @RequestBody Subtype subtype) {
        try {
            subtypeRepository.save(subtype);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        try {
            subtypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mana could not be deleted");
        }
    }
}
