package com.example.Payment.controller;

import com.example.Payment.repository.CompletedPaymentsRepository;
import com.example.Payment.service.PaypalService;
import com.example.Payment.entity.Completed_Payments;
import com.example.Payment.service.CompletedPaymentsImpl;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Controller
//@RequestMapping("/payment")
@CrossOrigin("*")
@ComponentScan
public class PaypalController {

    @Autowired
    PaypalService service;

    @Autowired
    CompletedPaymentsImpl completedPaymentsImpl;

    @Autowired
    CompletedPaymentsRepository completedPaymentsRepository;

    @Autowired
    RestTemplate restTemplate;



    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @PostMapping("/pay")
//    public String payment(@ModelAttribute("order") Completed_Payments order) {
//        try {
//            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
//                    order.getIntent(), order.getDescription(), "http://localhost:9090/" + CANCEL_URL,
//                    "http://localhost:9090/" + SUCCESS_URL);
//            for(Links link:payment.getLinks()) {
//                if(link.getRel().equals("approval_url")) {
//                    return "redirect:"+link.getHref();
//                }
//            }
//
//        } catch (PayPalRESTException e) {
//
//            e.printStackTrace();
//        }
//        return "redirect:/";
//    }

    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") Completed_Payments order) {
        try {

            // Set the current date
            order.setDate(new Date());
            // Save the payment details to the completed table
            completedPaymentsImpl.saveCompletedPayment(order);

            // Proceed with payment creation
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:9090/" + CANCEL_URL,
                    "http://localhost:9090/" + SUCCESS_URL);

            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }


    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                // Create a new Completed_Payments object
                Completed_Payments completedPayment = new Completed_Payments();
                completedPayment.setId(paymentId); // Set the payment ID
//                completedPayment.setDate(new Date()); // Set the current date
                // Save the completed payment details
                completedPaymentsRepository.save(completedPayment);
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}
