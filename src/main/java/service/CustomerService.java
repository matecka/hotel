package service;

import dao.CustomerDao;
import dto.request.CustomerRequest;
import dto.request.UpdateCustomerRequest;
import dto.response.CustomerResponse;
import mapper.CustomerMapper;
import model.Customer;

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
}
