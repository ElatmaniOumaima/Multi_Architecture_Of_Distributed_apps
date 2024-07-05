import java.rmi.RemoteException;

public class CalculeImplement implements CalculeInterface {

    private float memoire = 0;
    private float dernierResultat = 0;

    @Override
    public float Additionner(float a1, float a2) throws RemoteException {
        dernierResultat = a1 + a2;
        return dernierResultat;
    }

    @Override
    public float Soustraire(float a1, float a2) throws RemoteException {
        dernierResultat = a1 - a2;
        return dernierResultat;
    }

    @Override
    public float Multiplier(float a1, float a2) throws RemoteException {
        dernierResultat = a1 * a2;
        return dernierResultat;
    }

    @Override
    public float Diviser(float a1, float a2) throws RemoteException {
        if (a2 != 0) {
            dernierResultat = a1 / a2;
        } else {
            throw new RemoteException("Division by zero");
        }
        return dernierResultat;
    }

    @Override
    public float Racine(float a1) throws RemoteException {
        dernierResultat = (float) Math.sqrt(a1);
        return dernierResultat;
    }

    @Override
    public float Carre(float a1) throws RemoteException {
        dernierResultat = a1 * a1;
        return dernierResultat;
    }

    @Override
    public void RemiseAzero() throws RemoteException {
        dernierResultat = 0;
    }

    @Override
    public void MemoriserDernierResultat() throws RemoteException {
        memoire = dernierResultat;
    }

    @Override
    public float ExtraireDeLaMemoire() throws RemoteException {
        return memoire;
    }
}
