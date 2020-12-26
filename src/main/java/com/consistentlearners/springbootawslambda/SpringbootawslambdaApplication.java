package com.consistentlearners.springbootawslambda;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.consistentlearners.springbootawslambda.domain.Order;
import com.consistentlearners.springbootawslambda.repository.OrderRepo;

@SpringBootApplication
public class SpringbootawslambdaApplication {
	
	@Autowired
    private OrderRepo orderDao;

    @Bean
    public Supplier<List<Order>> orders() {
        return () -> orderDao.buildOrders();
    }

    @Bean
    public Function<APIGatewayProxyRequestEvent, List<Order>> findOrderByName() {
        return (requestEvent) -> orderDao.buildOrders().stream().filter(order -> order.getName().equals(requestEvent.getQueryStringParameters().get("orderName"))).collect(Collectors.toList());
    }
    
	public static void main(String[] args) {
		SpringApplication.run(SpringbootawslambdaApplication.class, args);
	}

}
