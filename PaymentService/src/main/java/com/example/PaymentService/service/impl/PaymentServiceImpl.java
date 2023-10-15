package com.example.PaymentService.service.impl;

import com.example.PaymentService.domain.TransactionDetail;
import com.example.PaymentService.model.PaymentRequest;
import com.example.PaymentService.repository.TransactionDetailRepository;
import com.example.PaymentService.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording payment details: {}",paymentRequest);

        TransactionDetail transactionDetail = TransactionDetail.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetailRepository.save(transactionDetail);
        log.info("Transaction completed with id : {}",transactionDetail.getOrderId());

        return transactionDetail.getId();
    }
}



















