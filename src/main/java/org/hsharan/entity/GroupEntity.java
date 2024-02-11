package org.hsharan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Entity @Getter @Setter
public class GroupEntity {
    @Id
    private String groupId;
    private Date createdAt;
    @ManyToMany(mappedBy = "groupList",cascade = CascadeType.ALL)
    private List<UserEntity> users;
    private String name;
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    private List<ExpenseEntity> expenseList;
}
