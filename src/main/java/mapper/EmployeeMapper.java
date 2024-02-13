package mapper;


import dto.response.EmployeeResponse;
import model.Employee;

public class EmployeeMapper {
    public EmployeeResponse from(Employee employee){
        return EmployeeResponse.builder()
                .name(employee.getName())
                .surname(employee.getSurname())
                .position(employee.getPosition())
                .salary(employee.getSalary())
                .hireDate(employee.getHireDate())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .build();
    }
}
