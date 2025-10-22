package ma.projet;

import ma.projet.beans.*;
import ma.projet.service.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    
    public static void main(String[] args) {
        try {
            // Initialisation des services
            HommeService hommeService = new HommeService();
            FemmeService femmeService = new FemmeService();
            MariageService mariageService = new MariageService();
            
            System.out.println("=== APPLICATION DE GESTION DE L'ÉTAT CIVIL ===\n");
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            // 1. Créer 10 femmes et 5 hommes
            System.out.println("1. Création des personnes:");
            
            // Création des femmes
            Femme[] femmes = new Femme[10];
            String[] nomsFemmes = {"ALAMI", "BENALI", "CHRAIBI", "DAHBI", "ELAMI", 
                                 "FARIDI", "GHAZI", "HASSANI", "IDRISSI", "JAFFARI"};
            String[] prenomsFemmes = {"Fatima", "Aicha", "Khadija", "Zineb", "Naima", 
                                     "Hakima", "Latifa", "Malika", "Samira", "Yasmina"};
            
            for (int i = 0; i < 10; i++) {
                femmes[i] = new Femme(nomsFemmes[i], prenomsFemmes[i], "061234567" + i, 
                                    "Adresse " + (i+1), sdf.parse("01/01/" + (1970 + i)));
                femmeService.create(femmes[i]);
            }
            
            // Création des hommes
            Homme[] hommes = new Homme[5];
            String[] nomsHommes = {"SAFI", "BENJELLOUN", "ALAOUI", "CHAKIR", "DAHMANI"};
            String[] prenomsHommes = {"Said", "Ahmed", "Omar", "Youssef", "Hassan"};
            
            for (int i = 0; i < 5; i++) {
                hommes[i] = new Homme(nomsHommes[i], prenomsHommes[i], "061234568" + i, 
                                    "Adresse " + (i+1), sdf.parse("01/01/" + (1965 + i)));
                hommeService.create(hommes[i]);
            }
            
            System.out.println("10 femmes et 5 hommes créés avec succès!\n");
            
            // 2. Afficher la liste des femmes
            System.out.println("2. Liste des femmes:");
            for (Femme femme : femmes) {
                System.out.println("- " + femme.getPrenom() + " " + femme.getNom());
            }
            System.out.println();
            
            // 3. Afficher la femme la plus âgée
            System.out.println("3. Femme la plus âgée:");
            femmeService.afficherFemmePlusAgee();
            System.out.println();
            
            // 4. Créer quelques mariages pour les tests
            System.out.println("4. Création des mariages:");
            
            // Mariages pour Said SAFI
            mariageService.creerMariage(hommes[0].getId(), femmes[0].getId(), 
                                      sdf.parse("03/09/1990"), null, 4);
            mariageService.creerMariage(hommes[0].getId(), femmes[1].getId(), 
                                      sdf.parse("03/09/1995"), null, 2);
            mariageService.creerMariage(hommes[0].getId(), femmes[2].getId(), 
                                      sdf.parse("04/11/2000"), null, 3);
            mariageService.creerMariage(hommes[0].getId(), femmes[3].getId(), 
                                      sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0);
            
            // Mariages pour Ahmed BENJELLOUN
            mariageService.creerMariage(hommes[1].getId(), femmes[4].getId(), 
                                      sdf.parse("15/06/1988"), null, 2);
            mariageService.creerMariage(hommes[1].getId(), femmes[5].getId(), 
                                      sdf.parse("20/03/1992"), sdf.parse("15/08/1995"), 1);
            mariageService.creerMariage(hommes[1].getId(), femmes[6].getId(), 
                                      sdf.parse("10/12/1996"), null, 3);
            mariageService.creerMariage(hommes[1].getId(), femmes[7].getId(), 
                                      sdf.parse("05/09/2001"), null, 1);
            
            // Mariages pour Omar ALAOUI
            mariageService.creerMariage(hommes[2].getId(), femmes[8].getId(), 
                                      sdf.parse("14/02/1991"), null, 2);
            mariageService.creerMariage(hommes[2].getId(), femmes[9].getId(), 
                                      sdf.parse("25/07/1998"), sdf.parse("10/11/2002"), 1);
            
            System.out.println("Mariages créés avec succès!\n");
            
            // 5. Afficher les épouses d'un homme donné
            System.out.println("5. Épouses de Said SAFI entre 1990 et 2005:");
            hommeService.afficherEpousesEntreDates(hommes[0].getId(), 
                                                 sdf.parse("01/01/1990"), sdf.parse("31/12/2005"));
            System.out.println();
            
            // 6. Afficher le nombre d'enfants d'une femme entre deux dates
            System.out.println("6. Nombre d'enfants de Fatima ALAMI entre 1985 et 2000:");
            femmeService.afficherNombreEnfantsEntreDates(femmes[0].getId(), 
                                                       sdf.parse("01/01/1985"), sdf.parse("31/12/2000"));
            System.out.println();
            
            // 7. Afficher les femmes mariées deux fois ou plus
            System.out.println("7. Femmes mariées au moins deux fois:");
            femmeService.afficherFemmesMariesPlusieursFois();
            System.out.println();
            
            // 8. Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("8. Hommes mariés à quatre femmes entre 1980 et 2010:");
            femmeService.afficherHommesMariesQuatreFemmesEntreDates(sdf.parse("01/01/1980"), 
                                                                   sdf.parse("31/12/2010"));
            System.out.println();
            
            // 9. Afficher les mariages d'un homme avec tous les détails
            System.out.println("9. Mariages détaillés de Said SAFI:");
            hommeService.afficherMariagesDetail(hommes[0].getId());
            System.out.println();
            
            System.out.println("=== FIN DES TESTS ===");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
