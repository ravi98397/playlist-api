package com.api.artist;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;


import com.api.artist.mq.Receiver;
import com.zaxxer.hikari.util.ClockSource.Factory;

//Routing key structure, that needs to be followed   from.to.          example artist.song.update

@SpringBootApplication
@EnableEurekaClient
public class ArtistApiApplication {
	
	static final String topicExchangeName = "REL_EXCHANGE";
	
	static final String queueName = "ARTIST_QUEUE";
	
	@Bean
	Queue queue() {
	  return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		System.out.println("new exchange");
	  return new TopicExchange(topicExchangeName);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		System.out.println("new binding");
	   //return BindingBuilder.bind(queue).to(exchange).with("artist.*.*");
		return BindingBuilder.bind(queue).to(exchange).with("*.artist.*");
	}
	
	  @Bean
	  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
	      MessageListenerAdapter listenerAdapter) {
		  System.out.println("new msg listener");
	    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	    container.setConnectionFactory(connectionFactory);
	    container.setQueueNames(queueName);	   
	    container.setMessageListener(listenerAdapter);
	    return container;
	  }

	  @Bean
	  MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	  }

	public static void main(String[] args) {
		SpringApplication.run(ArtistApiApplication.class, args);
	}

}
