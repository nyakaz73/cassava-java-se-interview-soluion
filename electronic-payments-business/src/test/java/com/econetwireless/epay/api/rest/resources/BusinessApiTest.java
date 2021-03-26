package com.econetwireless.epay.api.rest.resources;

import com.econetwireless.epay.business.config.EpayBusinessWebConfig;
import com.econetwireless.epay.business.config.RootConfig;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(classes = {RootConfig.class}),
        @ContextConfiguration(classes = {EpayBusinessWebConfig.class})
})
public class BusinessApiTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    public String partnerCode;

    @Before
    public void  setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        partnerCode ="hot-recharge";
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/testfiles/integration-test-load-partners.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/testfiles/integration-test-cleanup-partners.sql")
    public void airtimeTopupShouldReturnResponseCodeSUCCESSIfAllOtherSystemsAreUp() throws Exception {

            final AirtimeTopupRequest airtimeTopupRequest = new AirtimeTopupRequest();
            airtimeTopupRequest.setAmount(2.73);
            airtimeTopupRequest.setMsisdn("773303584");
            airtimeTopupRequest.setPartnerCode(partnerCode);
            airtimeTopupRequest.setReferenceNumber("TOPUP-REF-0123");
            this.mockMvc.perform(post("/transactions/business/credits").content(asJsonString(airtimeTopupRequest)).
                    contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).
                    andExpect(status().isOk()).
                    andExpect(content().contentType("application/json;charset=UTF-8")).
                    andExpect(jsonPath("$.responseCode").value(ResponseCode.SUCCESS.getCode())).
                    andExpect(jsonPath("$.balance").value(is(greaterThan(airtimeTopupRequest.getAmount()))));

    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/testfiles/integration-test-load-partners.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/testfiles/integration-test-cleanup-partners.sql")
    public void airtimeBalanceEnquiryShouldReturnResponseCodeSUCCESSIfAllOtherSystemsAreUp() throws Exception {
        this.mockMvc.perform(get("/transactions/business/enquiries/{partnerCode}/balances/{mobileNumber}", partnerCode, "774222278").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.responseCode").value(ResponseCode.SUCCESS.getCode())).
                andExpect(jsonPath("$.amount").value(is(greaterThan(0d))));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/testfiles/integration-test-load-partners.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/testfiles/integration-test-cleanup-partners.sql")
    public void validatePartnerCode() throws Exception {
        this.mockMvc.perform(get("/transactions/business/validate/{partnerCode}", partnerCode).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.code").value(partnerCode));

    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/testfiles/integration-test-load-partners.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/testfiles/integration-test-cleanup-partners.sql")
    public void should_fail_partner_code_is_not_provided() throws Exception {
        this.mockMvc.perform(get("/transactions/business/validate/{partnerCode}", "").accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).
                andExpect(status().is4xxClientError());
//

    }
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/testfiles/integration-test-load-partners.sql", "/testfiles/integration-test-load-request.sql"})
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/testfiles/integration-test-cleanup-partners.sql")
    public void shouldReturnStatusOkIfRequestsAreMoreThanOne() throws Exception {
        this.mockMvc.perform(get("/transactions/business/transactions/{partnerCode}", partnerCode).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.responseCode").value(ResponseCode.SUCCESS.getCode())).
                andExpect(jsonPath("$.subscriberRequests[0].id").value(12345));
    }


    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/testfiles/integration-test-load-partners.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/testfiles/integration-test-cleanup-partners.sql")
    public void it_should_fail_if_reference_number_is_missing_on_airtime_request() throws Exception {

        final AirtimeTopupRequest airtimeTopupRequest = new AirtimeTopupRequest();
        airtimeTopupRequest.setAmount(2.73);
        airtimeTopupRequest.setMsisdn("773303584");
        airtimeTopupRequest.setPartnerCode(partnerCode);
        airtimeTopupRequest.setReferenceNumber("");
        this.mockMvc.perform(post("/transactions/business/credits").content(asJsonString(airtimeTopupRequest)).
                contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.responseCode").value(ResponseCode.INVALID_REQUEST.getCode()));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/testfiles/integration-test-load-partners.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/testfiles/integration-test-cleanup-partners.sql")
    public void it_should_fail_if_no_request_is_provided_on_airtime_request() throws Exception {

        final AirtimeTopupRequest airtimeTopupRequest = new AirtimeTopupRequest();
//        airtimeTopupRequest.setAmount(2.73);
//        airtimeTopupRequest.setMsisdn("773303584");
//        airtimeTopupRequest.setPartnerCode(partnerCode);
//        airtimeTopupRequest.setReferenceNumber("");
        this.mockMvc.perform(post("/transactions/business/credits").content(asJsonString(airtimeTopupRequest)).
                contentType(MediaType.parseMediaType("application/json;charset=UTF-8")).accept(MediaType.parseMediaType("application/json;charset=UTF-8"))).
                andExpect(status().isOk()).
                andExpect(content().contentType("application/json;charset=UTF-8")).
                andExpect(jsonPath("$.responseCode").value(ResponseCode.INVALID_REQUEST.getCode()));
    }


    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
