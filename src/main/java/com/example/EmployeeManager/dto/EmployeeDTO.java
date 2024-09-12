package com.example.EmployeeManager.dto;

import com.example.EmployeeManager.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String fullName;
    private String email;
    private Role role;
}
