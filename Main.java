import java.util.ArrayList;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        ArrayList<Joueur> ListeJoueur = chargerJoueurConsole();
        Plateau plateau = new Plateau();

        Territoire territoireAttaque;
        Territoire territoiredefendu;

        Joueur Attaquant;

        Joueur Defenseur;

        int nextJoueur = 0;

        plateau.AttributionTerritoire(plateau.ListeTerritoire, ListeJoueur);


        while(ListeJoueur.size() > 1){

            Attaquant = ListeJoueur.get(nextJoueur);



            nextJoueur ++;


        }


    }





    public static ArrayList<Joueur> chargerJoueurConsole()
    {
        Scanner sc = new Scanner(System.in);


        System.out.println("Selectionner le nombre de joueur entre 2 et 6");
        String str = sc.nextLine();

        int nombreJoueur = Integer.parseInt(str);
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
        for (int i = 0; i < nombreJoueur; i++) {
            System.out.println("Joueur" + i);
            System.out.println("Surnom");
            String strSurnom = sc.nextLine();
            System.out.println("Couleur");
            String strCouleur = sc.nextLine();

            joueurs.add(new Joueur(i,strSurnom,strCouleur));
        }

        return joueurs;
    }
}
