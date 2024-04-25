package wojtanowski.konrad.currencyapp.currencyRequest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyRequestsDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.GetCurrencyValueDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.model.dto.PostCurrencyRequestDTO;
import wojtanowski.konrad.currencyapp.currencyRequest.service.api.CurrencyRequestService;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CurrencyRequestController.class)
class CurrencyRequestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CurrencyRequestService currencyRequestService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetAllCurrencyRequests() throws Exception {
        given(currencyRequestService.getAllCurrencyRequests())
                .willReturn(new GetCurrencyRequestsDTO(
                        Arrays.asList(getGetCurrencyRequestDTO1(), getGetCurrencyRequestDTO2())
                ));

        mockMvc.perform(get(CurrencyRequestController.CURRENCIES_GET_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currencyRequests").isArray())
                .andExpect(jsonPath("$.currencyRequests.length()", is(2)));
    }

    @Test
    void testPostCurrencyRequest() throws Exception {
        given(currencyRequestService.postCurrencyRequest(any())).willReturn(new GetCurrencyValueDTO(3.45F));

        mockMvc.perform(post(CurrencyRequestController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRequestDTO("EUR", "JAN KOWALSKI"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currencyValue", is(3.45)));
    }

    @Test
    void testPostCurrencyRequestNotFound() throws Exception {
        given(currencyRequestService.postCurrencyRequest(any())).willReturn(new GetCurrencyValueDTO(-1.0F));

        mockMvc.perform(post(CurrencyRequestController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRequestDTO("EUT", "JAN KOWALSKI"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostCurrencyRequestNullCurrencyName() throws Exception {
        given(currencyRequestService.postCurrencyRequest(any())).willReturn(new GetCurrencyValueDTO(3.45F));

        mockMvc.perform(post(CurrencyRequestController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRequestDTO(null, "JAN KOWALSKI"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostCurrencyRequestNullRequesterName() throws Exception {
        given(currencyRequestService.postCurrencyRequest(any())).willReturn(new GetCurrencyValueDTO(3.45F));

        mockMvc.perform(post(CurrencyRequestController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRequestDTO("EUR", null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostCurrencyRequestCurrencyNameToLong() throws Exception {
        given(currencyRequestService.postCurrencyRequest(any())).willReturn(new GetCurrencyValueDTO(3.45F));

        mockMvc.perform(post(CurrencyRequestController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRequestDTO(
                                "EURR",
                                "JAN KOWALSKI"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostCurrencyRequestCurrencyNameToShort() throws Exception {
        given(currencyRequestService.postCurrencyRequest(any())).willReturn(new GetCurrencyValueDTO(3.45F));

        mockMvc.perform(post(CurrencyRequestController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRequestDTO(
                                "EU",
                                "JAN KOWALSKI"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostCurrencyRequestRequesterNameToLong() throws Exception {
        given(currencyRequestService.postCurrencyRequest(any())).willReturn(new GetCurrencyValueDTO(3.45F));

        mockMvc.perform(post(CurrencyRequestController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRequestDTO(
                                "EUR",
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))))
                .andExpect(status().isBadRequest());
    }



    GetCurrencyRequestDTO getGetCurrencyRequestDTO1() {
        return new GetCurrencyRequestDTO("EUR", "JAN KOWALSKI", new Timestamp(System.currentTimeMillis()), 4.6F);
    }

    GetCurrencyRequestDTO getGetCurrencyRequestDTO2() {
        return new GetCurrencyRequestDTO("EUR", "KONRAD WISNIEWSKI", new Timestamp(System.currentTimeMillis()), 4.5F);
    }
}