package com.JWT.token.controller;

import com.JWT.token.entity.Department;
import com.JWT.token.entity.Employee;
import com.JWT.token.entity.Laptop;
import com.JWT.token.repository.DepartmentReportDto;
import com.JWT.token.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentReportDto departmentRepository;

    public EmployeeController(EmployeeRepository employeeRepository, DepartmentReportDto departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @PostMapping("/setup")
    public ResponseEntity<String> setupData() {
        Department hr = new Department();
        hr.setName("Human Resources");
        departmentRepository.save(hr);

        Employee emp = new Employee();
        emp.setName("John Doe");
        emp.setEmail("john.doe@company.com");
        emp.setDepartment(hr);

        Laptop corporateMac = new Laptop();
        corporateMac.setSerialNumber("CORP-99988");

        // Linking bidirectional relationship
        emp.setLaptop(corporateMac);

        // Cascade automatically inserts corporateMac into the laptop table
        employeeRepository.save(emp);

        return ResponseEntity.ok("Employee and Laptop saved successfully via Cascade!");
    }

    // 2. Demonstrating Dirty Checking & Entity States
    @PutMapping("/{id}/rename")
    public ResponseEntity<String> updateEmployeeName(@PathVariable Long id, @RequestParam String newName) {
        // We look up the employee. This entity is now in the "MANAGED" state.
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Modifying the field inside a service/controller route.
        // Because of Spring's OSIV pattern or @Transactional, Hibernate monitors this object.
        employee.setName(newName);

        // CRITICAL NOTE: Notice we do NOT call employeeRepository.save(employee);
        // Look at your console logs when hitting this endpoint—Hibernate updates it automatically!
        return ResponseEntity.ok("Name updated via automated Dirty Checking mechanism!");
    }

    // 3. Custom JPQL Query Endpoint
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String keyword) {
        List<Employee> results = employeeRepository.searchByNameKeyword(keyword);
        return ResponseEntity.ok(results);
    }

    // 4. Performance Optimized Fetch (JOIN FETCH)
    @GetMapping("/optimized-laptops")
    public ResponseEntity<String> getOptimizedData() {
        // Hits the database exactly ONE time to fetch employees AND their laptops
        List<Employee> employees = employeeRepository.findAllEmployeesWithLaptopsOptimized();

        return ResponseEntity.ok("Fetched " + employees.size() + " records in 1 database round-trip.");
    }

    // 5. Native SQL Query with DTO Projection Result
    @GetMapping("/report")
    public ResponseEntity<List<DepartmentReportDto>> getReport() {
        List<DepartmentReportDto> report = employeeRepository.getDepartmentEmployeeReport();
        return ResponseEntity.ok(report);
    }
}
