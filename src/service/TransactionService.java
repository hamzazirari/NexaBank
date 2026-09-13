package service;

import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;
import model.Compte;
import model.Transaction;
import model.TypeTransaction;

import java.time.LocalDateTime;

public class TransactionService {

    // Ce compteur sert à générer des identifiants uniques
    // "static" = une seule valeur partagée par toute la classe
    private static int compteurId = 1;

    public void deposer(Compte compte, double montant)
            throws MontantInvalideException {

        // Étape 1 : valider le montant
        if (montant <= 0) {
            throw new MontantInvalideException(
                    "Le montant doit être supérieur à zéro."
            );
        }

        // Étape 2 : mettre à jour le solde
        compte.setSolde(compte.getSolde() + montant);

        // Étape 3 : créer la transaction (compteSource = null, car l'argent arrive de l'extérieur)
        Transaction transaction = new Transaction(
                genererId(),
                TypeTransaction.DEPOT,
                montant,
                LocalDateTime.now(),
                null,
                compte
        );

        // Étape 4 : enregistrer la transaction dans l'historique du compte
        compte.ajouterTransaction(transaction);

        System.out.println("Dépôt effectué avec succès.");
    }

    public void retirer(Compte compte, double montant)
            throws MontantInvalideException, SoldeInsuffisantException {

        // Étape 1 : valider le montant
        if (montant <= 0) {
            throw new MontantInvalideException(
                    "Le montant doit être supérieur à zéro."
            );
        }

        // Étape 2 : vérifier que le solde est suffisant
        if (montant > compte.getSolde()) {
            throw new SoldeInsuffisantException(
                    "Solde insuffisant."
            );
        }

        // Étape 3 : mettre à jour le solde
        compte.setSolde(compte.getSolde() - montant);

        // Étape 4 : créer la transaction (compteDestination = null, car l'argent sort vers l'extérieur)
        Transaction transaction = new Transaction(
                genererId(),
                TypeTransaction.RETRAIT,
                montant,
                LocalDateTime.now(),
                compte,
                null
        );

        // Étape 5 : enregistrer la transaction
        compte.ajouterTransaction(transaction);

        System.out.println("Retrait effectué avec succès.");
    }

    public void virer(Compte compteSource, Compte compteDestination, double montant)
            throws MontantInvalideException, SoldeInsuffisantException {

        // Étape 1 : valider le montant
        if (montant <= 0) {
            throw new MontantInvalideException(
                    "Le montant doit être supérieur à zéro."
            );
        }

        // Étape 2 : vérifier le solde du compte source
        if (montant > compteSource.getSolde()) {
            throw new SoldeInsuffisantException(
                    "Solde insuffisant sur le compte source."
            );
        }

        // Étape 3 : débiter le compte source
        compteSource.setSolde(compteSource.getSolde() - montant);

        // Étape 4 : créditer le compte destination
        compteDestination.setSolde(compteDestination.getSolde() + montant);

        // Étape 5 : créer UNE SEULE transaction pour représenter le virement
        Transaction transaction = new Transaction(
                genererId(),
                TypeTransaction.VIREMENT,
                montant,
                LocalDateTime.now(),
                compteSource,
                compteDestination
        );

        // Étape 6 : l'enregistrer des deux côtés (source ET destination)
        compteSource.ajouterTransaction(transaction);
        compteDestination.ajouterTransaction(transaction);

        System.out.println("Virement effectué avec succès.");
    }

    // Génère un identifiant unique à chaque appel : 1, puis 2, puis 3...
    private int genererId() {
        return compteurId++;
    }
}