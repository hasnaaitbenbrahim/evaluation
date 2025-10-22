package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.List;

public class ProjetService implements IDao<Projet> {
    
    private SessionFactory sessionFactory;
    
    public ProjetService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public boolean create(Projet o) {
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
    public boolean update(Projet o) {
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
    public boolean delete(Projet o) {
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
    public Projet findById(int id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(Projet.class, id);
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
    public List<Projet> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Projet> query = session.createQuery("FROM Projet", Projet.class);
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
    
    // Méthode pour afficher la liste des tâches planifiées pour un projet
    public void afficherTachesPlanifiees(int projetId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Projet projet = session.get(Projet.class, projetId);
            
            if (projet != null) {
                System.out.println("Projet : " + projet.getId() + 
                                 "      Nom : " + projet.getNom() + 
                                 "     Date début : " + new SimpleDateFormat("dd MMMM yyyy").format(projet.getDateDebut()));
                System.out.println("Liste des tâches planifiées:");
                System.out.println("Num Nom            Date Début Planifiée   Date Fin Planifiée");
                
                List<Tache> taches = projet.getTaches();
                if (taches != null && !taches.isEmpty()) {
                    for (Tache tache : taches) {
                        System.out.printf("%-3d %-15s %-20s %-20s%n",
                            tache.getId(),
                            tache.getNom(),
                            new SimpleDateFormat("dd/MM/yyyy").format(tache.getDateDebut()),
                            new SimpleDateFormat("dd/MM/yyyy").format(tache.getDateFin())
                        );
                    }
                } else {
                    System.out.println("Aucune tâche planifiée pour ce projet.");
                }
            } else {
                System.out.println("Projet non trouvé avec l'ID: " + projetId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // Méthode pour afficher la liste des tâches réalisées avec les dates réelles
    public void afficherTachesRealisees(int projetId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Projet projet = session.get(Projet.class, projetId);
            
            if (projet != null) {
                System.out.println("Projet : " + projet.getId() + 
                                 "      Nom : " + projet.getNom() + 
                                 "     Date début : " + new SimpleDateFormat("dd MMMM yyyy").format(projet.getDateDebut()));
                System.out.println("Liste des tâches réalisées:");
                System.out.println("Num Nom            Date Début Réelle   Date Fin Réelle");
                
                String hql = "SELECT et FROM EmployeTache et " +
                           "JOIN et.tache t " +
                           "WHERE t.projet.id = :projetId " +
                           "AND et.dateDebutReelle IS NOT NULL " +
                           "AND et.dateFinReelle IS NOT NULL";
                
                Query<EmployeTache> query = session.createQuery(hql, EmployeTache.class);
                query.setParameter("projetId", projetId);
                
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
                    System.out.println("Aucune tâche réalisée pour ce projet.");
                }
            } else {
                System.out.println("Projet non trouvé avec l'ID: " + projetId);
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
