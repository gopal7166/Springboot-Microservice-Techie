package com.techie.order.service;

import com.techie.order.dto.OrderRequest;
import com.techie.order.dto.OrderResponse;
import com.techie.order.dto.Payment;
import com.techie.order.dto.PaymentRequest;
import com.techie.order.entity.Order;
import com.techie.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public OrderResponse addOrder(OrderRequest orderRequest) {
        OrderResponse response = new OrderResponse();
        Order order = new Order();
        order.setId(UUID.randomUUID().variant());
        order.setAmount(orderRequest.getAmount());
        order.setCode(orderRequest.getCode());
        order.setCreatedAt(new Date().getTime());
        response.setOrder(orderRepository.save(order));

        PaymentRequest request = new PaymentRequest();
        request.setOrderId(response.getOrder().getId());
        request.setPaymentStatus("FAILED");
        Payment payment = restTemplate.postForEntity("http://PAYMENT-SERVICE/api/payments/", request, Payment.class).getBody();
        response.setPayment(payment);

        return response;
    }
}
