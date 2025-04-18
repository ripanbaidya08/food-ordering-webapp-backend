package com.foodsphere.service;

import com.foodsphere.model.Order;
import com.foodsphere.response.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentResponse createPaymentLink(Order order) throws StripeException;

}
