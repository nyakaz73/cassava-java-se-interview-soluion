package com.econetwireless.epay.business.processors.api;

import com.econetwireless.epay.business.rest.messages.TransactionsResponse;
import com.econetwireless.epay.business.services.api.ReportingService;

public interface ReportingBusinessProcessor {
    TransactionsResponse getPartnerTransactions(String partnerCode);
}
