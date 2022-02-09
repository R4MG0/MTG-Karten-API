package ch.bbcag.mtgsorter.controllers;


import ch.bbcag.mtgsorter.models.Card;
import ch.bbcag.mtgsorter.repositories.CardRepository;

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
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardRepository cardRepository;


    @Operation(summary = "Find cards with a given name and mana color or with name and subtype or with name and type or with subtype and Color." +
            " If only name is blank, items with given color or subtype or type name are returned. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))})})
    @GetMapping
    public Iterable<Card> findByCardNameAndManaColorAndType(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String color,
                                                            @RequestParam(required = false) String type,
                                                            @RequestParam(required = false) String subtype) {
        if (Strings.isNotBlank(name) && Strings.isNotBlank(color))
            return cardRepository.findByManaColorAndCardName(name, color);
        if (Strings.isNotBlank(subtype) && Strings.isNotBlank(color))
            return cardRepository.findBySubtypeAndColor(subtype, color);
        if (Strings.isNotBlank(subtype) && Strings.isNotBlank(name))
            return cardRepository.findBySubtypeAndCardName(name, subtype);
        if (Strings.isNotBlank(type) && Strings.isNotBlank(name))
            return cardRepository.findByTypeAndCardName(name, type);
        if (Strings.isNotBlank(color)) return cardRepository.findByManaColor(color);
        if (Strings.isNotBlank(name)) return cardRepository.findByCardName(name);
        if (Strings.isNotBlank(type)) return cardRepository.findByType(type);
        if (Strings.isNotBlank(subtype)) return cardRepository.findBySubtype(subtype);

        return cardRepository.findAll();
    }


    @Operation(summary = "find a Card by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))}),
            @ApiResponse(responseCode = "404", description = "Card not found",
                    content = @Content)})
    @GetMapping(path = "{id}")
    public Card findById(@PathVariable Integer id) {
        try {
            return cardRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card couldn't be found");
        }
    }

    @Operation(summary = "Add a new card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card was added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))}),
            @ApiResponse(responseCode = "409", description = "Card could not be added",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))})})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void insert(@Valid @RequestBody @NotNull Card newCard) {
        try {
            cardRepository.save(newCard);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update a card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card was updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))}),
            @ApiResponse(responseCode = "409", description = "Card could not be updated",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))})})
    @PutMapping(consumes = "application/json", path = "{id}")
    public void update(@Valid @RequestBody @NotNull Card card) {
        try {
            cardRepository.save(card);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete a card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card was deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))}),
            @ApiResponse(responseCode = "404", description = "Card could not be deleted",
                    content = @Content)})
    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        try {
            cardRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card could not be deleted");
        }
    }
}
