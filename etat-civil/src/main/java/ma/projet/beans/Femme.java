package ma.projet.beans;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "femme")
@PrimaryKeyJoinColumn(name = "personne_id")
@NamedQueries({
    @NamedQuery(name = "Femme.findFemmesMariesPlusieursFois", 
                query = "SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"),
    @NamedQuery(name = "Femme.countEnfantsEntreDates", 
                query = "SELECT SUM(m.nbrEnfant) FROM Mariage m WHERE m.femme = :femme AND m.dateDebut >= :dateDebut AND m.dateDebut <= :dateFin")
})
public class Femme extends Personne {
    
    // Relation avec Mariage (une femme peut avoir plusieurs mariages)
    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL)
    private List<Mariage> mariages;
    
    // Constructeurs
    public Femme() {
        super();
    }
    
    public Femme(String nom, String prenom, String telephone, String adresse, java.util.Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
    
    // Getters et Setters
    public List<Mariage> getMariages() {
        return mariages;
    }
    
    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}
