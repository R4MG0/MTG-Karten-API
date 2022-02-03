package ch.bbcag.mtgsorter.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    public void checkGet_whenNoParam_thenAllItemsAreReturned() throws Exception {

        mockMvc.perform(get("/card")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
