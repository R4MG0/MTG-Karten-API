package ch.bbcag.mtgsorter.controllers;

import ch.bbcag.mtgsorter.models.Subtype;
import ch.bbcag.mtgsorter.repositories.SubtypeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Find subtype with a given subtype." +
            " If subtype is blank, all subtypes will be returned. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subtype found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subtype.class))})})
    @GetMapping
    public Iterable<Subtype> findByName(@RequestParam(required = false) String subtype) {
        if (Strings.isNotBlank(subtype)) {
           return subtypeRepository.findByName(subtype);
        }
        return subtypeRepository.findAll();
    }

    @Operation(summary = "find a subtype by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subtype found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subtype.class))}),
            @ApiResponse(responseCode = "404", description = "Subtype not found",
                    content = @Content)})
    @GetMapping(path = "{id}")
    public Subtype findById(@PathVariable Integer id) {
        try {
            return subtypeRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subtype could not be found");
        }
    }

    @Operation(summary = "Add a new subtype ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subtype was added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subtype.class))}),
            @ApiResponse(responseCode = "409", description = "Subtype could not be added",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subtype.class))})})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void insert(@Valid @RequestBody @NotNull Subtype newSubtype) {
        try {
            subtypeRepository.save(newSubtype);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update a subtype")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subtype was updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subtype.class))}),
            @ApiResponse(responseCode = "409", description = "Subtype could not be updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subtype.class))})})
    @PutMapping(consumes = "application/json")
    public void update(@Valid @RequestBody Subtype subtype) {
        try {
            subtypeRepository.save(subtype);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete a subtype")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subtype was deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Subtype.class))}),
            @ApiResponse(responseCode = "404", description = "Subtype could not be deleted",
                    content = @Content)})
    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        try {
            subtypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subtype could not be deleted");
        }
    }
}
