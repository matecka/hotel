package service;

import dao.CustomerDao;
import dto.request.CustomerRequest;
import dto.request.UpdateCustomerRequest;
import dto.response.CustomerResponse;
import mapper.CustomerMapper;
import model.Customer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerService {

    private CustomerDao customerDao;
    private CustomerMapper customerMapper;

    public CustomerService(CustomerDao customerDao, CustomerMapper customerMapper) {
        this.customerDao = customerDao;
        this.customerMapper = customerMapper;
    }

    public CustomerResponse updateCustomer(UpdateCustomerRequest updateCustomerRequest) {
        customerDao.getCustomerById(updateCustomerRequest.getId());

        Customer customer = Customer.builder()
                .id(updateCustomerRequest.getId())
                .name(updateCustomerRequest.getName())
                .surname(updateCustomerRequest.getSurname())
                .phone(updateCustomerRequest.getPhone())
                .email(updateCustomerRequest.getEmail())
                .address(updateCustomerRequest.getAddress()).build();

        customerDao.updateCustomer(customer);
        return customerMapper.from(customer);
    }

    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .surname(customerRequest.getSurname())
                .phone(customerRequest.getPhone())
                .email(customerRequest.getEmail())
                .address(customerRequest.getAddress()).build();

        customerDao.createCustomer(customer);
        return customerMapper.from(customer);
    }

    public void deleteCustomer(Long id) {
        customerDao.deleteCustomer(id);
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerDao.getCustomerById(id);
        return customerMapper.from(customer);
    }

    public List<CustomerResponse> searchCustomer(String name, String surname) {
        List<Customer> allCustomers = customerDao.getAllCustomers();
        List<CustomerResponse> collected = allCustomers.stream()
                .filter(c -> c.getName().equals(name) && c.getSurname().equals(surname))
                .map(c -> customerMapper.from(c)).collect(Collectors.toList());
        return collected;
    }

    public Map<Customer, Long> getAmountReservation() {
        return customerDao.getAllCustomers().stream()
                .collect(Collectors.groupingBy(
                        customer -> customer,
                        Collectors.summingLong(customer -> customer.getReservations().size())
                ));


    }

}
