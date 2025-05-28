package com.elvra.app.sms.utility.service;

import com.elvra.app.sms.utility.util.SMSBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import static com.elvra.app.sms.utility.constants.SMSServiceConstants.CUSTOMER_ONBOARDING_TOPIC;
@Service
public class SMSService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SMSService.class);
    @Autowired
    private SMSBuilder smsBuilder;

    @KafkaListener(topics = CUSTOMER_ONBOARDING_TOPIC,groupId = "customer-onboarding-v1")
    public Message.Status sendSMS(String details) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(details);
        JsonNode contactInformation = root.get("contactInformation");
        String customerId = root.get("customerId").asText();
        LOGGER.info("Sending SMS to the customer with customerId {}",customerId);
        String customerFirstname = root.get("customerFirstname").asText();
        StringBuilder toNumber = new StringBuilder();
        toNumber.append(contactInformation.get("countryCode").asText());
        toNumber.append(contactInformation.get("phoneNumber").asText());
        String messageText = smsBuilder.buildMessage(customerId,customerFirstname);
        Message message = smsBuilder.send(toNumber, messageText);
        LOGGER.info("Sent SMS to the customer with customerId {} Successfully",customerId);
        return message.getStatus();
    }
}
