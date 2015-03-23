package maku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;

@SpringBootApplication
@EnableAutoConfiguration
@IntegrationComponentScan
public class Application {

    @MessagingGateway(defaultRequestChannel = "fahrenheitToCelsius")
    public static interface TemperatureConverter {

        @Gateway(requestChannel = "convert.input")
        float fahrenheitToCelsius(float fahrenheit);

    }

    @Bean
    public IntegrationFlow convert() {
        return IntegrationFlows.from("input")
                .transform("@temperateTransformer.transform(payload)")
                .get();
    }

    @Bean
    public Object temperateTransformer() {
        return new Object() {
            public float transform(float f) {
                return (f-32) * 9 / 5;
            }
        };
    }

    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlows.from("input")
                .handle( new LoggingHandler("WARN"))
                .get();
    }
}