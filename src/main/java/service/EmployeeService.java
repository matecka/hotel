package service;

import dao.EmployeeDao;
import dto.request.EmployeeRequest;
import dto.request.UpdateEmployeeRequest;
import exception.EmployeeException;
import model.Employee;

import java.time.LocalDate;

public class EmployeeService {

    private EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void updateEmployee(UpdateEmployeeRequest updateEmployeeRequest) {
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
    }

    public void createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = Employee.builder()
                .name(employeeRequest.getName())
                .surname(employeeRequest.getSurname())
                .position(employeeRequest.getPosition())
                .hireDate(employeeRequest.getHireDate())
                .salary(employeeRequest.getSalary())
                .phone(employeeRequest.getPhone())
                .email(employeeRequest.getEmail()).build();

        employeeDao.createEmployee(employee);
    }

    public void deleteEmployee(Long id) {
        employeeDao.deleteEmployee(id);
    }

    public Employee getEmployeeById(Long id) {
        return employeeDao.getEmployeeById(id).orElseThrow(() -> new EmployeeException("employee not found", LocalDate.now()));
    }
}
