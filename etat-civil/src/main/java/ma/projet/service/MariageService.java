package ma.projet.service;

import ma.projet.beans.Mariage;
import ma.projet.beans.Homme;
import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MariageService implements IDao<Mariage> {
    
    private SessionFactory sessionFactory;
    
    public MariageService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public boolean create(Mariage o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public boolean update(Mariage o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public boolean delete(Mariage o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public Mariage findById(int id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(Mariage.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public List<Mariage> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Mariage> query = session.createQuery("FROM Mariage", Mariage.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // Méthode pour créer un mariage entre un homme et une femme
    public boolean creerMariage(int hommeId, int femmeId, Date dateDebut, Date dateFin, int nbrEnfant) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            
            Homme homme = session.get(Homme.class, hommeId);
            Femme femme = session.get(Femme.class, femmeId);
            
            if (homme != null && femme != null) {
                Mariage mariage = new Mariage(dateDebut, dateFin, nbrEnfant);
                mariage.setHomme(homme);
                mariage.setFemme(femme);
                
                session.save(mariage);
                tx.commit();
                return true;
            } else {
                System.out.println("Homme ou femme non trouvé.");
                return false;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
