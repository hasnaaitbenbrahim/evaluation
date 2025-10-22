package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Employe;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {
    
    private SessionFactory sessionFactory;
    
    public EmployeTacheService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public boolean create(EmployeTache o) {
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
    public boolean update(EmployeTache o) {
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
    public boolean delete(EmployeTache o) {
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
    public EmployeTache findById(int id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(EmployeTache.class, id);
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
    public List<EmployeTache> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<EmployeTache> query = session.createQuery("FROM EmployeTache", EmployeTache.class);
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
    
    // Méthode pour assigner un employé à une tâche
    public boolean assignerEmployeATache(int employeId, int tacheId, java.util.Date dateDebutReelle, java.util.Date dateFinReelle) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            
            Employe employe = session.get(Employe.class, employeId);
            Tache tache = session.get(Tache.class, tacheId);
            
            if (employe != null && tache != null) {
                EmployeTache employeTache = new EmployeTache(dateDebutReelle, dateFinReelle);
                employeTache.setEmploye(employe);
                employeTache.setTache(tache);
                
                session.save(employeTache);
                tx.commit();
                return true;
            } else {
                tx.rollback();
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
    
    // Méthode pour obtenir toutes les assignations d'un employé
    public List<EmployeTache> getAssignationsParEmploye(int employeId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "SELECT et FROM EmployeTache et WHERE et.employe.id = :employeId";
            Query<EmployeTache> query = session.createQuery(hql, EmployeTache.class);
            query.setParameter("employeId", employeId);
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
    
    // Méthode pour obtenir toutes les assignations d'une tâche
    public List<EmployeTache> getAssignationsParTache(int tacheId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "SELECT et FROM EmployeTache et WHERE et.tache.id = :tacheId";
            Query<EmployeTache> query = session.createQuery(hql, EmployeTache.class);
            query.setParameter("tacheId", tacheId);
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
}
