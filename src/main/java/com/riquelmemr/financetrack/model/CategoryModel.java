package com.riquelmemr.financetrack.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "tb_category")
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel extends ItemModel {

    @Column(nullable = false)
    private String code;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;
}
