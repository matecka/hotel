package dao;

import config.HibernateUtil;
import exception.MessageException;
import model.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MessageDao extends BaseDao {

    public Optional<Message> getOptionalMessageById(Long messageId) {
        return executeTransaction(session ->
                Optional.ofNullable(session.get(Message.class, messageId)));
    }

    public Message getMessageById(Long messageId) {
        return getOptionalMessageById(messageId).orElseThrow(() ->
                new MessageException("Message not found", LocalDate.now()));
    }


    public List<Message> getAllMessages() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Message> messages = session.createQuery("from message", Message.class).list();
            transaction.commit();
            return messages;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void createMessage(Message message) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateMessage(Message message) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public boolean deleteMessage(Long messageId) {
        Transaction transaction = null;
        boolean isDeleted = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Message message = session.get(Message.class, messageId);
            if (message != null) {
                session.delete(message);
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
