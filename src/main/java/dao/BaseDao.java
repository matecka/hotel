package dao;

import config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.function.Function;

public abstract class BaseDao {

    public <T> T executeTransaction(Function<Session, T> function) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T result = function.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Transaction Field", e);
        }
    }
}
