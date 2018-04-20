package com.cleverbuilder.cameldemos.restdsl.processor;

import com.cleverbuilder.cameldemos.restdsl.model.Time;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.time.LocalDateTime;

public class TimeProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Time timeMessage = exchange.getIn().getBody(Time.class);
        timeMessage.setTime(LocalDateTime.now());
        exchange.getOut().setBody(timeMessage);
    }
}
