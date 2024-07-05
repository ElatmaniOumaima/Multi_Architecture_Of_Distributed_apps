import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Scanner;

public class CalculatriceClient {
    private static Scanner scan;
    static CalculatriceImpl calculatriceImpl;

    public static void main(final String[] args) {
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        Destination destination = null;
        Session session = null;
        MessageProducer sender = null;

        try {
            // Créer le contexte JNDI
            context = new InitialContext();

            // Rechercher la factory de connexion JMS
            factory = (ConnectionFactory) context.lookup("ConnectionFactory");

            // Rechercher la destination
            destination = (Destination) context.lookup("queue1");

            // Créer la connexion JMS
            connection = factory.createConnection();

            // Créer la session JMS
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Créer le producteur de messages
            sender = session.createProducer(destination);

            // Démarrer la connexion JMS
            connection.start();

            // Créer une instance de CalculatriceService pour traiter les opérations
            calculatriceImpl = new CalculatriceImpl();

            // Initialiser l'objet Scanner
            scan = new Scanner(System.in);

            while (true) {
                afficherMenu();
                int choix = scan.nextInt();

                switch (choix) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        // Effectuer les opérations localement
                        float num1 = demanderNombre("premier");
                        float num2 = (choix >= 1 && choix <= 4) ? demanderNombre("deuxième") : 0;
                        float resultat = effectuerOperation(choix, num1, num2);
                        System.out.println("Résultat : " + resultat);
                        break;
                    case 0:
                        System.out.println("Fin du programme.");
                        System.exit(0);
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources
            if (context != null) {
                try {
                    context.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("### MENU ###");
        System.out.println("1. Addition");
        System.out.println("2. Soustraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println("5. Racine carrée");
        System.out.println("6. Carré");
        System.out.println("7. Remise à zéro");
        System.out.println("8. Mémoriser dernier résultat");
        System.out.println("9. Extraire de la mémoire");
        System.out.println("0. Quitter");
        System.out.print("Entrez votre choix : ");
    }

    private static float demanderNombre(String ordre) {
        System.out.print("Entrez le " + ordre + " nombre : ");
        return scan.nextFloat();
    }

    private static float effectuerOperation(int choix, float num1, float num2) throws Exception {
        switch (choix) {
            case 1:
                return calculatriceImpl.additionner(num1, num2);
            case 2:
                return calculatriceImpl.soustraire(num1, num2);
            case 3:
                return calculatriceImpl.multiplier(num1, num2);
            case 4:
                return calculatriceImpl.diviser(num1, num2);
            case 5:
                return calculatriceImpl.racine(num1);
            case 6:
                return calculatriceImpl.carre(num1);
            case 7:
                calculatriceImpl.remiseAzero();
                System.out.println("Mémoire remise à zéro.");
                return Float.NaN; // Retourne une valeur non numérique
            case 8:
                System.out.println("Entrez le résultat à mémoriser : ");
                float resultatAMemoriser = scan.nextFloat();
                calculatriceImpl.memoriserDernierResultat();
                System.out.println("Dernier résultat mémorisé : " + resultatAMemoriser);
                return resultatAMemoriser;
            case 9:
                float resultatExtrait = calculatriceImpl.extraireDeLaMemoire();
                System.out.println("Dernier résultat extrait de la mémoire : " + resultatExtrait);
                return resultatExtrait;
            default:
                throw new IllegalArgumentException("Opération non valide : " + choix);
        }
    }
}
