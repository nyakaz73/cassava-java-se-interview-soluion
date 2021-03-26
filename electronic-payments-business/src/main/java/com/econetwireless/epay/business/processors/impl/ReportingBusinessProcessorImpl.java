package com.econetwireless.epay.business.processors.impl;

import com.econetwireless.epay.business.processors.api.ReportingBusinessProcessor;
import com.econetwireless.epay.business.rest.messages.TransactionsResponse;
import com.econetwireless.epay.business.services.api.ReportingService;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.enums.ResponseCode;

import java.util.List;

public class ReportingBusinessProcessorImpl implements ReportingBusinessProcessor {
    private ReportingService reportingService;
    public ReportingBusinessProcessorImpl(ReportingService reportingService){
        this.reportingService = reportingService;
    }

    @Override
    public TransactionsResponse getPartnerTransactions(String partnerCode) {
        final TransactionsResponse transactionsResponse = new TransactionsResponse();
        final List<SubscriberRequest> subscriberRequests = reportingService.findSubscriberRequestsByPartnerCode(partnerCode);
        transactionsResponse.setResponseCode(ResponseCode.SUCCESS.getCode());
        transactionsResponse.setNarrative("Successful search");
        transactionsResponse.setSubscriberRequests(subscriberRequests);
        transactionsResponse.setPartnerCode(partnerCode);
        return transactionsResponse;
    }
}
