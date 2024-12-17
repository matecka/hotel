package dao;
import model.Employee;
import java.util.List;
import java.util.Optional;

public class EmployeeDao extends BaseDao {


    public Optional<Employee> getEmployeeById(Long employeeId) {
        return executeTransaction(session -> Optional.ofNullable(session.get(Employee.class, employeeId)));
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
                return true;}
            return false;
        });
    }
}
