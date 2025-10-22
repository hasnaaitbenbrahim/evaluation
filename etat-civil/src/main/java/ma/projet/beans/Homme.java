package ma.projet.beans;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "homme")
@PrimaryKeyJoinColumn(name = "personne_id")
public class Homme extends Personne {
    
    // Relation avec Mariage (un homme peut avoir plusieurs mariages)
    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL)
    private List<Mariage> mariages;
    
    // Constructeurs
    public Homme() {
        super();
    }
    
    public Homme(String nom, String prenom, String telephone, String adresse, java.util.Date dateNaissance) {
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
