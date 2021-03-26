package com.econetwireless.epay.business.processors.api;

import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.PartnerResponse;

public interface EpayBusinessRequestProcessor {
    AirtimeTopupResponse creditAirtime(AirtimeTopupRequest airtimeTopupRequest);
    AirtimeBalanceResponse enquireAirtimeBalance(String partnerCode, String msisdn);
    PartnerResponse validatePartnerCode(String partnerCode);
}
