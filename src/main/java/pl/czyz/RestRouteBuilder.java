package pl.czyz;

import pl.czyz.processor.CreateTimeObjectProcessor;
import pl.czyz.processor.HelloPocessor;
import pl.czyz.processor.TimeProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        /**
         * Use 'restlet', which is a very simple component for providing REST
         * services. Ensure that camel-restlet or camel-restlet-starter is
         * included as a Maven dependency first.
         */
        restConfiguration()
                .component("restlet")
                .host("0.0.0.0")
                .port("8085")
                .bindingMode(RestBindingMode.json);

        /**
         * Configure the REST API (POST, GET, etc.)
         */

        rest("/hello?name={name}")
                .get()
                .to("direct:getHello");

        from("direct:getHello").routeId("hello")
                .process(new CreateTimeObjectProcessor())
                .multicast()
                    .parallelProcessing()
                        .process(new HelloPocessor())
                        .process(new TimeProcessor())
                .endRest();
    }
}
