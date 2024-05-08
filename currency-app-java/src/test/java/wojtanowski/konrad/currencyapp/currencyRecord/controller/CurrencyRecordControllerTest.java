package wojtanowski.konrad.currencyapp.currencyRecord.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyRecordResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.GetCurrencyValueResponse;
import wojtanowski.konrad.currencyapp.currencyRecord.model.dto.PostCurrencyRecordRequest;
import wojtanowski.konrad.currencyapp.currencyRecord.service.api.CurrencyRecordService;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CurrencyRecordController.class)
class CurrencyRecordControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CurrencyRecordService currencyRecordService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testGetAllCurrencyRecords() throws Exception {
        given(currencyRecordService.getAllCurrencyRecords(any(), any()))
                .willReturn(new PageImpl<>(
                        List.of(getGetCurrencyRecordResponse1(), getGetCurrencyRecordResponse2())
                ));

        mockMvc.perform(get(CurrencyRecordController.CURRENCIES_GET_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()", is(2)));
    }

    @Test
    void testPostCurrencyRecordRequest() throws Exception {
        given(currencyRecordService.postCurrencyRecord(any())).willReturn(new GetCurrencyValueResponse(3.45F));

        mockMvc.perform(post(CurrencyRecordController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRecordRequest("EUR", "JAN KOWALSKI"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.currencyValue", is(3.45)));
    }

    @Test
    void testPostCurrencyRecordRequestNotFound() throws Exception {
        given(currencyRecordService.postCurrencyRecord(any())).willReturn(new GetCurrencyValueResponse(-1.0F));

        mockMvc.perform(post(CurrencyRecordController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRecordRequest("EUT", "JAN KOWALSKI"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPostCurrencyRecordRequestNullCurrencyName() throws Exception {
        given(currencyRecordService.postCurrencyRecord(any())).willReturn(new GetCurrencyValueResponse(3.45F));

        mockMvc.perform(post(CurrencyRecordController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRecordRequest(null, "JAN KOWALSKI"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostCurrencyRecordRequestNullRequesterName() throws Exception {
        given(currencyRecordService.postCurrencyRecord(any())).willReturn(new GetCurrencyValueResponse(3.45F));

        mockMvc.perform(post(CurrencyRecordController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRecordRequest("EUR", null))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostCurrencyRecordRequestCurrencyNameToLong() throws Exception {
        given(currencyRecordService.postCurrencyRecord(any())).willReturn(new GetCurrencyValueResponse(3.45F));

        mockMvc.perform(post(CurrencyRecordController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRecordRequest(
                                "EURR",
                                "JAN KOWALSKI"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostCurrencyRecordRequestCurrencyNameToShort() throws Exception {
        given(currencyRecordService.postCurrencyRecord(any())).willReturn(new GetCurrencyValueResponse(3.45F));

        mockMvc.perform(post(CurrencyRecordController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRecordRequest(
                                "EU",
                                "JAN KOWALSKI"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPostCurrencyRecordRequestRequesterNameToLong() throws Exception {
        given(currencyRecordService.postCurrencyRecord(any())).willReturn(new GetCurrencyValueResponse(3.45F));

        mockMvc.perform(post(CurrencyRecordController.CURRENCY_POST_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PostCurrencyRecordRequest(
                                "EUR",
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))))
                .andExpect(status().isBadRequest());
    }



    GetCurrencyRecordResponse getGetCurrencyRecordResponse1() {
        return new GetCurrencyRecordResponse("EUR", "JAN KOWALSKI", new Timestamp(System.currentTimeMillis()), 4.6F);
    }

    GetCurrencyRecordResponse getGetCurrencyRecordResponse2() {
        return new GetCurrencyRecordResponse("EUR", "KONRAD WISNIEWSKI", new Timestamp(System.currentTimeMillis()), 4.5F);
    }
}