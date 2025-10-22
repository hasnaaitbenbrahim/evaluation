package ma.projet;

import ma.projet.classes.*;
import ma.projet.service.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestProjetManagement {
    
    public static void main(String[] args) {
        try {
            // Initialisation des services
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();
            
            System.out.println("=== TEST DE L'APPLICATION DE GESTION DE PROJETS ===\n");
            
            // 1. Création des employés
            System.out.println("1. Création des employés:");
            Employe emp1 = new Employe("Alami", "Ahmed", "0612345678");
            Employe emp2 = new Employe("Benali", "Fatima", "0612345679");
            Employe emp3 = new Employe("Chraibi", "Omar", "0612345680");
            
            employeService.create(emp1);
            employeService.create(emp2);
            employeService.create(emp3);
            
            System.out.println("Employés créés avec succès!\n");
            
            // 2. Création des projets
            System.out.println("2. Création des projets:");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dateDebut1 = sdf.parse("14/01/2013");
            Date dateFin1 = sdf.parse("30/06/2013");
            
            Projet projet1 = new Projet("Gestion de stock", dateDebut1, dateFin1);
            projet1.setChefProjet(emp1);
            
            Date dateDebut2 = sdf.parse("01/07/2013");
            Date dateFin2 = sdf.parse("31/12/2013");
            
            Projet projet2 = new Projet("Système de facturation", dateDebut2, dateFin2);
            projet2.setChefProjet(emp2);
            
            projetService.create(projet1);
            projetService.create(projet2);
            
            System.out.println("Projets créés avec succès!\n");
            
            // 3. Création des tâches
            System.out.println("3. Création des tâches:");
            
            // Tâches pour le projet 1
            Tache tache1 = new Tache("Analyse", sdf.parse("10/02/2013"), sdf.parse("20/02/2013"), 1500.0);
            tache1.setProjet(projet1);
            
            Tache tache2 = new Tache("Conception", sdf.parse("10/03/2013"), sdf.parse("15/03/2013"), 2000.0);
            tache2.setProjet(projet1);
            
            Tache tache3 = new Tache("Développement", sdf.parse("10/04/2013"), sdf.parse("25/04/2013"), 5000.0);
            tache3.setProjet(projet1);
            
            Tache tache4 = new Tache("Test", sdf.parse("26/04/2013"), sdf.parse("10/05/2013"), 800.0);
            tache4.setProjet(projet1);
            
            // Tâches pour le projet 2
            Tache tache5 = new Tache("Analyse besoins", sdf.parse("15/07/2013"), sdf.parse("25/07/2013"), 1200.0);
            tache5.setProjet(projet2);
            
            Tache tache6 = new Tache("Architecture", sdf.parse("26/07/2013"), sdf.parse("05/08/2013"), 1800.0);
            tache6.setProjet(projet2);
            
            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            tacheService.create(tache4);
            tacheService.create(tache5);
            tacheService.create(tache6);
            
            System.out.println("Tâches créées avec succès!\n");
            
            // 4. Assignation des employés aux tâches avec dates réelles
            System.out.println("4. Assignation des employés aux tâches:");
            
            // Assignations pour le projet 1
            employeTacheService.assignerEmployeATache(emp1.getId(), tache1.getId(), 
                sdf.parse("10/02/2013"), sdf.parse("20/02/2013"));
            employeTacheService.assignerEmployeATache(emp2.getId(), tache2.getId(), 
                sdf.parse("10/03/2013"), sdf.parse("15/03/2013"));
            employeTacheService.assignerEmployeATache(emp3.getId(), tache3.getId(), 
                sdf.parse("10/04/2013"), sdf.parse("25/04/2013"));
            employeTacheService.assignerEmployeATache(emp1.getId(), tache4.getId(), 
                sdf.parse("26/04/2013"), sdf.parse("10/05/2013"));
            
            // Assignations pour le projet 2
            employeTacheService.assignerEmployeATache(emp2.getId(), tache5.getId(), 
                sdf.parse("15/07/2013"), sdf.parse("25/07/2013"));
            employeTacheService.assignerEmployeATache(emp3.getId(), tache6.getId(), 
                sdf.parse("26/07/2013"), sdf.parse("05/08/2013"));
            
            System.out.println("Assignations créées avec succès!\n");
            
            // 5. Tests des méthodes demandées
            System.out.println("=== TESTS DES MÉTHODES DEMANDÉES ===\n");
            
            // Test ProjetService - Afficher les tâches planifiées
            System.out.println("5.1. Tâches planifiées pour le projet 'Gestion de stock':");
            projetService.afficherTachesPlanifiees(projet1.getId());
            System.out.println();
            
            // Test ProjetService - Afficher les tâches réalisées
            System.out.println("5.2. Tâches réalisées pour le projet 'Gestion de stock':");
            projetService.afficherTachesRealisees(projet1.getId());
            System.out.println();
            
            // Test EmployeService - Tâches réalisées par un employé
            System.out.println("5.3. Tâches réalisées par Ahmed Alami:");
            employeService.afficherTachesRealiseesParEmploye(emp1.getId());
            System.out.println();
            
            // Test EmployeService - Projets gérés par un employé
            System.out.println("5.4. Projets gérés par Ahmed Alami:");
            employeService.afficherProjetsGeresParEmploye(emp1.getId());
            System.out.println();
            
            // Test TacheService - Tâches avec prix > 1000 DH
            System.out.println("5.5. Tâches avec prix supérieur à 1000 DH:");
            tacheService.afficherTachesPrixSuperieur(1000.0);
            System.out.println();
            
            // Test TacheService - Tâches réalisées entre deux dates
            System.out.println("5.6. Tâches réalisées entre 01/02/2013 et 30/04/2013:");
            tacheService.afficherTachesEntreDates(sdf.parse("01/02/2013"), sdf.parse("30/04/2013"));
            System.out.println();
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
