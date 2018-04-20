package com.cleverbuilder.cameldemos.restdsl;

import com.cleverbuilder.cameldemos.restdsl.processor.CreateTimeObjectProcessor;
import com.cleverbuilder.cameldemos.restdsl.processor.HelloPocessor;
import com.cleverbuilder.cameldemos.restdsl.processor.TimeProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

@Component
public class RestDslRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {



        /**
         * Use 'restlet', which is a very simple component for providing REST
         * services. Ensure that camel-restlet or camel-restlet-starter is
         * included as a Maven dependency first.
         */
        restConfiguration()
                .component("restlet")
                .host("localhost")
                .port("8080")
                .bindingMode(RestBindingMode.json);

        /**
         * Configure the REST API (POST, GET, etc.)
         */

        rest("/hello?name={name}")
                .get()
                    .to("direct:getHello");

        from("direct:getHello")
                .process(new CreateTimeObjectProcessor())
                    .multicast()
                    .parallelProcessing()
                        .process(new HelloPocessor())
                        .process(new TimeProcessor())
                        .endRest();

    }
}
