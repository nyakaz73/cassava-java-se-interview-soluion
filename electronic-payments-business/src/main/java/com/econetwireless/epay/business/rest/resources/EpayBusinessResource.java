package com.econetwireless.epay.business.rest.resources;

import com.econetwireless.epay.business.processors.api.EpayBusinessRequestProcessor;
import com.econetwireless.epay.business.processors.api.ReportingBusinessProcessor;
import com.econetwireless.epay.business.rest.messages.TransactionsResponse;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.business.services.impl.CreditsServiceImpl;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.PartnerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transactions/business")

public class EpayBusinessResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(EpayBusinessResource.class);
    private EpayBusinessRequestProcessor epayBusinessRequestProcessor;
    private ReportingBusinessProcessor reportingBusinessProcessor;

    @PostMapping(value = "credits",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AirtimeTopupResponse creditAirtime(@RequestBody final AirtimeTopupRequest airtimeTopupRequest) {
        LOGGER.info(">>>>>>>NOW IN BUSINESS RESOURCE");
        return epayBusinessRequestProcessor.creditAirtime(airtimeTopupRequest);
    }


    @GetMapping(value = "enquiries/{partnerCode}/balances/{mobileNumber}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AirtimeBalanceResponse enquireAirtimeBalance(@PathVariable("partnerCode")  final String pCode, @PathVariable("mobileNumber") final String msisdn) {
        return epayBusinessRequestProcessor.enquireAirtimeBalance(pCode, msisdn);
    }

    @GetMapping(value = "validate/{partnerCode}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public PartnerResponse validatePartnerCode(@PathVariable("partnerCode") final String partnerCode){
        return epayBusinessRequestProcessor.validatePartnerCode(partnerCode);
    }

    @GetMapping(value = "transactions/{partnerCode}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TransactionsResponse getPartnerTransactions(@PathVariable("partnerCode") final String partnerCode) {
        return reportingBusinessProcessor.getPartnerTransactions(partnerCode);
    }
}
