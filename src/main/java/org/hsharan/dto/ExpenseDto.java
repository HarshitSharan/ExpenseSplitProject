package org.hsharan.dto;

import java.util.Date;
import java.util.List;

public class ExpenseDto {
    private String expenseId;
    private String paidBy;
    private String description;
    private Date createdAt;
    private Double amount;
    private List<SplitDto> splitList;

    private String groupId;


}
