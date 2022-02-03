package ch.bbcag.mtgsorter.controllers;

import ch.bbcag.mtgsorter.models.Type;
import ch.bbcag.mtgsorter.repositories.TypeRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeRepository typeRepository;


    @GetMapping
    public Iterable<Type> findByName(@RequestParam(required = false) String type) {
        if (Strings.isBlank(type)) {
            return typeRepository.findAll();
        }
        return typeRepository.findByType(type);
    }

    @GetMapping(path = "{id}")
    public Type findById(@PathVariable Integer id) {
        try {
            return typeRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type could not be found");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void insert(@Valid @RequestBody @NotNull Type newType) {
        try {
            typeRepository.save(newType);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PutMapping(consumes = "application/json")
    public void update(@Valid @RequestBody Type type) {
        try {
            typeRepository.save(type);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        try {
            typeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type could not be deleted");
        }
    }
}
