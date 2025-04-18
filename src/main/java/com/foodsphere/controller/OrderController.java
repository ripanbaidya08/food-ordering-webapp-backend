package com.foodsphere.controller;

import com.foodsphere.model.Order;
import com.foodsphere.model.Users;
import com.foodsphere.request.OrderRequest;
import com.foodsphere.response.OrderCancelResponse;
import com.foodsphere.service.OrderService;
import com.foodsphere.service.PaymentService;
import com.foodsphere.service.UserService;
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
        Users user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(request, user);

//        PaymentResponse response = paymentService.createPaymentLink(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getUsersOrder(@RequestHeader("Authorization")String jwt) throws Exception {
        Users user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getUsersOrder(user.getId());

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<OrderCancelResponse> cancelOrder(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws Exception {
        Users user = userService.findUserByJwtToken(jwt);
        orderService.cancelOrder(orderId);
        OrderCancelResponse response = new OrderCancelResponse("Order cancelled Successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
