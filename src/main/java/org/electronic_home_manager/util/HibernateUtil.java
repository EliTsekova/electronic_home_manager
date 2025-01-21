package org.electronic_home_manager.util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Consumer;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Зареждане на application.properties
            Properties settings = new Properties();
            try (InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (input == null) {
                    throw new RuntimeException("Unable to find application.properties");
                }
                settings.load(input);
            }

            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", settings.getProperty("hibernate.connection.driver_class"));
            configuration.setProperty("hibernate.connection.url", settings.getProperty("hibernate.connection.url"));
            configuration.setProperty("hibernate.connection.username", settings.getProperty("hibernate.connection.username"));
            configuration.setProperty("hibernate.connection.password", settings.getProperty("hibernate.connection.password"));
            configuration.setProperty("hibernate.dialect", settings.getProperty("hibernate.dialect"));
            configuration.setProperty("hibernate.show_sql", settings.getProperty("hibernate.show_sql"));
            configuration.setProperty("hibernate.format_sql", settings.getProperty("hibernate.format_sql"));
            configuration.setProperty("hibernate.hbm2ddl.auto", settings.getProperty("hibernate.hbm2ddl.auto"));

            // Register entity classes
            configuration.addAnnotatedClass(org.electronic_home_manager.entity.Apartment.class);
            configuration.addAnnotatedClass(org.electronic_home_manager.entity.Building.class);
            configuration.addAnnotatedClass(org.electronic_home_manager.entity.Company.class);
            configuration.addAnnotatedClass(org.electronic_home_manager.entity.Contract.class);
            configuration.addAnnotatedClass(org.electronic_home_manager.entity.Employee.class);
            configuration.addAnnotatedClass(org.electronic_home_manager.entity.Fee.class);
            configuration.addAnnotatedClass(org.electronic_home_manager.entity.Resident.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void doInTransaction(Consumer<Session> action) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            action.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }



}
