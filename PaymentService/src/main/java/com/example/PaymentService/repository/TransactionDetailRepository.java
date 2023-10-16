package com.example.PaymentService.repository;

import com.example.PaymentService.domain.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail,Long> {

    TransactionDetail findByOrderId(long orderId);
}
