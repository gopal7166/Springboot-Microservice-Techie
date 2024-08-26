package com.techie.order.controller;

import com.techie.order.dto.OrderRequest;
import com.techie.order.dto.OrderResponse;
import com.techie.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/orders/")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private int attempt=1;

    @PostMapping
   // @CircuitBreaker(name = "orderService", fallbackMethod = "getDefaultOrderResponse")
   // @Retry(name = "orderService", fallbackMethod = "getDefaultOrderResponse")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderRequest orderRequest){
        System.out.println("retry method called "+attempt++ +" times "+" at "+new Date());
        return new ResponseEntity<OrderResponse>(orderService.addOrder(orderRequest), HttpStatus.CREATED);
    }


    public ResponseEntity<OrderResponse> getDefaultOrderResponse(Exception ex){
        return new ResponseEntity<OrderResponse>(new OrderResponse(), HttpStatus.CREATED);
    }
}
