package service;

import exception.MontantInvalideException;
import exception.SoldeInsuffisantException;
import model.Compte;
import model.Transaction;
import model.TypeTransaction;

import java.time.LocalDateTime;

public class TransactionService {


    private static int compteurId = 1;

    public void deposer(Compte compte, double montant)
            throws MontantInvalideException {

        if (montant <= 0) {
            throw new MontantInvalideException(
                    "Le montant doit être supérieur à zéro."
            );
        }

        compte.setSolde(compte.getSolde() + montant);

        Transaction transaction = new Transaction(
                genererId(),
                TypeTransaction.DEPOT,
                montant,
                LocalDateTime.now(),
                null,
                compte
        );

        compte.ajouterTransaction(transaction);

        System.out.println("Dépôt effectué avec succès.");
    }

    public void retirer(Compte compte, double montant)
            throws MontantInvalideException, SoldeInsuffisantException {

        if (montant <= 0) {
            throw new MontantInvalideException(
                    "Le montant doit être supérieur à zéro."
            );
        }

        if (montant > compte.getSolde()) {
            throw new SoldeInsuffisantException(
                    "Solde insuffisant."
            );
        }

        compte.setSolde(compte.getSolde() - montant);

        Transaction transaction = new Transaction(
                genererId(),
                TypeTransaction.RETRAIT,
                montant,
                LocalDateTime.now(),
                compte,
                null
        );

        compte.ajouterTransaction(transaction);

        System.out.println("Retrait effectué avec succès.");
    }

    public void virer(Compte compteSource, Compte compteDestination, double montant)
            throws MontantInvalideException, SoldeInsuffisantException {

        if (montant <= 0) {
            throw new MontantInvalideException(
                    "Le montant doit être supérieur à zéro."
            );
        }

        if (montant > compteSource.getSolde()) {
            throw new SoldeInsuffisantException(
                    "Solde insuffisant sur le compte source."
            );
        }

        compteSource.setSolde(compteSource.getSolde() - montant);

        compteDestination.setSolde(compteDestination.getSolde() + montant);

        Transaction transaction = new Transaction(
                genererId(),
                TypeTransaction.VIREMENT,
                montant,
                LocalDateTime.now(),
                compteSource,
                compteDestination
        );

        compteSource.ajouterTransaction(transaction);
        compteDestination.ajouterTransaction(transaction);

        System.out.println("Virement effectué avec succès.");
    }

    private int genererId() {
        return compteurId++;
    }
}