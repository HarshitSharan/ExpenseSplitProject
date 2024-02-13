package org.hsharan.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @ToString
public class GroupDto {
    private List<String> usersId;
    private String name;
}
