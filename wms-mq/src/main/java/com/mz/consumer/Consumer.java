package com.mz.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author tongzhou
 * @date 2018-04-20 10:25
 **/
public class Consumer {
    @Autowired
    JmsTemplate jmsTemplate;


}
