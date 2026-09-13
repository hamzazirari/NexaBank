package service;

import model.Compte;
import model.Transaction;

import java.io.FileWriter;
import java.io.IOException;

public class ReleveService {

    public void genererReleve(Compte compte) throws IOException {

        String nomFichier = "releve_" + compte.getNumeroCompte() + ".txt";

        FileWriter writer = new FileWriter(nomFichier);

        writer.write("===== RELEVE BANCAIRE =====\n");
        writer.write("Compte : " + compte.getNumeroCompte() + "\n");
        writer.write("Solde : " + compte.getSolde() + "\n");
        writer.write("\n");

        writer.write("Date | Type | Montant | Compte Source | Compte Destination\n");
        writer.write("----------------------------------------------------------\n");

        for (Transaction transaction : compte.getHistoriqueTransactions()) {

            String source = "N/A";
            String destination = "N/A";

            if (transaction.getCompteSource() != null) {
                source = transaction.getCompteSource().getNumeroCompte();
            }

            if (transaction.getCompteDestination() != null) {
                destination = transaction.getCompteDestination().getNumeroCompte();
            }

            writer.write(
                    transaction.getDate()
                            + " | "
                            + transaction.getType()
                            + " | "
                            + transaction.getMontant()
                            + " | "
                            + source
                            + " | "
                            + destination
                            + "\n"
            );
        }

        writer.close();

        System.out.println("Relevé créé : " + nomFichier);
    }
}