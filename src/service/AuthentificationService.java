 package service;

import model.Client;
import model.Gestionnaire;

import java.util.ArrayList;

public class AuthentificationService {

    public Client authentifierClient(
            ArrayList<Client> clients,
            String email,
            String motDePasse) {

        for (Client client : clients) {

            if (client.getEmail().equals(email)
                    && client.getMotDePasse().equals(motDePasse)) {

                return client;
            }
        }

        return null;
    }

    public Gestionnaire authentifierGestionnaire(
            ArrayList<Gestionnaire> gestionnaires,
            String email,
            String motDePasse) {

        for (Gestionnaire gestionnaire : gestionnaires) {

            if (gestionnaire.getEmail().equals(email)
                    && gestionnaire.getMotDePasse().equals(motDePasse)) {

                return gestionnaire;
            }
        }

        return null;
    }
}