package ma.projet.classes;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "employe_tache")
public class EmployeTache {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "dateDebutReelle")
    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;
    
    @Column(name = "dateFinReelle")
    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;
    
    // Relation avec Employe
    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe employe;
    
    // Relation avec Tache
    @ManyToOne
    @JoinColumn(name = "tache_id")
    private Tache tache;
    
    // Constructeurs
    public EmployeTache() {
    }
    
    public EmployeTache(Date dateDebutReelle, Date dateFinReelle) {
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }
    
    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }
    
    public Date getDateFinReelle() {
        return dateFinReelle;
    }
    
    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }
    
    public Employe getEmploye() {
        return employe;
    }
    
    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    
    public Tache getTache() {
        return tache;
    }
    
    public void setTache(Tache tache) {
        this.tache = tache;
    }
    
    @Override
    public String toString() {
        return "EmployeTache{" +
                "id=" + id +
                ", dateDebutReelle=" + dateDebutReelle +
                ", dateFinReelle=" + dateFinReelle +
                '}';
    }
}
