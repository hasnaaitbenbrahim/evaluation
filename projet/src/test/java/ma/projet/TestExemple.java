package ma.projet;

import ma.projet.classes.*;
import ma.projet.service.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestExemple {
    
    public static void main(String[] args) {
        try {
            // Initialisation des services
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();
            
            System.out.println("=== EXEMPLE D'AFFICHAGE ATTENDU ===\n");
            
            // Création d'un employé chef de projet
            Employe chefProjet = new Employe("Dupont", "Jean", "0612345678");
            employeService.create(chefProjet);
            
            // Création du projet "Gestion de stock"
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateDebutProjet = sdf.parse("14/01/2013");
            Date dateFinProjet = sdf.parse("30/06/2013");
            
            Projet projet = new Projet("Gestion de stock", dateDebutProjet, dateFinProjet);
            projet.setChefProjet(chefProjet);
            projetService.create(projet);
            
            // Création des tâches
            Tache tache1 = new Tache("Analyse", sdf.parse("10/02/2013"), sdf.parse("20/02/2013"), 1500.0);
            tache1.setProjet(projet);
            
            Tache tache2 = new Tache("Conception", sdf.parse("10/03/2013"), sdf.parse("15/03/2013"), 2000.0);
            tache2.setProjet(projet);
            
            Tache tache3 = new Tache("Développement", sdf.parse("10/04/2013"), sdf.parse("25/04/2013"), 5000.0);
            tache3.setProjet(projet);
            
            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            
            // Création des employés pour les tâches
            Employe emp1 = new Employe("Martin", "Pierre", "0612345679");
            Employe emp2 = new Employe("Durand", "Marie", "0612345680");
            Employe emp3 = new Employe("Moreau", "Paul", "0612345681");
            
            employeService.create(emp1);
            employeService.create(emp2);
            employeService.create(emp3);
            
            // Assignation des employés aux tâches avec dates réelles
            employeTacheService.assignerEmployeATache(emp1.getId(), tache1.getId(), 
                sdf.parse("10/02/2013"), sdf.parse("20/02/2013"));
            employeTacheService.assignerEmployeATache(emp2.getId(), tache2.getId(), 
                sdf.parse("10/03/2013"), sdf.parse("15/03/2013"));
            employeTacheService.assignerEmployeATache(emp3.getId(), tache3.getId(), 
                sdf.parse("10/04/2013"), sdf.parse("25/04/2013"));
            
            // Affichage selon l'exemple demandé
            System.out.println("Affichage des tâches réalisées pour le projet 'Gestion de stock':");
            projetService.afficherTachesRealisees(projet.getId());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
