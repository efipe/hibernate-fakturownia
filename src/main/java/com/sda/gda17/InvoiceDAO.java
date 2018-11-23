package com.sda.gda17;

import org.hibernate.*;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    public boolean saveSingleIntoDatabase(SingleSaver sv) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            // otwieramy
            transaction = session.beginTransaction();
            session.save(sv);
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

    public boolean mergeCompaniesWithPayers(long payerID, Long companyID) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            //stworz zapytanie

            Query<Payer> queryT = session.createQuery("from Payer where id = :zmienna", Payer.class);
            queryT.setParameter("zmienna", payerID);

            Query<Company> queryS = session.createQuery("from Company where id = :zmienna", Company.class);
            queryS.setParameter("zmienna", companyID);

            Payer payer = queryT.getSingleResult();
            Company company = queryS.getSingleResult();


            payer.getCompanyList().add(company);
            company.getPayersList().add(payer);

            session.save(payer);
            session.save(company);

            transaction.commit();

            return true;

        } catch (SessionException se) {

            if (transaction != null) {
                transaction.rollback();

            }
            return false;
        }
    }

}

