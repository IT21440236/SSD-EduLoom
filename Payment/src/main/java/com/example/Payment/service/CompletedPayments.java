package com.example.Payment.service;

import com.example.Payment.entity.Completed_Payments;

import java.util.List;

public interface CompletedPayments {

    void saveCompletedPayment(Completed_Payments completedPayment);

    List<Completed_Payments> getPaymentHistoryByLearnerId(String learnerId);
}
