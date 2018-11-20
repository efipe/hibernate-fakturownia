package com.sda.gda17;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

        private static SessionFactory sessionFactory;


        static {
            // blok static dziala podobnie jak BEFORE przy testach, uruchamia odrazu przy utworzeniu instancji klasy
            // Tworzymy obiekt ktory pobiera konfiguracje z naszego pliku hibernate.cfg
            StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            //metadata to informacje dotyczace plikow z danych zaladowanych wczesniej
            //tworzymy obiekt metadata
            Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();
            //stworzenie sesji z danych zawartych w pliku hibernate cfg xml
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        }

        public static SessionFactory getSessionFactory() {
            return sessionFactory;
        }

    }

