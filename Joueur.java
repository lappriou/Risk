/**
 * Created by Ludo on 15/01/2017.
 */
public class Joueur {
    String surname;
    int IDJoueur;
    String Couleur;
    int NbTroupeStock;

    public Joueur(int idJoueur,String surname, String Couleur)
    {
        this.surname = surname;
        this.Couleur = Couleur;
        this.IDJoueur = idJoueur;
        this.NbTroupeStock = 200;
    }

    public void RetirerNbTroupeStock(int nb)
    {
        this.NbTroupeStock -= nb;
    }





}
