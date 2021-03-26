package com.econetwireless.epay.business.services.api;

import com.econetwireless.utils.pojo.PartnerResponse;

/**
 * Created by tnyamakura on 18/3/2017.
 */
public interface PartnerCodeValidator {

    public boolean validatePartnerCode(String partnerCode);

    public PartnerResponse validatePartnerCodeData(String partnerCode);
}
