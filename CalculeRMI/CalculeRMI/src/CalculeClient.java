import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CalculeClient {
    private static Scanner scan = null;
    static CalculeInterface calculatrice1;
    static CalculeInterface calculatrice2;

    public static void main(String[] args) {
        try {
            // Obtention de la référence du registre RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Récupération du stub de l'objet distant
            calculatrice1 = (CalculeInterface) registry.lookup("calc1");
            calculatrice2 = (CalculeInterface) registry.lookup("calc2");

            // Code client
            scan = new Scanner(System.in);

            while (true) {
                afficherMenu();
                int choix = scan.nextInt();

                switch (choix) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        float num1 = demanderNombre("premier");
                        float num2 = demanderNombre("deuxième");
                        float resultat = effectuerOperation(choix, num1, num2);
                        System.out.println("Résultat : " + resultat);
                        break;
                    case 5:
                    case 6:
                        float num = demanderNombre("");
                        float res = effectuerOperation(choix, num, 0); // Le deuxième nombre est ignoré
                        System.out.println("Résultat : " + res);
                        break;
                    case 7:
                        calculatrice1.RemiseAzero();
                        System.out.println("Mémoire remise à zéro.");
                        break;
                    case 8:
                        System.out.println("Entrez le résultat à mémoriser : ");
                        float resultatAMemoriser = scan.nextFloat();
                        calculatrice1.MemoriserDernierResultat();
                        System.out.println("Dernier résultat mémorisé : " + resultatAMemoriser);
                        break;
                    case 9:
                        float resultatExtrait = calculatrice1.ExtraireDeLaMemoire();
                        System.out.println("Dernier résultat extrait de la mémoire : " + resultatExtrait);
                        break;
                    case 0:
                        System.out.println("Fin du programme.");
                        System.exit(0);
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
        } finally {
            if (scan != null) {
                scan.close();
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
                return calculatrice1.Additionner(num1, num2);
            case 2:
                return calculatrice1.Soustraire(num1, num2);
            case 3:
                return calculatrice1.Multiplier(num1, num2);
            case 4:
                return calculatrice1.Diviser(num1, num2);
            case 5:
                return calculatrice1.Racine(num1);
            case 6:
                return calculatrice1.Carre(num1);
            default:
                throw new IllegalArgumentException("Opération non valide : " + choix);
        }
    }
}
