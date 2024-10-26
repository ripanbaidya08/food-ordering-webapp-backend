package com.ripan.production.service;

import com.ripan.production.model.Order;
import com.ripan.production.response.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentResponse createPaymentLink(Order order) throws StripeException;

}
