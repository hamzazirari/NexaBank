import model.Client;
import model.Compte;
import model.Gestionnaire;
import model.TypeCompte;
import service.AuthentificationService;
import service.CompteService;
import exception.CompteInexistantException;
import model.Compte;
import service.TransactionService;
import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Gestionnaire> gestionnaires = new ArrayList<>();

        // Client  test
        Client client = new Client(
                1,
                "testclient",
                "client",
                "testclient@gmail.com",
                "1234"
        );

        clients.add(client);

        // Gestionnaire test
        Gestionnaire gestionnaire = new Gestionnaire(
                1,
                "Admin",
                "test",
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
                            clients,
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
            ArrayList<Client> clients,
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

            menuGestionnaire(scanner, clients);
        } else {
            System.out.println("Email ou mot de passe incorrect.");
        }
    }

    private static void menuClient(Scanner scanner, Client client) {

        TransactionService transactionService = new TransactionService();

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
                    System.out.println("Nombre de comptes : " + client.getComptes().size());
                    for (Compte c : client.getComptes().values()) {
                        System.out.println(
                                c.getNumeroCompte() + " | " + c.getTypeCompte()
                                        + " | Solde : " + c.getSolde() + " €"
                        );
                    }
                    break;

                case 2: {
                    System.out.print("Numéro du compte : ");
                    String numeroCompte = scanner.nextLine();

                    Compte compte = client.getComptes().get(numeroCompte);

                    if (compte == null) {
                        System.out.println("Erreur : ce compte n'existe pas.");
                        break;
                    }

                    System.out.print("Montant à déposer : ");
                    double montant = scanner.nextDouble();
                    scanner.nextLine();

                    try {
                        transactionService.deposer(compte, montant);
                    } catch (MontantInvalideException e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                }

                case 3: {
                    System.out.print("Numéro du compte : ");
                    String numeroCompte = scanner.nextLine();

                    Compte compte = client.getComptes().get(numeroCompte);

                    if (compte == null) {
                        System.out.println("Erreur : ce compte n'existe pas.");
                        break;
                    }

                    System.out.print("Montant à retirer : ");
                    double montant = scanner.nextDouble();
                    scanner.nextLine();

                    try {
                        transactionService.retirer(compte, montant);
                    } catch (MontantInvalideException | SoldeInsuffisantException e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                }

                case 4: {
                    System.out.print("Numéro du compte source : ");
                    String numeroSource = scanner.nextLine();

                    Compte compteSource = client.getComptes().get(numeroSource);

                    if (compteSource == null) {
                        System.out.println("Erreur : ce compte source n'existe pas.");
                        break;
                    }

                    System.out.print("Numéro du compte destination : ");
                    String numeroDestination = scanner.nextLine();

                    Compte compteDestination = client.getComptes().get(numeroDestination);

                    if (compteDestination == null) {
                        System.out.println("Erreur : ce compte destination n'existe pas.");
                        break;
                    }

                    System.out.print("Montant à virer : ");
                    double montant = scanner.nextDouble();
                    scanner.nextLine();

                    try {
                        transactionService.virer(compteSource, compteDestination, montant);
                    } catch (MontantInvalideException | SoldeInsuffisantException e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                }
                case 5:

                case 6:
                    continuer = false;
                    System.out.println("Déconnexion...");
                    break;

                default:
                    System.out.println("Choix invalide.");
            }
        }
    }

    private static void menuGestionnaire(Scanner scanner, ArrayList<Client> clients) {

        CompteService compteService = new CompteService();

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

                case 1: {
                    Client c = trouverClientParId(scanner, clients);

                    if (c == null) break;

                    System.out.print("Numéro du nouveau compte : ");
                    String numeroCompte = scanner.nextLine();

                    System.out.println("Type de compte (1=COURANT, 2=EPARGNE) : ");
                    int typeChoix = scanner.nextInt();
                    scanner.nextLine();

                    TypeCompte type = (typeChoix == 2)
                            ? TypeCompte.EPARGNE
                            : TypeCompte.COURANT;

                    compteService.creerCompte(c, numeroCompte, type);
                    break;
                }

                case 2: {
                    Client c = trouverClientParId(scanner, clients);

                    if (c == null) break;

                    System.out.print("Numéro du compte à modifier : ");
                    String numeroCompte = scanner.nextLine();

                    System.out.println("Nouveau type (1=COURANT, 2=EPARGNE) : ");
                    int typeChoix = scanner.nextInt();
                    scanner.nextLine();

                    TypeCompte nouveauType = (typeChoix == 2)
                            ? TypeCompte.EPARGNE
                            : TypeCompte.COURANT;

                    try {
                        compteService.modifierCompte(c, numeroCompte, nouveauType);
                    } catch (CompteInexistantException e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                }

                case 3: {
                    Client c = trouverClientParId(scanner, clients);

                    if (c == null) break;

                    System.out.print("Numéro du compte à clôturer : ");
                    String numeroCompte = scanner.nextLine();

                    try {
                        compteService.cloturerCompte(c, numeroCompte);
                    } catch (CompteInexistantException e) {
                        System.out.println("Erreur : " + e.getMessage());
                    }
                    break;
                }

                case 4:
                    for (Client c : clients) {
                        System.out.println(
                                "ID: " + c.getIdClient()
                                        + " | " + c.getPrenom() + " " + c.getNom()
                                        + " | Comptes : " + c.getComptes().size()
                        );
                    }
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

    private static Client trouverClientParId(Scanner scanner, ArrayList<Client> clients) {

        System.out.print("ID du client : ");
        int idClient = scanner.nextInt();
        scanner.nextLine();

        for (Client c : clients) {
            if (c.getIdClient() == idClient) {
                return c;
            }
        }

        System.out.println("Erreur : aucun client trouvé avec cet ID.");
        return null;
    }
}