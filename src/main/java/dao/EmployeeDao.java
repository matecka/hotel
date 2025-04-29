package dao;

import exception.EmployeeException;
import model.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EmployeeDao extends BaseDao {

    public Optional<Employee> getOptionalEmployeeById(Long employeeId) {
        return executeTransaction(session ->
                Optional.ofNullable(session.get(Employee.class, employeeId)));
    }

    public Employee getEmployeeById(Long employeeId) {
        return getOptionalEmployeeById(employeeId).orElseThrow(() ->
                new EmployeeException("Employee not found", LocalDate.now()));
    }

    public void createEmployee(Employee employee) {
        executeTransaction(session -> session.save(employee));
    }

    public Employee updateEmployee(Employee employee) {
        return executeTransaction(session -> {
            session.update(employee);
            return employee;
        });
    }

    public List<Employee> getAllEmployees() {
        return executeTransaction(session ->
                session.createQuery("from Employee", Employee.class).list()
        );
    }

    public boolean deleteEmployee(Long employeeId) {
        return executeTransaction(session -> {
            Employee employee = session.get(Employee.class, employeeId);
            if (employee != null) {
                session.delete(employee);
                return true;
            }
            return false;
        });
    }
}
