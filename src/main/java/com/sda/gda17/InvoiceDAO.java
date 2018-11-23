package com.sda.gda17;

import org.hibernate.*;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public <T extends SingleSaver> Optional <T> getByID(Long id, Class<T> tClass){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<T> query = session.createQuery("from "+ tClass.getName() + " where id in :id", tClass);
            query.setParameter("id", id);
//            System.out.println(query.list());
            return Optional.ofNullable(query.getSingleResult());
        } catch (PersistenceException pe) {
            System.out.println("Nie udalo sie pobrac z bazy");
           return Optional.empty();
        }
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

    public List<Payer> getAllPayersFromDatabase(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            //stworz zapytanie
            Query<Payer> query = session.createQuery("from Payer payer ", Payer.class);
            //wywolaj zapytanie
            List<Payer> payers = query.list();
            System.out.println(payers);
            return payers;
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

    public <T extends SingleSaver> boolean remove(Long id, Class<T>tClass){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        Optional<T> singleSaverOptional = getByID(id,tClass);
        if (singleSaverOptional.isPresent()) {
            T singleSaver = singleSaverOptional.get();

            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                // za pomoca usuwania kaskadowego ( adnotacja przy studencie nie musimy wczesniej usuwać relacji student-> ocena
//                for (Ocena ocena: student.getOceny()){
//                    // czyscimy relacje zanim usuniemy studenta
//                    session.delete(ocena);
//                }
                // usuwanie studenta
                session.delete(singleSaver);
                transaction.commit();
                return true;
            } catch (SessionException se) {
                // w razie bledu zrob rollback sprzed wykonania transakcji
                if (transaction != null) {
                    transaction.rollback();
                    return false;
                }
            }
        }
        return false;
    }


}

