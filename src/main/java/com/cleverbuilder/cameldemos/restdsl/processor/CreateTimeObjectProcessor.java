package com.cleverbuilder.cameldemos.restdsl.processor;

import com.cleverbuilder.cameldemos.restdsl.model.Time;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CreateTimeObjectProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Time time = new Time();
        exchange.getIn().setBody(time);
    }
}
