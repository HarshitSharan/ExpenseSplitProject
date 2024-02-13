package org.hsharan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratedColumn;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;
@Entity @Getter @Setter
public class GroupEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String groupId;
    private Date createdAt;
    @ManyToOne
    private UserEntity createdBy;

    @ManyToMany(mappedBy = "groupList")
    private List<UserEntity> users;
    private String name;
    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    private List<ExpenseEntity> expenseList;


}
