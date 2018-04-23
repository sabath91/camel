package pl.czyz;

import pl.czyz.model.Time;
import pl.czyz.processor.CreateTimeObjectProcessor;
import pl.czyz.processor.HelloPocessor;
import pl.czyz.processor.TimeProcessor;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.Is.is;

public class RestRouteBuilderTest extends CamelTestSupport {

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testMockEndpoints() throws Exception {
        RouteDefinition route = context.getRouteDefinition("hello");
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                mockEndpoints();
            }
        });

        // must start Camel after we are done using advice-with
        context.start();


        MockEndpoint mockEndpoint = getMockEndpoint("mock:getMessageAndTime");
        mockEndpoint.expectedMessageCount(1);
        String before = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        template.requestBody("rest:get:hello?name=World", "");
        assertMockEndpointsSatisfied();
        Time output = mockEndpoint.getReceivedExchanges().get(0).getIn().getBody(Time.class);
        assertNotNull(output);
        assertEquals("Hello World <---> from MessageProcessor", output.getMessage());
        assertThat(output.getTime(), anyOf(is(before), is(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))));


    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                restConfiguration()
                        .component("restlet")
                        .host("localhost")
                        .port("8082")
                        .bindingMode(RestBindingMode.json);

                rest("/hello?name={name}")
                        .get()
                        .to("direct:getHello");

                from("direct:getHello").routeId("hello")
                        .process(new CreateTimeObjectProcessor())
                        .multicast()
                        .parallelProcessing()
                        .process(new HelloPocessor())
                        .process(new TimeProcessor())
                        .to("mock:getMessageAndTime");
            }
        };
    }
}