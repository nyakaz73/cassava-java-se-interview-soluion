package com.econetwireless.epay.api.processors.impl;

import com.econetwireless.epay.api.processors.api.ReportingProcessor;
import com.econetwireless.epay.api.rest.resources.messages.TransactionsResponse;
import com.econetwireless.epay.business.services.api.ReportingService;
import com.econetwireless.epay.business.services.impl.CreditsServiceImpl;
import com.econetwireless.epay.domain.SubscriberRequest;
import com.econetwireless.utils.enums.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by tnyamakura on 18/3/2017.
 */
public class ReportingProcessorImpl implements ReportingProcessor{

    private ReportingService reportingService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditsServiceImpl.class);

    public ReportingProcessorImpl(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @Override
    public TransactionsResponse getPartnerTransactions(final String partnerCode) {
        LOGGER.info(">>>>>>>>>>>>>>>>>>.NOW IN GET PARTNER TRANSACTION {}: ",partnerCode);
        final TransactionsResponse transactionsResponse = new TransactionsResponse();
        LOGGER.info(">>>>>>>>>>>>>>>>>>.NOW IN GET PARTNER TRANSACTION {}: ",partnerCode);
        final List<SubscriberRequest> subscriberRequests = reportingService.findSubscriberRequestsByPartnerCode(partnerCode);
        LOGGER.info(">>>>>>>>>>>>>>>The returned subscribers by code {} ",subscriberRequests.toString());
        transactionsResponse.setResponseCode(ResponseCode.SUCCESS.getCode());
        transactionsResponse.setNarrative("Successful search");
        transactionsResponse.setSubscriberRequests(subscriberRequests);
        transactionsResponse.setPartnerCode(partnerCode);
        return transactionsResponse;

    }
}
