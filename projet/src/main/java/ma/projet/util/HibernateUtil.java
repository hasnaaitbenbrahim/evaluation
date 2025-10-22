package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.util.Properties;

public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    static {
        try {
            Configuration configuration = new Configuration();
            
            // Configuration des propriétés
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/gestion_projets?useSSL=false&serverTimezone=UTC");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.FORMAT_SQL, "true");
            
            // Configuration du pool de connexions
            properties.put(Environment.C3P0_MIN_SIZE, "5");
            properties.put(Environment.C3P0_MAX_SIZE, "20");
            properties.put(Environment.C3P0_TIMEOUT, "300");
            properties.put(Environment.C3P0_MAX_STATEMENTS, "50");
            properties.put(Environment.C3P0_IDLE_TEST_PERIOD, "3000");
            
            configuration.setProperties(properties);
            
            // Ajout des classes d'entité
            configuration.addAnnotatedClass(ma.projet.classes.Employe.class);
            configuration.addAnnotatedClass(ma.projet.classes.Projet.class);
            configuration.addAnnotatedClass(ma.projet.classes.Tache.class);
            configuration.addAnnotatedClass(ma.projet.classes.EmployeTache.class);
            
            sessionFactory = configuration.buildSessionFactory();
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation de SessionFactory: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
