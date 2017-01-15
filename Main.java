import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Joueur[] ListeJoueur = chargerJoueurConsole();
        Plateau plateau = new Plateau();

        plateau.AttributionTerritoire(plateau.ListeTerritoire, ListeJoueur);




    }





    public static Joueur[] chargerJoueurConsole()
    {
        Scanner sc = new Scanner(System.in);


        System.out.println("Selectionner le nombre de joueur entre 2 et 6");
        String str = sc.nextLine();

        int nombreJoueur = Integer.parseInt(str);
        Joueur[] joueurs = new Joueur[nombreJoueur];
        for (int i = 0; i < nombreJoueur; i++) {
            System.out.println("Joueur" + i);
            System.out.println("Surnom");
            String strSurnom = sc.nextLine();
            System.out.println("Couleur");
            String strCouleur = sc.nextLine();

            joueurs[i] = new Joueur(strSurnom,strCouleur);
        }

        return joueurs;
    }
}
