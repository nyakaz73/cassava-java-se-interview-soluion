package com.econetwireless.epay.business.services.impl;

import com.econetwireless.epay.business.rest.resources.EpayBusinessResource;
import com.econetwireless.epay.business.services.api.PartnerCodeValidator;
import com.econetwireless.epay.dao.requestpartner.api.RequestPartnerDao;
import com.econetwireless.epay.domain.RequestPartner;
import com.econetwireless.utils.enums.ResponseCode;
import com.econetwireless.utils.execeptions.EpayException;
import com.econetwireless.utils.pojo.PartnerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tnyamakura on 18/3/2017.
 */
public class PartnerCodeValidatorImpl implements PartnerCodeValidator{

    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerCodeValidator.class);

    private RequestPartnerDao requestPartnerDao;

    public PartnerCodeValidatorImpl(RequestPartnerDao requestPartnerDao) {
        super();
        this.requestPartnerDao = requestPartnerDao;
    }

    @Override
    public boolean validatePartnerCode(final String partnerCode) {
        final boolean isValidPartner = requestPartnerDao.findByCode(partnerCode) != null;
        if(!isValidPartner) {
            throw new EpayException(ResponseCode.INVALID_REQUEST, "Invalid partner code supplied : "+partnerCode);
        }
        return isValidPartner;
    }

    @Override
    public PartnerResponse validatePartnerCodeData(String partnerCode) {

        RequestPartner requestPartner = requestPartnerDao.findByCode(partnerCode);
        if(requestPartner == null) {
            throw new EpayException(ResponseCode.INVALID_REQUEST, "Invalid partner code supplied : "+partnerCode);
        }
        PartnerResponse response = new PartnerResponse();
        response.setCode(requestPartner.getCode());
        response.setDescription(requestPartner.getDescription());
        response.setName(requestPartner.getName());
        LOGGER.info(response.toString());
        return response;
    }
}
