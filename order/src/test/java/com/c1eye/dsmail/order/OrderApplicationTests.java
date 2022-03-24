package com.c1eye.dsmail.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderApplicationTests {

	@Autowired
	AmqpAdmin amqpAdmin;

	@Test
	public void createExchange(){
		DirectExchange directExchange = new DirectExchange("hello-java-exchange", true, false, null);
		amqpAdmin.declareExchange(directExchange);
	}

	@Test
	public void createQueue() {
		Queue queue = new Queue("hello-java-queue", true, false, false);
		amqpAdmin.declareQueue(queue);
	}

	@Test
	public void createBind() {
		Binding binding = new Binding("hello-java-queue", Binding.DestinationType.QUEUE, "hello-java-exchange", "hello" +
				".java", null);
		amqpAdmin.declareBinding(binding);
	}

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Test
	public void sendMsg() {
		Date date = new Date();
		rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java", date);
	}
}
