package dao;

import exception.CustomerException;
import model.Customer;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CustomerDao extends BaseDao {


    public Optional<Customer> getOptionalCustomerById(Long customerId) {
        return executeTransaction(session -> Optional.ofNullable(session.get(Customer.class, customerId)));
    }

    public Customer getCustomerById(Long customerId) {
        return getOptionalCustomerById(customerId).orElseThrow(() ->
                new CustomerException("Customer not found", LocalDate.now()));
    }

    public void createCustomer(Customer customer) {
        executeTransaction(session -> session.save(customer));
    }

    public Customer updateCustomer(Customer customer) {
        return executeTransaction(session -> {
            session.update(customer);
            return customer;
        });
    }

    public List<Customer> getAllCustomers() {
        return executeTransaction(session ->
                session.createQuery("from Customer", Customer.class).list()
        );
    }

    public boolean deleteCustomer(Long customerId) {
        return executeTransaction(session -> {
            Customer customer = session.get(Customer.class, customerId);
            if (customer != null) {
                session.delete(customer);
                return true;
            }
            return false;
        });
    }

    public List<Customer> findByCustomerName(String name) {
        return executeTransaction(session -> {
                    String hql = "FROM Customer WHERE name = :name";

                    return session.createQuery(hql, Customer.class)
                            .setParameter("name", name)
                            .getResultList();
                }
        );
    }
}
