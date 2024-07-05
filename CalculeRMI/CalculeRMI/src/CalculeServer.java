import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculeServer {

    public static void main(String[] args) {
        try {
            System.out.println("Le serveur démarre....");
            System.setProperty("java.rmi.server.hostname", "localhost");

            // Create remote objects
            CalculeImplement c1 = new CalculeImplement();
            CalculeImplement c2 = new CalculeImplement();

            // Export the objects and associate them with a skeleton
            CalculeInterface skeleton1 = (CalculeInterface) UnicastRemoteObject.exportObject(c1, 0);
            CalculeInterface skeleton2 = (CalculeInterface) UnicastRemoteObject.exportObject(c2, 0);

            // Get a registry locally on the host and the listening port passed as a parameter
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Bind the exported objects in the RMI registry so that the client can look them up and use their methods
            registry.rebind("calc1", skeleton1);
            registry.rebind("calc2", skeleton2);

            System.out.println("Serveur prêt.");

        } catch (RemoteException e) {
            System.out.println("Erreur serveur : " + e);
        }
    }
}
