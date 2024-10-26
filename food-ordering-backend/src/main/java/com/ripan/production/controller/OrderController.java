package com.ripan.production.controller;

import com.ripan.production.model.Order;
import com.ripan.production.model.User;
import com.ripan.production.request.OrderRequest;
import com.ripan.production.response.OrderCancelResponse;
import com.ripan.production.response.PaymentResponse;
import com.ripan.production.service.OrderService;
import com.ripan.production.service.PaymentService;
import com.ripan.production.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final PaymentService paymentService;


    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request, @RequestHeader("Authorization")String jwt) throws Exception {
        /* *
         * while adding the payment service, we will redirect to the PaymentResponse, ResponseEntity<PaymentResponse>. and before that while testing
         * we will return Order as a response, ResponseEntity<Order>.
         */
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(request, user);

//        PaymentResponse response = paymentService.createPaymentLink(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getUsersOrder(@RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getUsersOrder(user.getId());

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<OrderCancelResponse> cancelOrder(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        orderService.cancelOrder(orderId);
        OrderCancelResponse response = new OrderCancelResponse("Order cancelled Successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
