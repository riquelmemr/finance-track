package com.riquelmemr.financetrack.repository;

import com.riquelmemr.financetrack.model.TransactionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    Page<TransactionModel> findAllByUserId(Long userId, Pageable pageable);

    @Query("""
    SELECT COALESCE(SUM(
        CASE
            WHEN t.type = 'INCOME' THEN t.amount
            WHEN t.type = 'OUTCOME' THEN -t.amount
        END
    ), 0)
    FROM TransactionModel t
    WHERE t.user.id = :userId""")
    BigDecimal calculateBalance(@Param("userId") Long userId);
}
