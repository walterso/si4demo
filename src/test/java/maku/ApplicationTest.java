package maku;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@ContextConfiguration()
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationTest {
    @Autowired
    Application.TemperatureConverter converter;

    @Autowired @Qualifier("flow.input")
    MessageChannel in;
    @Autowired @Qualifier("flow.output")
    MessageChannel testChannel;

    @Test
    public void freezingPoint() {
        new MessagingTemplate(in).convertAndSend(32.0f);
    }

}