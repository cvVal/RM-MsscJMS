package guru.microservices.msscjms.listener;

import guru.microservices.msscjms.config.JmsConfig;
import guru.microservices.msscjms.model.HelloWorlMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class HelloMessageListener {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorlMessage helloWorlMessage,
                       @Headers MessageHeaders messageHeaders, Message message) {

        System.out.println("I got a message!!");

        System.out.println(helloWorlMessage);
    }
}
