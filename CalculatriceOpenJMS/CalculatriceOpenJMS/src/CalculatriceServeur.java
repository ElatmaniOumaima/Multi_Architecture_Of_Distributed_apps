import java.util.Scanner;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class CalculatriceServeur {

    private CalculatriceService calculatriceService;

    public static void main(String[] args) {
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = null;
        String destName = "queue1";
        Destination dest = null;
        Session session = null;
        MessageConsumer receiver = null;            

        try {
            // Créer le contexte JNDI
            context = new InitialContext();

            // Rechercher la ConnectionFactory
            factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            // Rechercher la Destination
            dest = (Destination) context.lookup("queue1");
            // Créer la connexion
            connection = factory.createConnection();
            
         // Ajouter un ExceptionListener à la connexion pour détecter les erreurs de connexion
            connection.setExceptionListener(new ExceptionListener() {
                @Override
                public void onException(JMSException ex) {
                    System.out.println("Erreur de connexion au serveur : " + ex.getMessage());
                }
            });

            // Créer la session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Créer le récepteur
            receiver = session.createConsumer(dest);

            // Démarrer la connexion pour permettre la réception de messages
            connection.start();
         // Envoyer un message de confirmation de connexion
            System.out.println("Le serveur de calculatrice est connecté et prêt à recevoir des opérations.");

            // Créer une instance de CalculatriceService pour traiter les opérations
            CalculatriceService calculatriceService = new CalculatriceImpl();

            while (true) {
                Message message = receiver.receive();
                if (message instanceof TextMessage) {
                    TextMessage text = (TextMessage) message;
                    String[] parts = text.getText().split(" ");
                    String operation = parts[0];
                    
                    float result = 0;

                 // Exécuter l'opération demandée
                    switch (operation) {
                        case "additionner":
                        case "soustraire":
                        case "multiplier":
                        case "diviser":
                            float a1 = Float.parseFloat(parts[1]);
                            float a2 = Float.parseFloat(parts[2]);
                            switch (operation) {
                                case "additionner":
                                    result = calculatriceService.additionner(a1, a2);
                                    break;
                                case "soustraire":
                                    result = calculatriceService.soustraire(a1, a2);
                                    break;
                                case "multiplier":
                                    result = calculatriceService.multiplier(a1, a2);
                                    break;
                                case "diviser":
                                    result = calculatriceService.diviser(a1, a2);
                                    break;
                            }
                            break;
                        case "racine":
                            float a = Float.parseFloat(parts[1]);
                            result = calculatriceService.racine(a);
                            break;
                        case "carre":
                            float b = Float.parseFloat(parts[1]);
                            result = calculatriceService.carre(b);
                            break;
                        case "remiseAzero":
                                calculatriceService.remiseAzero();
                                System.out.println("Opération 'remiseAzero' exécutée.");
                                break;
                        case "extraireDeLaMemoire":
                                calculatriceService.extraireDeLaMemoire();
                                System.out.println("Opération 'extraireDeLaMemoire' exécutée.");
                                break;
                        case "memoriserDernierResultat":
                                calculatriceService.memoriserDernierResultat();
                                System.out.println("Opération 'memoriserDernierResultat' exécutée.");
                                break;
                        default:
                            System.out.println("Opération non reconnue : " + operation);
                            break;
                    }
                    // Envoyer la réponse
                    System.out.println("Résultat de l'opération: " + result);
                } else if (message != null) {
                    System.out.println("Received non text message");
                }
               
            }
        } catch (JMSException | NamingException exception) {
            exception.printStackTrace();
        } finally {
            // Fermer la connexion
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
}