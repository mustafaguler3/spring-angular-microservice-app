package com.example.PaymentService.repository;

import com.example.PaymentService.domain.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail,Long> {
}
