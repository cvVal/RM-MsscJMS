package guru.microservices.msscjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.microservices.msscjms.config.JmsConfig;
import guru.microservices.msscjms.model.HelloWorlMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {

        HelloWorlMessage message = HelloWorlMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {

        HelloWorlMessage message = HelloWorlMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        Message receiveMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_QUEUE, session -> {

            Message helloMessage;

            try {

                helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                helloMessage.setStringProperty("_type", "guru.microservices.msscjms.model.HelloWorlMessage");

                return helloMessage;

            } catch (JsonProcessingException e) {
                throw new JMSException("boom");
            }
        });

        System.out.println(receiveMessage.getBody(String.class));
    }
}
