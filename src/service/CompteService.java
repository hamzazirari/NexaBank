package service;

import model.Client;
import model.Compte;
import model.TypeCompte;
import exception.CompteInexistantException;

public class CompteService {

    public void creerCompte(Client client, String numeroCompte, TypeCompte typeCompte) {

        if (client.getComptes().containsKey(numeroCompte)) {
            System.out.println("Ce compte existe déjà.");
            return;
        }

        Compte compte = new Compte(numeroCompte, 0, typeCompte);

        client.getComptes().put(numeroCompte, compte);

        System.out.println("Compte créé avec succès.");
    }

    public Compte chercherCompte(Client client, String numeroCompte)
            throws CompteInexistantException {

        Compte compte = client.getComptes().get(numeroCompte);

        if (compte == null) {
            throw new CompteInexistantException(
                    "Le compte " + numeroCompte + " n'existe pas."
            );
        }

        return compte;
    }

    public void modifierCompte(Client client, String numeroCompte,
                               TypeCompte nouveauType)
            throws CompteInexistantException {

        Compte compte = chercherCompte(client, numeroCompte);

        compte.setTypeCompte(nouveauType);

        System.out.println("Compte modifié avec succès.");
    }

    public void cloturerCompte(Client client, String numeroCompte)
            throws CompteInexistantException {

        Compte compte = chercherCompte(client, numeroCompte);

        client.getComptes().remove(numeroCompte);

        System.out.println("Compte " + compte.getNumeroCompte()
                + " clôturé avec succès.");
    }
}