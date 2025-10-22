package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {
    
    private SessionFactory sessionFactory;
    
    public TacheService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public boolean create(Tache o) {
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
    public boolean update(Tache o) {
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
    public boolean delete(Tache o) {
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
    public Tache findById(int id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(Tache.class, id);
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
    public List<Tache> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Tache> query = session.createQuery("FROM Tache", Tache.class);
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
    
    // Méthode pour afficher les tâches dont le prix est supérieur à 1000 DH (requête nommée)
    public void afficherTachesPrixSuperieur(double prix) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Tache> query = session.getNamedQuery("Tache.findByPrixSuperieur");
            query.setParameter("prix", prix);
            
            List<Tache> taches = query.list();
            
            System.out.println("Tâches dont le prix est supérieur à " + prix + " DH:");
            System.out.println("Num Nom            Prix (DH)");
            
            if (taches != null && !taches.isEmpty()) {
                for (Tache tache : taches) {
                    System.out.printf("%-3d %-15s %.2f%n",
                        tache.getId(),
                        tache.getNom(),
                        tache.getPrix()
                    );
                }
            } else {
                System.out.println("Aucune tâche trouvée avec un prix supérieur à " + prix + " DH.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // Méthode pour afficher les tâches réalisées entre deux dates
    public void afficherTachesEntreDates(Date dateDebut, Date dateFin) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Tache> query = session.getNamedQuery("Tache.findByDateRange");
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            
            List<Tache> taches = query.list();
            
            System.out.println("Tâches réalisées entre " + 
                             new SimpleDateFormat("dd/MM/yyyy").format(dateDebut) + 
                             " et " + 
                             new SimpleDateFormat("dd/MM/yyyy").format(dateFin) + ":");
            System.out.println("Num Nom            Date Début        Date Fin          Prix (DH)");
            
            if (taches != null && !taches.isEmpty()) {
                for (Tache tache : taches) {
                    System.out.printf("%-3d %-15s %-17s %-17s %.2f%n",
                        tache.getId(),
                        tache.getNom(),
                        new SimpleDateFormat("dd/MM/yyyy").format(tache.getDateDebut()),
                        new SimpleDateFormat("dd/MM/yyyy").format(tache.getDateFin()),
                        tache.getPrix()
                    );
                }
            } else {
                System.out.println("Aucune tâche trouvée dans cette période.");
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
