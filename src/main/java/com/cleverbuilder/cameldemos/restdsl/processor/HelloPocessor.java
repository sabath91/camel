package com.cleverbuilder.cameldemos.restdsl.processor;

import com.cleverbuilder.cameldemos.restdsl.model.Time;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;


public class HelloPocessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Time timeMessage = exchange.getIn().getBody(Time.class);
        String name = (String) exchange.getIn().getHeaders().get("name");
        timeMessage.setMessage("Hello "+ name + " <---> from MessageProcessor");
        exchange.getOut().setBody(timeMessage);
    }
}
