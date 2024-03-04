package dao;

import config.HibernateUtil;
import exception.CustomerException;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.Optional;

public class CustomerDao {

    public Optional<Customer> getOptionalCustomerById(Long customerId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Customer customer = session.get(Customer.class, customerId);
            transaction.commit();
            return Optional.ofNullable(customer);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Customer getCustomerById(Long customerId) {
        return getOptionalCustomerById(customerId).orElseThrow(() -> new CustomerException("guest not found", LocalDate.now()));
    }

    public void createCustomer(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public boolean deleteCustomer(Long guestId) {
        Transaction transaction = null;
        boolean isDeleted = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, guestId);
            if (customer != null) {
                session.delete(customer);
                isDeleted = true;
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return isDeleted;
    }
}
