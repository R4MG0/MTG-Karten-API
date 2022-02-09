package ch.bbcag.mtgsorter.controllers;

import ch.bbcag.mtgsorter.models.Mana;
import ch.bbcag.mtgsorter.repositories.ManaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.util.Strings;
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
    private ManaRepository manaRepository;

    public ManaController(ManaRepository manaRepository) {
        this.manaRepository = manaRepository;
    }

    @Operation(summary = "Find Mana with a given color." +
            " If mana color is blank, all mana colors will be returned. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mana found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mana.class))})})
    @GetMapping
    public Iterable<Mana> findByName(@RequestParam(required = false) String color) {
        if (Strings.isNotBlank(color)) {
            return manaRepository.findByManaColor(color);
        }
        return manaRepository.findAll();
    }

    @Operation(summary = "find a Mana color by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mana color found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mana.class))}),
            @ApiResponse(responseCode = "404", description = "Mana color not found",
                    content = @Content)})
    @GetMapping(path = "{id}")
    public Mana findById(@PathVariable Integer id) {
        try {
            return manaRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mana could not be found");
        }
    }

    @Operation(summary = "Add a new mana color")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mana color was added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mana.class))}),
            @ApiResponse(responseCode = "409", description = "Mana color could not be added",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mana.class))})})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void insert(@Valid @RequestBody @NotNull Mana newMana) {
        try {
            manaRepository.save(newMana);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update a mana color")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mana color was updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mana.class))}),
            @ApiResponse(responseCode = "409", description = "Mana color could not be updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mana.class))})})
    @PutMapping(consumes = "application/json")
    public void update(@Valid @RequestBody Mana mana) {
        try {
            manaRepository.save(mana);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete a mana color")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mana color was deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Mana.class))}),
            @ApiResponse(responseCode = "404", description = "Mana color could not be deleted",
                    content = @Content)})
    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        try {
            manaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mana could not be deleted");
        }
    }
}
