package dao;

import config.HibernateUtil;
import exception.CustomerException;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
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
        return getOptionalCustomerById(customerId).orElseThrow(() ->
                new CustomerException("Customer not found", LocalDate.now()));
    }

    public List<Customer> getAllCustomers() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Customer> customers = session.createQuery("from customer", Customer.class).list();
            transaction.commit();
            return customers;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public List<Customer> findByCustomerName(String name) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM Customer WHERE name = :name";
            List<Customer> customers = session.createQuery(hql, Customer.class)
                    .setParameter("name", name)
                    .getResultList();
            transaction.commit();
            return customers;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

//    public List<Customer> findByCustomerName(String name) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Transaction transaction = session.beginTransaction();
//            String hql = "FROM Customer WHERE name = :name";
//            List<Customer> customers = session.createQuery(hql, Customer.class)
//                    .setParameter("name", name)
//                    .getResultList();
//            transaction.commit();
//            return customers;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Collections.emptyList();
//        }
//    }

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

    public boolean deleteCustomer(Long customerId) {
        Transaction transaction = null;
        boolean isDeleted = false;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Customer customer = session.get(Customer.class, customerId);
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
