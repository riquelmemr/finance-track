package com.riquelmemr.financetrack.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tb_role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
