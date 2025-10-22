package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ma.projet.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {
    
    private SessionFactory sessionFactory;
    
    public HommeService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public boolean create(Homme o) {
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
    public boolean update(Homme o) {
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
    public boolean delete(Homme o) {
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
    public Homme findById(int id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(Homme.class, id);
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
    public List<Homme> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Homme> query = session.createQuery("FROM Homme", Homme.class);
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
    
    // Méthode pour afficher les épouses d'un homme entre deux dates
    public void afficherEpousesEntreDates(int hommeId, Date dateDebut, Date dateFin) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Homme homme = session.get(Homme.class, hommeId);
            
            if (homme != null) {
                System.out.println("Épouses de " + homme.getPrenom() + " " + homme.getNom() + 
                                 " entre " + new SimpleDateFormat("dd/MM/yyyy").format(dateDebut) + 
                                 " et " + new SimpleDateFormat("dd/MM/yyyy").format(dateFin) + ":");
                
                String hql = "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId " +
                           "AND m.dateDebut >= :dateDebut AND m.dateDebut <= :dateFin";
                
                Query<Mariage> query = session.createQuery(hql, Mariage.class);
                query.setParameter("hommeId", hommeId);
                query.setParameter("dateDebut", dateDebut);
                query.setParameter("dateFin", dateFin);
                
                List<Mariage> mariages = query.list();
                
                if (mariages != null && !mariages.isEmpty()) {
                    for (Mariage mariage : mariages) {
                        System.out.println("- " + mariage.getFemme().getPrenom() + " " + 
                                         mariage.getFemme().getNom() + 
                                         " (Marié le: " + new SimpleDateFormat("dd/MM/yyyy").format(mariage.getDateDebut()) + 
                                         ", Enfants: " + mariage.getNbrEnfant() + ")");
                    }
                } else {
                    System.out.println("Aucune épouse trouvée pour cette période.");
                }
            } else {
                System.out.println("Homme non trouvé avec l'ID: " + hommeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // Méthode pour afficher les mariages d'un homme avec tous les détails
    public void afficherMariagesDetail(int hommeId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Homme homme = session.get(Homme.class, hommeId);
            
            if (homme != null) {
                System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
                
                String hql = "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId ORDER BY m.dateDebut";
                Query<Mariage> query = session.createQuery(hql, Mariage.class);
                query.setParameter("hommeId", hommeId);
                
                List<Mariage> mariages = query.list();
                
                if (mariages != null && !mariages.isEmpty()) {
                    System.out.println("Mariages En Cours :");
                    int countEnCours = 1;
                    int countEchoues = 1;
                    
                    for (Mariage mariage : mariages) {
                        if (mariage.getDateFin() == null) {
                            // Mariage en cours
                            System.out.println(countEnCours + ". Femme : " + 
                                             mariage.getFemme().getNom() + " " + mariage.getFemme().getPrenom() + 
                                             "   Date Début : " + new SimpleDateFormat("dd/MM/yyyy").format(mariage.getDateDebut()) + 
                                             "    Nbr Enfants : " + mariage.getNbrEnfant());
                            countEnCours++;
                        } else {
                            // Mariage échoué
                            if (countEchoues == 1) {
                                System.out.println("\nMariages échoués :");
                            }
                            System.out.println(countEchoues + ". Femme : " + 
                                             mariage.getFemme().getNom() + " " + mariage.getFemme().getPrenom() + 
                                             "   Date Début : " + new SimpleDateFormat("dd/MM/yyyy").format(mariage.getDateDebut()) + 
                                             "    Date Fin : " + new SimpleDateFormat("dd/MM/yyyy").format(mariage.getDateFin()) + 
                                             "    Nbr Enfants : " + mariage.getNbrEnfant());
                            countEchoues++;
                        }
                    }
                } else {
                    System.out.println("Aucun mariage trouvé pour cet homme.");
                }
            } else {
                System.out.println("Homme non trouvé avec l'ID: " + hommeId);
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
