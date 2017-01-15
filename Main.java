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

        ArrayList<Territoire> territoiresDuJoueur;
        int nextJoueur = 0;

        plateau.AttributionTerritoire(plateau.ListeTerritoire, ListeJoueur);

        plateau.ListeTerritoire = PlacementDesTroupesConsole(plateau,ListeJoueur);

        while(ListeJoueur.size() > 1){

            Attaquant = ListeJoueur.get(nextJoueur);

            territoiresDuJoueur = plateau.ListeTerritoirePourUnJoueur(plateau.ListeTerritoire, Attaquant.IDJoueur);


            /*A changer */
            territoireAttaque = territoiresDuJoueur.get(1);

            territoiresDuJoueur = plateau.GetTerritoireVoisin(territoireAttaque);

            territoiredefendu = territoiresDuJoueur.get(1);





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

    public static ArrayList<Territoire> PlacementDesTroupesConsole(Plateau plateau, ArrayList<Joueur> joueurs)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Placer vos troupes");
        ArrayList<Territoire> territoiresDuJoueur = new ArrayList<Territoire>();
        for(int i = 0; i< joueurs.size(); i++)
        {
            System.out.println("Joueur" + i);
            territoiresDuJoueur = plateau.ListeTerritoirePourUnJoueur(plateau.ListeTerritoire,joueurs.get(i).IDJoueur);

            for(int o = 0; o < territoiresDuJoueur.size(); o++){
                System.out.println("Territoire " + territoiresDuJoueur.get(o).IDTerritoire);
                territoiresDuJoueur.get(o).SetTroupe(Integer.parseInt(sc.nextLine()));

            }




        }

        return plateau.ListeTerritoire;
    }
}
