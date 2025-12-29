package com.riquelmemr.financetrack.model;

import com.riquelmemr.financetrack.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "tb_transaction")
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class TransactionModel extends ItemModel {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryModel category;

    private String description;

    private LocalDateTime date;
}
