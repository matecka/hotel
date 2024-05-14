package service;

import dao.EmployeeDao;
import dto.request.EmployeeRequest;
import dto.request.UpdateEmployeeRequest;
import dto.response.EmployeeResponse;
import exception.EmployeeException;
import mapper.EmployeeMapper;
import model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class EmployeeService {

    private EmployeeDao employeeDao;
    private EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeDao employeeDao, EmployeeMapper employeeMapper) {
        this.employeeDao = employeeDao;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeResponse updateEmployee(UpdateEmployeeRequest updateEmployeeRequest) {
        employeeDao.getEmployeeById(updateEmployeeRequest.getId()).orElseThrow(() -> new EmployeeException("employee not found", LocalDate.now()));

        Employee employee = Employee.builder()
                .id(updateEmployeeRequest.getId())
                .name(updateEmployeeRequest.getName())
                .surname(updateEmployeeRequest.getSurname())
                .position(updateEmployeeRequest.getPosition())
                .hireDate(updateEmployeeRequest.getHireDate())
                .salary(updateEmployeeRequest.getSalary())
                .phone(updateEmployeeRequest.getPhone())
                .email(updateEmployeeRequest.getEmail()).build();

        employeeDao.updateEmployee(employee);
        return employeeMapper.from(employee);
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .name(employeeRequest.getName())
                .surname(employeeRequest.getSurname())
                .position(employeeRequest.getPosition())
                .hireDate(employeeRequest.getHireDate())
                .salary(employeeRequest.getSalary())
                .phone(employeeRequest.getPhone())
                .email(employeeRequest.getEmail()).build();

        employeeDao.createEmployee(employee);
        return employeeMapper.from(employee);
    }

    public void deleteEmployee(Long id) {
        employeeDao.deleteEmployee(id);
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeDao.getEmployeeById(id).orElseThrow(() -> new EmployeeException("employee not found", LocalDate.now()));
        return employeeMapper.from(employee);
    }

    public List<EmployeeResponse> searchEmployee(String surname) {
        List<Employee> employees = employeeDao.getAllEmployees().stream()
                .filter(n -> n.getSurname()
                        .equals(surname))
                .collect(Collectors.toList());
        List<EmployeeResponse> result = null;
        for (Employee employee : employees) {
            result.add(employeeMapper.from(employee));
        }
        return result;
    }

    public BigDecimal avgSalary() {
        OptionalDouble average = employeeDao.getAllEmployees()
                .stream()
                .mapToDouble(salary -> salary.getSalary().doubleValue())
                .average();
        return average.isPresent() ? BigDecimal.valueOf(average.getAsDouble()) : BigDecimal.ZERO;
    }
}
