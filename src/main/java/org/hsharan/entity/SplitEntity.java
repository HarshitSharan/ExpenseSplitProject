package org.hsharan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class SplitEntity {
    @Id
    String splitId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private ExpenseEntity expense;
}
