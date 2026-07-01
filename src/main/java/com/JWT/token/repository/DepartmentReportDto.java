package com.JWT.token.repository;

import com.JWT.token.entity.Department;
import org.springframework.stereotype.Repository;


public interface DepartmentReportDto {
    String getDepartmentName();
    Long getEmployeeCount();
    void save(Department department);
}
