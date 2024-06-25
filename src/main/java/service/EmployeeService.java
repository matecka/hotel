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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class EmployeeService {

    private EmployeeDao employeeDao;
    private EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeDao employeeDao, EmployeeMapper employeeMapper) {
        this.employeeDao = employeeDao;
        this.employeeMapper = employeeMapper;
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

    public void deleteEmployee(Long id) {
        employeeDao.deleteEmployee(id);
    }


    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeDao.getEmployeeById(id).orElseThrow(() -> new EmployeeException("employee not found", LocalDate.now()));
        return employeeMapper.from(employee);
    }

//    public List<EmployeeResponse> searchEmployee(String surname) {
//        List<Employee> employees = employeeDao.getAllEmployees().stream()
//                .filter(n -> n.getSurname()
//                        .equals(surname))
//                .collect(Collectors.toList());
//        List<EmployeeResponse> result = null;
//        for (Employee employee : employees) {
//            result.add(employeeMapper.from(employee));
//        }
//        return result;
//    }


    public List<EmployeeResponse> searchEmployee(String surname) {
        return employeeDao.getAllEmployees().stream()
                .filter(employee -> surname != null && surname.equals(employee.getSurname()))
                .map(employeeMapper::from)
                .collect(Collectors.toList());
    }


    public BigDecimal avgSalary() {
        OptionalDouble average = employeeDao.getAllEmployees()
                .stream()
                .mapToDouble(salary -> salary.getSalary().doubleValue())
                .average();
        return average.isPresent() ? BigDecimal.valueOf(average.getAsDouble()) : BigDecimal.ZERO;
    }

    public BigDecimal findMaxSalary(String nameHotel) {
        OptionalDouble max = employeeDao.getAllEmployees()
                .stream()
                .filter(e -> e.getHotel().getName().equals(nameHotel))
                .mapToDouble(salary -> salary.getSalary().doubleValue())
                .max();

        return max.isPresent() ? BigDecimal.valueOf(max.getAsDouble()) : BigDecimal.ZERO;
    }

    public BigDecimal findMaxSalary2(String nameHotel) {
        Employee employee = employeeDao.getAllEmployees()
                .stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new EmployeeException("lack", LocalDate.now()));
        return new BigDecimal(employee.getSalary().doubleValue());

    }


    public EmployeeResponse findEmployeeWithMaxSalary(String nameHotel) {
        Employee employee1 = employeeDao.getAllEmployees()
                .stream()
                .filter(employee -> employee.getHotel().getName().equals(nameHotel))
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(() -> new EmployeeException("lack of employees", LocalDate.now()));
        return employeeMapper.from(employee1);
    }

    public List<EmployeeResponse> sortByEmployeeName() {
        return employeeDao.getAllEmployees()
                .stream()
                .sorted(Comparator.comparing(Employee::getName))
                .map(employeeMapper::from)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponse> sortByEmployeeNameReverse() {
        return employeeDao.getAllEmployees()
                .stream()
                .sorted(Comparator.comparing(Employee::getName).reversed())
                .map(employeeMapper::from)
                .collect(Collectors.toList());
    }


    public List<EmployeeResponse> findEmployeesByDates(LocalDate from, LocalDate to) {
        return employeeDao.getAllEmployees()
                .stream()
                .filter(e -> e.getHireDate().isAfter(from) && e.getHireDate().isBefore(to))
                .map(employeeMapper::from)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponse> findEmployeesByNameAndSurname(String name, String surname) {
        return employeeDao.getAllEmployees()
                .stream()
                .filter(n -> n.getName().equals(name) && n.getSurname().equals(surname))
                .map(employeeMapper::from)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponse> findEmployeesBySalaryRange(BigDecimal max, BigDecimal min) {
        return employeeDao.getAllEmployees()
                .stream()
                .filter(s -> s.getSalary().compareTo(min) >= 1 && s.getSalary().compareTo(max) <= 1)
                .map(employeeMapper::from)
                .collect(Collectors.toList());
    }

    public Map<Employee, List<String>> findEmployeesByPosition(){
        return employeeDao.getAllEmployees()
                .stream().collect(Collectors
                        .groupingBy(employee -> employee,
                                Collectors.mapping(Employee::getPosition, Collectors.toList())));

    }
    public Map<String, List<String>> findEmployeesByEmail(){
     return    employeeDao.getAllEmployees()
                .stream().collect(Collectors
                        .groupingBy(employee -> employee.getName(),
                                Collectors.mapping(Employee::getEmail, Collectors.toList())));
    }
}
