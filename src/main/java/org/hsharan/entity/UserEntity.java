package org.hsharan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Entity
public class UserEntity {
    @Id
    private String id;
    private String phoneNum;
    private String name;
    private String email;
    @OneToMany(mappedBy = "paidBy")
    private List<ExpenseEntity> paidExpensesList;

    @ManyToMany
    private List<GroupEntity> groupList;


    @OneToMany(mappedBy = "user")
    private List<SplitEntity> pendingPayList;
}
