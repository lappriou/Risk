/**
 * Created by Ludo on 15/01/2017.
 */
public class Territoire {
    String Nom;
    Joueur Roi;
    int IDTerritoire;
    int troupe;

    public Territoire(int idTerritoire){
        this.IDTerritoire = idTerritoire;
        this.troupe = 0;
    }

    public void SetJoueur(Joueur newJoueur){
        this.Roi = newJoueur;
    }

    public void SetTroupe(int newNumberTroupe){
        this.troupe = newNumberTroupe;
    }

}
