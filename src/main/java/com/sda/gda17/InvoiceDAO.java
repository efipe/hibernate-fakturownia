package com.sda.gda17;

import org.hibernate.*;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    public boolean saveInvoicesIntoDatabase(Invoice inv) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // otwieramy
            transaction = session.beginTransaction();
            session.save(inv);
            transaction.commit();

        } catch (SessionException se) {
            // w razie bledu zrob rollback sprzed wykonania transakcji
            if (transaction != null) {
                transaction.rollback();
                return false;
            }
        }
        return true;
    }

    public List<Invoice> getAllInvoicesFromDatabase() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {

            //stworz zapytanie

            Query<Invoice> query = session.createQuery("from Invoice invoice ", Invoice.class);

            //wywolaj zapytanie

            List<Invoice> invoices = query.list();
            System.out.println(invoices);
            return invoices;
        } catch (SessionException se) {

            // jezeli cos pojdzie nie tak - wypiszmy komunikat z loggerem
            // todo: logger
            System.err.println();
        }
        // zwracam pusta liste jezeli niczego nie uda sie znalezc
        return new ArrayList<>();
    }
}
