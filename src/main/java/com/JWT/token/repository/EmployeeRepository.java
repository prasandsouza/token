package com.JWT.token.repository;

import com.JWT.token.entity.Employee;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    // 1. Custom JPQL Query
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:keyword%")
    List<Employee> searchByNameKeyword(@Param("keyword") String keyword);

    // 2. CRITICAL CONCEPT: Resolving N+1 select issue using JOIN FETCH
    // Without 'JOIN FETCH', if you loop over 100 employees and print their laptops,
    // Hibernate will issue 101 separate queries to the DB. This fetches them all in 1 query!
    @Query("SELECT e FROM Employee e JOIN FETCH e.laptop")
    List<Employee> findAllEmployeesWithLaptopsOptimized();

    // 3. Custom Native SQL Query with Projection
    @Query(value = "SELECT d.name as departmentName, COUNT(e.id) as employeeCount " +
            "FROM department d " +
            "LEFT JOIN employees e ON d.id = e.department_id " +
            "GROUP BY d.name", nativeQuery = true)
    List<DepartmentReportDto> getDepartmentEmployeeReport();
}
