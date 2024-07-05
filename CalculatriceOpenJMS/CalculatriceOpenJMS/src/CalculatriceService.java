import javax.ejb.Remote;


public interface CalculatriceService {
    float additionner(float a1, float a2);
    float soustraire(float a1, float a2);
    float multiplier(float a1, float a2);
    float diviser(float a1, float a2);
    float racine(float a1);
    float carre(float a1);
    void remiseAzero();
    void memoriserDernierResultat();
    float extraireDeLaMemoire();
}

