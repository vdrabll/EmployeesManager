package com.example.EmployeeManager.repository;

import com.example.EmployeeManager.entity.Employee;
import com.example.EmployeeManager.entity.Leave;
import com.example.EmployeeManager.entity.Role;
import com.example.EmployeeManager.enums.AuthRole;
import com.example.EmployeeManager.enums.LeaveStatus;
import com.example.EmployeeManager.enums.LeaveType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("test")
class LeaveRepositoryTest {

    @Autowired
    private LeaveRepository leaveRepository;
    private Leave vacationLeave;
    private Leave sickLeave;
    private Employee employee;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = Pageable.unpaged();
        employee = Employee.builder().fullName( "Иванов Петр Петрович").email("example@sber.ru").build();
        vacationLeave = Leave.builder()
                .status(LeaveStatus.AGREED)
                .type(LeaveType.VACATION)
                .employee(employee)
                .startDate(LocalDate.of(2022, 2,1))
                .endDate(LocalDate.of(2022, 2,1))
                .build();

        sickLeave = Leave.builder()
                .status(LeaveStatus.AGREED)
                .type(LeaveType.SICK)
                .employee(employee)
                .startDate(LocalDate.of(2022, 7,1))
                .endDate(LocalDate.of(2022, 7,15))
                .build();
        leaveRepository.save(vacationLeave);
        leaveRepository.save(sickLeave);
    }

    @AfterEach
    void tearDown() {
        leaveRepository.deleteById(vacationLeave.getId());
    }

    @Test
    void findByEmployeeTest() {
        List testLeaves = List.of(vacationLeave, sickLeave);
        List<Leave> leaves = leaveRepository.findByEmployee(employee, pageable).toList();;
        assertNotNull(leaves);
        assertThat(leaves).isEqualTo(testLeaves);
    }

    @Test
    void findAllByEndDateBetweenTest() {
        List<Leave> testLeaves = new ArrayList<Leave>();
        testLeaves.add(vacationLeave);
        List<Leave> leaves = leaveRepository.findAllByEndDateBetween(LocalDate.of(2022,1,1), LocalDate.of(2022,6,29), pageable).toList();
        assertNotNull(leaves);
        assertThat(leaves).isEqualTo(testLeaves);
    }

}