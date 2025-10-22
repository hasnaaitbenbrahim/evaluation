package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.Tache;
import ma.projet.classes.Projet;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeService implements IDao<Employe> {
    
    private SessionFactory sessionFactory;
    
    public EmployeService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public boolean create(Employe o) {
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
    public boolean update(Employe o) {
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
    public boolean delete(Employe o) {
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
    public Employe findById(int id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(Employe.class, id);
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
    public List<Employe> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Employe> query = session.createQuery("FROM Employe", Employe.class);
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
    
    // Méthode pour afficher la liste des tâches réalisées par un employé
    public void afficherTachesRealiseesParEmploye(int employeId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Employe employe = session.get(Employe.class, employeId);
            
            if (employe != null) {
                System.out.println("Employé : " + employe.getPrenom() + " " + employe.getNom());
                System.out.println("Liste des tâches réalisées:");
                System.out.println("Num Nom            Date Début Réelle   Date Fin Réelle");
                
                String hql = "SELECT et FROM EmployeTache et " +
                           "WHERE et.employe.id = :employeId " +
                           "AND et.dateDebutReelle IS NOT NULL " +
                           "AND et.dateFinReelle IS NOT NULL";
                
                Query<EmployeTache> query = session.createQuery(hql, EmployeTache.class);
                query.setParameter("employeId", employeId);
                
                List<EmployeTache> employeTaches = query.list();
                
                if (employeTaches != null && !employeTaches.isEmpty()) {
                    for (EmployeTache et : employeTaches) {
                        System.out.printf("%-3d %-15s %-20s %-20s%n",
                            et.getTache().getId(),
                            et.getTache().getNom(),
                            new SimpleDateFormat("dd/MM/yyyy").format(et.getDateDebutReelle()),
                            new SimpleDateFormat("dd/MM/yyyy").format(et.getDateFinReelle())
                        );
                    }
                } else {
                    System.out.println("Aucune tâche réalisée par cet employé.");
                }
            } else {
                System.out.println("Employé non trouvé avec l'ID: " + employeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // Méthode pour afficher la liste des projets gérés par un employé
    public void afficherProjetsGeresParEmploye(int employeId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Employe employe = session.get(Employe.class, employeId);
            
            if (employe != null) {
                System.out.println("Employé : " + employe.getPrenom() + " " + employe.getNom());
                System.out.println("Liste des projets gérés:");
                System.out.println("Num Nom            Date Début        Date Fin");
                
                List<Projet> projets = employe.getProjetsGeres();
                
                if (projets != null && !projets.isEmpty()) {
                    for (Projet projet : projets) {
                        System.out.printf("%-3d %-15s %-17s %-17s%n",
                            projet.getId(),
                            projet.getNom(),
                            new SimpleDateFormat("dd/MM/yyyy").format(projet.getDateDebut()),
                            new SimpleDateFormat("dd/MM/yyyy").format(projet.getDateFin())
                        );
                    }
                } else {
                    System.out.println("Aucun projet géré par cet employé.");
                }
            } else {
                System.out.println("Employé non trouvé avec l'ID: " + employeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
