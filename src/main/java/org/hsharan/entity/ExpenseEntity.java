package org.hsharan.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Entity @Getter @Setter
public class ExpenseEntity {
    @Id
    private String expenseId;
    @ManyToOne
    @JoinColumn(name = "paidBy")
    private UserEntity paidBy;
    private String description;
    private Double amount;
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "group_Id")
    private GroupEntity group;
    @OneToMany(mappedBy = "expense",cascade = CascadeType.ALL)
    private List<SplitEntity> splitList;
}
