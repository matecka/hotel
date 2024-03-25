package mapper;

import dto.response.CustomerResponse;
import model.Customer;

public class CustomerMapper {
    public CustomerResponse from(Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }
}
