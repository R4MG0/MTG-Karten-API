package ch.bbcag.mtgsorter.controllers;


import ch.bbcag.mtgsorter.repositories.ManaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ManaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ManaControllerTest {

    private static final String TEST_REQUEST_INPUT_DATA = """
             {
                    "color": "blue and colorless"
                }
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManaRepository manaRepository;

    @Test
    public void checkCreateNewMana_whenValidMana_thenIsOk() throws Exception {
        mockMvc.perform(post("/mana/")
                        .contentType("application/json")
                        .content(TEST_REQUEST_INPUT_DATA))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkCreateNewMana_whenInvalidMana_thenIsBadRequest() throws Exception {
        mockMvc.perform(post("/mana")
                        .contentType("application/json")
                        .content("{\"Invalid Id\":\"Invalid Color\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkGet_whenNoParam_thenAllManaAreReturned() throws Exception {
        mockMvc.perform(get("/mana")
                    .contentType("application/json"))
        .andExpect(status().isOk());
    }

    @Test
    public void checkGet_whenValidColor_thenManaIsReturned() throws Exception {
        String manaName = "blue and colorless";

        mockMvc.perform(get("/mana")
                        .contentType("application/json")
                        .queryParam("color", manaName))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGet_whenNotExistingColor_thenNoColorsAreReturned() throws Exception {
        String manaName = "NotExistingCard";

        mockMvc.perform(get("/mana")
                        .contentType("application/json")
                        .queryParam("mana", manaName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void checkPut_whenValidMana_thenIsOk() throws Exception {
        mockMvc.perform(put("/mana")
                        .contentType("application/json")
                        .content("{\"color\":\"NewColor\", \"manaID\":\"1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenInValidMana_thenIsBadRequest() throws Exception {
        mockMvc.perform(put("/mana")
                        .contentType("application/json")
                        .content("{\"wrongFieldName\":\"mana1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/mana/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        mockMvc.perform(get("/mana/" + 0)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }


}
