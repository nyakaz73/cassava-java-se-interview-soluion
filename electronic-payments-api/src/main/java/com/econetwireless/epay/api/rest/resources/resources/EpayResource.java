package com.econetwireless.epay.api.rest.resources.resources;

import com.econetwireless.epay.api.processors.api.EpayRequestProcessor;
import com.econetwireless.epay.api.processors.api.ReportingProcessor;
import com.econetwireless.epay.api.rest.resources.messages.TransactionsResponse;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tnyamakura on 18/3/2017.
 */
@RestController
@RequestMapping("resources/services")
public class EpayResource {

    private EpayRequestProcessor epayRequestProcessor;


    private ReportingProcessor reportingProcessor;
    private static final Logger LOGGER = LoggerFactory.getLogger(EpayResource.class);

    @GetMapping(value = "enquiries/{partnerCode}/balances/{mobileNumber}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AirtimeBalanceResponse enquireAirtimeBalance(@PathVariable("partnerCode")  final String pCode, @PathVariable("mobileNumber") final String msisdn) {
        return epayRequestProcessor.enquireAirtimeBalance(pCode, msisdn);
    }

    @PostMapping(value = "credits",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AirtimeTopupResponse creditAirtime(@RequestBody final AirtimeTopupRequest airtimeTopupRequest) {
        return epayRequestProcessor.creditAirtime(airtimeTopupRequest);
    }

    @GetMapping(value = "transactions/{partnerCode}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TransactionsResponse getPartnerTransactions(@PathVariable("partnerCode") final String partnerCode) {
        LOGGER.info("<<<<<<<<<<<<<<<<Parsed partnerCOde>>>>>>>>>>>>>>> {}", partnerCode);
        return reportingProcessor.getPartnerTransactions(partnerCode);
    }


}
