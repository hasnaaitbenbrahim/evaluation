package ma.projet.classes;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "projet")
public class Projet {
    
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
    
    // Relation avec Employe (Chef de projet)
    @ManyToOne
    @JoinColumn(name = "chef_projet_id")
    private Employe chefProjet;
    
    // Relation avec Tache
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL)
    private List<Tache> taches;
    
    // Constructeurs
    public Projet() {
    }
    
    public Projet(String nom, Date dateDebut, Date dateFin) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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
    
    public Employe getChefProjet() {
        return chefProjet;
    }
    
    public void setChefProjet(Employe chefProjet) {
        this.chefProjet = chefProjet;
    }
    
    public List<Tache> getTaches() {
        return taches;
    }
    
    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }
    
    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }
}
