import java.rmi.*;
public interface CalculeInterface extends Remote {
	public float Additionner (float a1,float a2)  throws RemoteException;
	public float Soustraire (float a1,float a2) throws RemoteException;
	public float Multiplier (float a1,float a2) throws RemoteException;
	public float Diviser (float a1,float a2)throws RemoteException;
	public float Racine (float a1) throws RemoteException;
	public float Carre (float a1) throws RemoteException;
	public void RemiseAzero () throws RemoteException;
	public void MemoriserDernierResultat() throws RemoteException;
	public float ExtraireDeLaMemoire() throws RemoteException;
	

}
