package ma.projet.classes;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "tache")
@NamedQueries({
    @NamedQuery(name = "Tache.findByPrixSuperieur", 
                query = "SELECT t FROM Tache t WHERE t.prix > :prix"),
    @NamedQuery(name = "Tache.findByDateRange", 
                query = "SELECT t FROM Tache t WHERE t.dateDebut >= :dateDebut AND t.dateFin <= :dateFin")
})
public class Tache {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "dateDebut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(name = "dateFin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @Column(name = "prix")
    private double prix;
    
    // Relation avec Projet
    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;
    
    // Relation avec Employe via EmployeTache
    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL)
    private List<EmployeTache> employeTaches;
    
    // Constructeurs
    public Tache() {
    }
    
    public Tache(String nom, Date dateDebut, Date dateFin, double prix) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public Date getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public Date getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    
    public double getPrix() {
        return prix;
    }
    
    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    public Projet getProjet() {
        return projet;
    }
    
    public void setProjet(Projet projet) {
        this.projet = projet;
    }
    
    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }
    
    public void setEmployeTaches(List<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }
    
    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", prix=" + prix +
                '}';
    }
}
