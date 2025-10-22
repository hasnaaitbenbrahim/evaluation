package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ma.projet.util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {
    
    private SessionFactory sessionFactory;
    
    public FemmeService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    
    @Override
    public boolean create(Femme o) {
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
    public boolean update(Femme o) {
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
    public boolean delete(Femme o) {
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
    public Femme findById(int id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            return session.get(Femme.class, id);
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
    public List<Femme> findAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Femme> query = session.createQuery("FROM Femme", Femme.class);
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
    
    // Méthode pour afficher la femme la plus âgée
    public void afficherFemmePlusAgee() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "FROM Femme ORDER BY dateNaissance ASC";
            Query<Femme> query = session.createQuery(hql, Femme.class);
            query.setMaxResults(1);
            
            Femme femme = query.uniqueResult();
            
            if (femme != null) {
                System.out.println("Femme la plus âgée : " + femme.getPrenom() + " " + femme.getNom() + 
                                 " (Née le: " + new SimpleDateFormat("dd/MM/yyyy").format(femme.getDateNaissance()) + ")");
            } else {
                System.out.println("Aucune femme trouvée.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // Méthode pour exécuter une requête native nommée retournant le nombre d'enfants d'une femme entre deux dates
    public void afficherNombreEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Femme femme = session.get(Femme.class, femmeId);
            
            if (femme != null) {
                String sql = "SELECT SUM(nbrEnfant) FROM mariage WHERE femme_id = ? AND dateDebut >= ? AND dateDebut <= ?";
                Query<Long> query = session.createNativeQuery(sql);
                query.setParameter(1, femmeId);
                query.setParameter(2, dateDebut);
                query.setParameter(3, dateFin);
                
                Object result = query.uniqueResult();
                Long nombreEnfants = result != null ? ((Number) result).longValue() : 0L;
                
                System.out.println("Nombre d'enfants de " + femme.getPrenom() + " " + femme.getNom() + 
                                 " entre " + new SimpleDateFormat("dd/MM/yyyy").format(dateDebut) + 
                                 " et " + new SimpleDateFormat("dd/MM/yyyy").format(dateFin) + ": " + 
                                 (nombreEnfants != null ? nombreEnfants : 0));
            } else {
                System.out.println("Femme non trouvée avec l'ID: " + femmeId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // Méthode pour exécuter une requête nommée retournant les femmes mariées au moins deux fois
    public void afficherFemmesMariesPlusieursFois() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query<Femme> query = session.getNamedQuery("Femme.findFemmesMariesPlusieursFois");
            
            List<Femme> femmes = query.list();
            
            System.out.println("Femmes mariées au moins deux fois:");
            if (femmes != null && !femmes.isEmpty()) {
                for (Femme femme : femmes) {
                    System.out.println("- " + femme.getPrenom() + " " + femme.getNom());
                }
            } else {
                System.out.println("Aucune femme mariée plusieurs fois trouvée.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    // Méthode utilisant l'API Criteria pour afficher le nombre d'hommes mariés à quatre femmes entre deux dates
    public void afficherHommesMariesQuatreFemmesEntreDates(Date dateDebut, Date dateFin) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Mariage> mariage = cq.from(Mariage.class);
            
            cq.select(cb.countDistinct(mariage.get("homme")))
              .where(cb.and(
                  cb.greaterThanOrEqualTo(mariage.get("dateDebut"), dateDebut),
                  cb.lessThanOrEqualTo(mariage.get("dateDebut"), dateFin)
              ))
              .groupBy(mariage.get("homme"))
              .having(cb.equal(cb.count(mariage.get("femme")), 4L));
            
            Query<Long> query = session.createQuery(cq);
            List<Long> resultats = query.list();
            
            System.out.println("Nombre d'hommes mariés à quatre femmes entre " + 
                             new SimpleDateFormat("dd/MM/yyyy").format(dateDebut) + 
                             " et " + new SimpleDateFormat("dd/MM/yyyy").format(dateFin) + ": " + 
                             resultats.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
