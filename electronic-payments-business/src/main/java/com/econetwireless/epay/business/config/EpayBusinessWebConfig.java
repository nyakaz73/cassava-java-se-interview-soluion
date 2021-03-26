package com.econetwireless.epay.business.config;

import com.econetwireless.epay.business.processors.api.EpayBusinessRequestProcessor;
import com.econetwireless.epay.business.processors.impl.EpayBusinessRequestProcessorImpl;
import com.econetwireless.epay.business.services.api.CreditsService;
import com.econetwireless.epay.business.services.api.EnquiriesService;
import com.econetwireless.epay.business.services.api.PartnerCodeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.econetwireless.epay.business.rest",
})
@Import({EpayBusinessAspectConfig.class})
public class EpayBusinessWebConfig {
    @Bean
    public EpayBusinessRequestProcessor epayBusinessRequestProcessor(final CreditsService creditsService, final EnquiriesService enquiriesService, final PartnerCodeValidator partnerCodeValidator){
        return new EpayBusinessRequestProcessorImpl(creditsService,enquiriesService,partnerCodeValidator);
    }
}
