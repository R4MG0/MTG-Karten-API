package ch.bbcag.mtgsorter.controllers;

import ch.bbcag.mtgsorter.repositories.TypeRepository;
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

@WebMvcTest(controllers = TypeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TypeControllerTest {

    private static final String TEST_REQUEST_INPUT_DATA = """
             {
                "type": "artifact land"
             }
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TypeRepository typeRepository;

    @Test
    public void checkCreateNewType_whenValidType_thenIsOk() throws Exception {
        mockMvc.perform(post("/type/")
                        .contentType("application/json")
                        .content(TEST_REQUEST_INPUT_DATA))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkCreateNewType_whenInvalidType_thenIsBadRequest() throws Exception {
        mockMvc.perform(post("/type")
                        .contentType("application/json")
                        .content("{\"Invalid Id\":\"Invalid Subtype\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkGet_whenNoParam_thenAllTypesAreReturned() throws Exception {
        mockMvc.perform(get("/type")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGet_whenValidType_thenTypeIsReturned() throws Exception {
        String typeName = "artifact land";

        mockMvc.perform(get("/type")
                        .contentType("application/json")
                        .queryParam("type", typeName))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenValidType_thenIsOk() throws Exception {
        mockMvc.perform(put("/type")
                        .contentType("application/json")
                        .content("{\"type\":\"NewType\", \"typeID\":\"1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/type/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        mockMvc.perform(get("/type/" + 0)
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void checkGet_whenNotExistingSubtype_thenNoSubtypesAreReturned() throws Exception {
        String typeName = "NotExistingCard";

        mockMvc.perform(get("/subtype")
                        .contentType("application/json")
                        .queryParam("type", typeName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
