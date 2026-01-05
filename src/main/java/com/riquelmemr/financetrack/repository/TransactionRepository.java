package com.riquelmemr.financetrack.repository;

import com.riquelmemr.financetrack.data.TransactionSummaryData;
import com.riquelmemr.financetrack.model.TransactionModel;
import com.riquelmemr.financetrack.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<TransactionModel, Long>, JpaSpecificationExecutor<TransactionModel> {

    Optional<TransactionModel> findByIdAndUser(Long id, UserModel user);

    @Query("""
    SELECT COALESCE(SUM(
        CASE
            WHEN t.type = 'INCOME' THEN t.amount
            WHEN t.type = 'EXPENSE' THEN -t.amount
        END
    ), 0)
    FROM TransactionModel t
    WHERE t.user.id = :userId""")
    BigDecimal calculateBalance(@Param("userId") Long userId);

    @Query("""
    SELECT new com.riquelmemr.financetrack.data.TransactionSummaryData(
        SUM(CASE WHEN t.type = 'INCOME' THEN t.amount END),
        SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount END),
        COUNT(t)
    )
    FROM TransactionModel t
    WHERE t.user = :user
      AND t.date BETWEEN :from AND :to
    """)
    TransactionSummaryData getSummary(
            @Param("user") UserModel user,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}
