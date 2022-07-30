package org.barclays.repository;

import org.barclays.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
