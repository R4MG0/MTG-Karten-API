package ch.bbcag.mtgsorter.controllers;


import ch.bbcag.mtgsorter.repositories.SubtypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SubtypeConroller.class)
@AutoConfigureMockMvc(addFilters = false)
public class SubtypeControllerTest {

    private static final String TEST_REQUEST_INPUT_DATA = """
             {
                "name": "zombie"
             }
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubtypeRepository subtypeRepository;

    @Test
    public void checkCreateNewSubtype_whenValidSubtype_thenIsOk() throws Exception {
        mockMvc.perform(post("/subtype/")
                        .contentType("application/json")
                        .content(TEST_REQUEST_INPUT_DATA))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkCreateNewSubtype_whenInvalidSubtype_thenIsBadRequest() throws Exception {
        mockMvc.perform(post("/subtype")
                        .contentType("application/json")
                        .content("{\"Invalid Id\":\"Invalid Subtype\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkGet_whenNoParam_thenAllSubtypesAreReturned() throws Exception {
        mockMvc.perform(get("/subtype")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGet_whenValidSubtype_thenSubtypeIsReturned() throws Exception {
        String subtypeName = "zombie";

        mockMvc.perform(get("/subtype")
                        .contentType("application/json")
                        .queryParam("subtype", subtypeName))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenValidType_thenIsOk() throws Exception {
        mockMvc.perform(put("/subtype")
                        .contentType("application/json")
                        .content("{\"name\":\"NewSubtype\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/subtype/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        mockMvc.perform(get("/subtype/" + 0)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void checkGet_whenNotExistingSubtype_thenNoSubtypesAreReturned() throws Exception {
        String subtypeName = "NotExistingCard";

        mockMvc.perform(get("/subtype")
                        .contentType("application/json")
                        .queryParam("subtype", subtypeName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}
