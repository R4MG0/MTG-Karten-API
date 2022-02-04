package ch.bbcag.mtgsorter.controllers;

import ch.bbcag.mtgsorter.models.Type;
import ch.bbcag.mtgsorter.repositories.TypeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Find type with a given type." +
            " If type is blank, all types will be returned. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Type.class))})})
    @GetMapping
    public Iterable<Type> findByName(@RequestParam(required = false) String type) {
        if (Strings.isNotBlank(type)) {
            return typeRepository.findByType(type);
        }
        return typeRepository.findAll();
    }

    @Operation(summary = "find a type by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Type.class))}),
            @ApiResponse(responseCode = "404", description = "Type not found",
                    content = @Content)})
    @GetMapping(path = "{id}")
    public Type findById(@PathVariable Integer id) {
        try {
            return typeRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type could not be found");
        }
    }

    @Operation(summary = "Add a new type ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Type was added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Type.class))}),
            @ApiResponse(responseCode = "409", description = "Type could not be added",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Type.class))})})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void insert(@Valid @RequestBody @NotNull Type newType) {
        try {
            typeRepository.save(newType);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update a type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type was updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Type.class))}),
            @ApiResponse(responseCode = "409", description = "Type could not be updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Type.class))})})
    @PutMapping(consumes = "application/json")
    public void update(@Valid @RequestBody Type type) {
        try {
            typeRepository.save(type);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete a type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Type was deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Type.class))}),
            @ApiResponse(responseCode = "404", description = "Type could not be deleted",
                    content = @Content)})
    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        try {
            typeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type could not be deleted");
        }
    }
}
