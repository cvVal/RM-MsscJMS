package guru.microservices.msscjms.listener;

import guru.microservices.msscjms.config.JmsConfig;
import guru.microservices.msscjms.model.HelloWorlMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorlMessage helloWorlMessage,
                       @Headers MessageHeaders messageHeaders, Message message) {

        System.out.println("I got a message!!");

        System.out.println(helloWorlMessage);
    }

    @JmsListener(destination = JmsConfig.MY_SEND_QUEUE)
    public void listenForHello(@Payload HelloWorlMessage helloWorlMessage,
                               @Headers MessageHeaders messageHeaders, Message message) throws JMSException {

        HelloWorlMessage payloadMsg = HelloWorlMessage.builder()
                .id(UUID.randomUUID())
                .message(" World!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
    }
}
