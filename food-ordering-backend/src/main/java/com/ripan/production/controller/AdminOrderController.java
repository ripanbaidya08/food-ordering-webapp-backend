package com.ripan.production.controller;

import com.ripan.production.model.Order;
import com.ripan.production.model.User;
import com.ripan.production.service.OrderService;
import com.ripan.production.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getRestaurantsOrder(@PathVariable Long id,@RequestParam(required = false) String orderStatus,@RequestHeader("Authorization")String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantsOrder(id, orderStatus);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,@PathVariable String orderStatus, @RequestHeader("Authorization")String jwt) throws Exception {
        /* *
         * Order Status: OUT_FOR_DELIVERY, DELIVERED, COMPLETED, PENDING
         */
        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrderStatus(orderId, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
