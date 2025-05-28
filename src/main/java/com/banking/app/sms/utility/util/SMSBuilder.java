package com.banking.app.sms.utility.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
@Component
public class SMSBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(SMSBuilder.class);
    @Autowired
    private MessageSource messageSource;
    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.account_token}")
    private String AUTH_TOKEN;
    @Value("${twilio.from-mobile-number}")
    private String fromMobileNumber;

    public Message send(StringBuilder toNumber, String messageText) {
        LOGGER.info("Sending SMS to the customer with mobile number {}",toNumber);
//        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
//        return Message.creator(new PhoneNumber(new String(toNumber)),new PhoneNumber(fromMobileNumber), messageText).create();
        System.out.println("SMS Service "+messageText);
        return null;
    }

    public String buildMessage(String customerId,String customerFirstname){
        return messageSource.getMessage("customer.onboarding.message",
                new Object[]{customerFirstname, customerId},
                Locale.ENGLISH);
    }

}
