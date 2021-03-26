package com.econetwireless.epay.business.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.econetwireless.epay.business.aspects"})
public class EpayBusinessAspectConfig {
}
