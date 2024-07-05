import javax.ejb.Stateless;


public class CalculatriceImpl implements CalculatriceService {
    private float dernierResultat = 0;

    @Override
    public float additionner(float a1, float a2) {
        dernierResultat = a1 + a2;
        return dernierResultat;
    }

    @Override
    public float soustraire(float a1, float a2) {
        dernierResultat = a1 - a2;
        return dernierResultat;
    }

    @Override
    public float multiplier(float a1, float a2) {
        dernierResultat = a1 * a2;
        return dernierResultat;
    }

    @Override
    public float diviser(float a1, float a2) {
        if (a2 != 0) {
            dernierResultat = a1 / a2;
            return dernierResultat;
        } else {
            throw new IllegalArgumentException("Division par zéro impossible");
        }
    }

    @Override
    public float racine(float a1) {
        dernierResultat = (float) Math.sqrt(a1);
        return dernierResultat;
    }

    @Override
    public float carre(float a1) {
        dernierResultat = a1 * a1;
        return dernierResultat;
    }

    @Override
    public void remiseAzero() {
        dernierResultat = 0;
    }


    @Override
    public float extraireDeLaMemoire() {
        return dernierResultat;
    }

	@Override
	public void memoriserDernierResultat() {
		// TODO Auto-generated method stub
		
	}
}
