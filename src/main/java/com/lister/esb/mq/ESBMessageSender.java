package com.lister.esb.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component("queueSender")
public class ESBMessageSender {

    @Autowired(required = true)
    @Qualifier("jmsTemplate")
    protected JmsTemplate jmsTemplate;

    public void simpleSend(final String message) {
        this.jmsTemplate.convertAndSend(message);
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}