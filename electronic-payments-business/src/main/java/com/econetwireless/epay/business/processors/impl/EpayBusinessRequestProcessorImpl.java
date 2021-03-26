package com.econetwireless.epay.business.processors.impl;

import com.econetwireless.epay.business.processors.api.EpayBusinessRequestProcessor;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.business.services.api.EnquiriesService;
import com.econetwireless.epay.business.services.api.PartnerCodeValidator;
import com.econetwireless.utils.formatters.MobileNumberUtils;
import com.econetwireless.utils.messages.AirtimeBalanceResponse;
import com.econetwireless.utils.messages.AirtimeTopupRequest;
import com.econetwireless.utils.messages.AirtimeTopupResponse;
import com.econetwireless.utils.pojo.PartnerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EpayBusinessRequestProcessorImpl implements EpayBusinessRequestProcessor {
    private CreditsService creditsService;
    private EnquiriesService enquiriesService;
    private PartnerCodeValidator partnerCodeValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(EpayBusinessRequestProcessorImpl.class);


    public  EpayBusinessRequestProcessorImpl(CreditsService creditsService, EnquiriesService enquiriesService, PartnerCodeValidator partnerCodeValidator){
        this.creditsService = creditsService;
        this.enquiriesService = enquiriesService;
        this.partnerCodeValidator = partnerCodeValidator;
    }
    @Override
    public AirtimeTopupResponse creditAirtime(AirtimeTopupRequest airtimeTopupRequest) {
        LOGGER.info(">>>>>>>>>>>>>>CREDIST FROM BUSINESSS");
        System.out.println("NOW IN BUSINESS");
        airtimeTopupRequest.setMsisdn(MobileNumberUtils.formatMobileToInternationalMode(airtimeTopupRequest.getMsisdn()));
        return creditsService.credit(airtimeTopupRequest);
    }

    @Override
    public AirtimeBalanceResponse enquireAirtimeBalance(String partnerCode, String msisdn) {
        return enquiriesService.enquire(partnerCode,msisdn);
    }

    @Override
    public PartnerResponse validatePartnerCode(String partnerCode) {
        return  partnerCodeValidator.validatePartnerCodeData(partnerCode);
    }

}
