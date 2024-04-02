package com.example.ekart_backend.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Data
@CrossOrigin
public class PaymentController {


    @PostMapping("/create-order")
    public String createOrder(@RequestBody Map<String, Object> orderDetails) throws Exception{

        UUID transactionUUID = UUID.randomUUID();
        String transcationId = transactionUUID.toString().substring(0,10);
        RazorpayClient client = new RazorpayClient("rzp_test_5MEEGqW9b9cCYZ", "RnlHi3qqcFtJyZoKjMUZA0iE");

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", Double.parseDouble(orderDetails.get("amount").toString())*100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "TXN"+transcationId);

        Order order = client.orders.create(orderRequest);
        System.out.println(order);
        return order.toString();
    }

}
