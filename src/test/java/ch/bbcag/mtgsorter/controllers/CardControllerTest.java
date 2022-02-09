package ch.bbcag.mtgsorter.controllers;

import ch.bbcag.mtgsorter.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CardController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CardControllerTest {

    private static final String TEST_REQUEST_INPUT_DATA = """
             {
                    "name": "Restless Bloodseeker",
                    "type": {
                        "id": 1
                    },
                    "mana": {
                        "id": 11
                    },
                    "subtype": {
                        "id": 1
                    }
                }
            """;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardRepository cardRepository;

    @Test
    public void checkCreateNewCard_whenValidCard_thenIsOk() throws Exception {
        mockMvc.perform(post("/card")
                        .contentType("application/json")
                        .content(TEST_REQUEST_INPUT_DATA))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkCreateNewCard_whenInvalidCard_thenIsBadRequest() throws Exception {
        mockMvc.perform(post("/card")
                        .contentType("application/json")
                        .content("{\"Invalid Name\":\"Invalid Data\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkGet_whenNoParam_thenAllCardsAreReturned() throws Exception {
        mockMvc.perform(get("/card")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGet_whenValidName_thenCardIsReturned() throws Exception {
        String cardName = "Restless Bloodseeker";

        mockMvc.perform(get("/card")
                        .contentType("application/json")
                        .queryParam("name", cardName))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGet_whenNotExistingName_thenNoCardsAreReturned() throws Exception {
        String cardName = "NotExistingCard";

        mockMvc.perform(get("/card")
                        .contentType("application/json")
                        .queryParam("card", cardName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void checkPut_whenValidCard_thenIsOk() throws Exception {
        mockMvc.perform(put("/card/1")
                        .contentType("application/json")
                        .content("{\"name\":\"NewCard\", \"cardId\":\"1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenInValidCard_thenIsBadRequest() throws Exception {
        mockMvc.perform(put("/card/1")
                        .contentType("application/json")
                        .content("{\"invalidName\":\"NewCard1\", \"InvalidcardId\":\"invalidId\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/card/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        mockMvc.perform(get("/card/" + 0)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

}
