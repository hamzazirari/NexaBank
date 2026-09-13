import model.Client;
import model.Gestionnaire;
import service.AuthentificationService;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Gestionnaire> gestionnaires = new ArrayList<>();

        // Client de test
        Client client = new Client(
                1,
                "Dupont",
                "Jean",
                "jean@gmail.com",
                "1234"
        );

        clients.add(client);

        // Gestionnaire de test
        Gestionnaire gestionnaire = new Gestionnaire(
                1,
                "Admin",
                "Nexa",
                "admin@nexabank.com",
                "admin123"
        );

        gestionnaires.add(gestionnaire);

        AuthentificationService authentificationService =
                new AuthentificationService();

        boolean continuer = true;

        while (continuer) {

            System.out.println("\n===== NEXABANK =====");
            System.out.println("1. Connexion Client");
            System.out.println("2. Connexion Gestionnaire");
            System.out.println("3. Quitter");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {

                case 1:
                    connexionClient(
                            scanner,
                            clients,
                            authentificationService
                    );
                    break;

                case 2:
                    connexionGestionnaire(
                            scanner,
                            gestionnaires,
                            authentificationService
                    );
                    break;

                case 3:
                    continuer = false;
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }

        scanner.close();
    }

    private static void connexionClient(
            Scanner scanner,
            ArrayList<Client> clients,
            AuthentificationService authentificationService) {

        System.out.println("\n===== CONNEXION CLIENT =====");

        System.out.print("Email : ");
        String email = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String motDePasse = scanner.nextLine();

        Client client = authentificationService.authentifierClient(
                clients,
                email,
                motDePasse
        );

        if (client != null) {
            System.out.println(
                    "Bienvenue " + client.getPrenom() + " "
                            + client.getNom() + " !"
            );

            menuClient(scanner, client);
        } else {
            System.out.println("Email ou mot de passe incorrect.");
        }
    }

    private static void connexionGestionnaire(
            Scanner scanner,
            ArrayList<Gestionnaire> gestionnaires,
            AuthentificationService authentificationService) {

        System.out.println("\n===== CONNEXION GESTIONNAIRE =====");

        System.out.print("Email : ");
        String email = scanner.nextLine();

        System.out.print("Mot de passe : ");
        String motDePasse = scanner.nextLine();

        Gestionnaire gestionnaire =
                authentificationService.authentifierGestionnaire(
                        gestionnaires,
                        email,
                        motDePasse
                );

        if (gestionnaire != null) {
            System.out.println(
                    "Bienvenue " + gestionnaire.getPrenom() + " "
                            + gestionnaire.getNom() + " !"
            );

            menuGestionnaire(scanner);
        } else {
            System.out.println("Email ou mot de passe incorrect.");
        }
    }

    private static void menuClient(
            Scanner scanner,
            Client client) {

        boolean continuer = true;

        while (continuer) {

            System.out.println("\n===== MENU CLIENT =====");
            System.out.println("1. Consulter mes comptes");
            System.out.println("2. Déposer");
            System.out.println("3. Retirer");
            System.out.println("4. Effectuer un virement");
            System.out.println("5. Consulter mon relevé");
            System.out.println("6. Déconnexion");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {

                case 1:
                    System.out.println(
                            "Nombre de comptes : "
                                    + client.getComptes().size()
                    );
                    break;

                case 2:
                    System.out.println("Dépôt : fonctionnalité à connecter.");
                    break;

                case 3:
                    System.out.println("Retrait : fonctionnalité à connecter.");
                    break;

                case 4:
                    System.out.println(
                            "Virement : fonctionnalité à connecter."
                    );
                    break;

                case 5:
                    System.out.println(
                            "Relevé : fonctionnalité à connecter."
                    );
                    break;

                case 6:
                    continuer = false;
                    System.out.println("Déconnexion...");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void menuGestionnaire(Scanner scanner) {

        boolean continuer = true;

        while (continuer) {

            System.out.println("\n===== MENU GESTIONNAIRE =====");
            System.out.println("1. Créer un compte");
            System.out.println("2. Modifier un compte");
            System.out.println("3. Clôturer un compte");
            System.out.println("4. Consulter les clients");
            System.out.println("5. Déconnexion");
            System.out.print("Votre choix : ");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {

                case 1:
                    System.out.println(
                            "Création : fonctionnalité à connecter."
                    );
                    break;

                case 2:
                    System.out.println(
                            "Modification : fonctionnalité à connecter."
                    );
                    break;

                case 3:
                    System.out.println(
                            "Clôture : fonctionnalité à connecter."
                    );
                    break;

                case 4:
                    System.out.println(
                            "Consultation des clients : fonctionnalité à connecter."
                    );
                    break;

                case 5:
                    continuer = false;
                    System.out.println("Déconnexion...");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
}
