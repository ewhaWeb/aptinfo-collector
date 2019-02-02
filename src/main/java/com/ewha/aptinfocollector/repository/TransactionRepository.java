package com.ewha.aptinfocollector.repository;

import com.ewha.aptinfocollector.VO.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
