import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Couleur> ListeCouleurs = ChargerListeCouleurDepart();
        ArrayList<Joueur> ListeJoueur = chargerJoueurConsole(ListeCouleurs);


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

            System.out.println("C'est au tour de "+Attaquant.surname);
            sc.nextLine();

            ArrayList<Territoire> territoiresDuJoueursAvecVoisins = plateau.ListeTerritoirePourUnJoueur(plateau.ListeTerritoire, Attaquant);
            ArrayList<Territoire> territoireVoisin = new ArrayList<Territoire>();

            System.out.println("Tes territoires");

            for(int i = 0; i < territoiresDuJoueursAvecVoisins.size(); i++){
                System.out.println("    Le territoire: " + territoiresDuJoueursAvecVoisins.get(i).IDTerritoire + ". Il possède "+ territoiresDuJoueursAvecVoisins.get(i).troupe);
                territoireVoisin = plateau.GetTerritoireVoisin(territoiresDuJoueursAvecVoisins.get(i),Attaquant);
                System.out.println("        Ces voisins sont: ");
                for(int o = 0; o < territoireVoisin.size(); o++){
                    System.out.println("            Le territoire: " + territoireVoisin.get(o).IDTerritoire + ". Il possède " + territoireVoisin.get(o).troupe + " et appartient au joueur "+ territoireVoisin.get(o).Roi.surname);
                }


            }

            System.out.println("Que veux tu faire?");
            System.out.println("    0: Attaquer ?");
            System.out.println("    1: Deplacer une troupe ?");
            int choix = Integer.parseInt(sc.nextLine());
            switch(choix){
                case 0:{
                    CombatConsole(plateau,Attaquant);
                }

                case 1:{
                    DeplacementTroupeConsole(plateau, Attaquant);
                }

            }



            if(nextJoueur < ListeJoueur.size()-1) {
                nextJoueur++;
            }
            else{
                nextJoueur = 0;
            }
        }
    }




    public static void DeplacementTroupeConsole(Plateau plateau, Joueur joueur)
    {

        Scanner sc = new Scanner(System.in);


        Territoire TerriDepart;

        Territoire TerriArrive;


        System.out.println("Indique le territoire de depart");

        int str =Integer.parseInt(sc.nextLine()) ;

        TerriDepart = plateau.GetTerritoireParID(str);

        System.out.println("Indique le territoire d'arrivé");

        str =Integer.parseInt(sc.nextLine()) ;

        TerriArrive = plateau.GetTerritoireParID(str);
        System.out.println("Choisis le nombre de troupe que tu veux mettre pour cette attaque sachant que c'est "+ TerriDepart.troupe+ "le max. Le defenseur a " + TerriArrive.troupe);



        int nbTroupeDeplacer = Integer.parseInt(sc.nextLine());

        plateau.DeplacerTroupe(TerriDepart,TerriArrive,nbTroupeDeplacer);
        System.out.println("Le nombre de troupe est maintenant de "+ TerriDepart.troupe+" sur le territoire "+ TerriDepart.IDTerritoire+ ". Et  " + TerriArrive.troupe+" sur le territoire "+ TerriArrive.IDTerritoire);


    }




    public static void CombatConsole(Plateau plateau, Joueur joueur){

        Joueur attaquant = joueur;
        Scanner sc = new Scanner(System.in);
        Joueur defenseur;

        Territoire Terriattaquant;

        Territoire Terridefenseur;


        System.out.println("Indique le territoire avec lequel tu comptes attaqué");

        int str =Integer.parseInt(sc.nextLine()) ;

        Terriattaquant = plateau.GetTerritoireParID(str);

        System.out.println("Met le n° du territoire que tu attaques");

        str =Integer.parseInt(sc.nextLine()) ;

        Terridefenseur = plateau.GetTerritoireParID(str);

        defenseur = Terridefenseur.Roi;
        System.out.println("Choisis le nombre de troupe que tu veux mettre pour cette attaque sachant que c'est "+ Terriattaquant.troupe+ "le max. Le defenseur a " + Terridefenseur.troupe);


        int nbTroupeAttaquant = Integer.parseInt(sc.nextLine());

        Terriattaquant.troupe -= nbTroupeAttaquant;
        System.out.println("Le combat commence");
        nbTroupeAttaquant = CombatDetailConsole(Terriattaquant, Terridefenseur, nbTroupeAttaquant);

        while(Terridefenseur.troupe > 0 && nbTroupeAttaquant > 0) {


            System.out.println("Que veux tu faire?");
            System.out.println("    0: Continuer?");
            System.out.println("    1: Repli ?");
            int choix = Integer.parseInt(sc.nextLine());
            switch (choix){
                case 0:
                    nbTroupeAttaquant = CombatDetailConsole(Terriattaquant, Terridefenseur, nbTroupeAttaquant);
                case 1:{
                    Terriattaquant.troupe += nbTroupeAttaquant;
                    nbTroupeAttaquant = 0;
                }


            }
        }

        if(Terridefenseur.troupe> 0){
            Terridefenseur.SetJoueur(attaquant);
            Terridefenseur.troupe = nbTroupeAttaquant;
            System.out.println("Bravo a" + attaquant.surname +" pour avoir remporter " + Terridefenseur.IDTerritoire+". Il dispose maintenant d'une troupe de " + Terridefenseur.troupe);
        }
        else{
            System.out.println("Bravo a" + defenseur +" pour avoir defendu " + Terridefenseur.IDTerritoire+". Il dispose maintenant d'une troupe de " + Terridefenseur.troupe);
        }


    }

    public static int CombatDetailConsole(Territoire territoireAttaquant, Territoire territoireDefenseur, int nbTroupeAttaquant){
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> DesAttaquant = new ArrayList<Integer>();
        ArrayList<Integer> DesDefenseur = new ArrayList<Integer>();
        Joueur attaquant = territoireAttaquant.Roi;
        Joueur defenseur = territoireDefenseur.Roi;
        System.out.println(" L'attaquant "+ attaquant.surname+" lance les dés et obtiens: ");

        for(int i =0; i<3; i++){
            DesAttaquant.add(LancerDes());

        }
        Collections.sort(DesAttaquant, Collections.reverseOrder());
        System.out.println(DesAttaquant);

        System.out.println(" Le defenseur "+ defenseur.surname+" lance les dés et obtiens: ");
        for(int i =0; i<2; i++){
            DesDefenseur.add(LancerDes());

        }
        Collections.sort(DesDefenseur, Collections.reverseOrder());
        System.out.println(DesDefenseur);
        sc.nextLine();

        if(DesAttaquant.size() > DesDefenseur.size()){
            for(int i = 0; i < DesDefenseur.size(); i++){
                if(DesAttaquant.get(i) > DesDefenseur.get(i)){
                    territoireDefenseur.troupe -= DesAttaquant.get(i) - DesDefenseur.get(i);

                    sc.nextLine();
                }
                else{
                    nbTroupeAttaquant -= DesDefenseur.get(i) - DesAttaquant.get(i);
                }

            }
        }

        else{
            for(int i = 0; i < DesAttaquant.size(); i++){
                if(DesAttaquant.get(i) > DesDefenseur.get(i)){
                    territoireDefenseur.troupe -= DesAttaquant.get(i) - DesDefenseur.get(i);
                }
                else{
                    territoireAttaquant.troupe -= DesDefenseur.get(i) - DesAttaquant.get(i);
                }
            }
        }
        System.out.println("Ce combat fut fatal, il reste "+ territoireAttaquant.troupe+" sur le territoire de " + attaquant.surname);
        System.out.println("Ce combat fut fatal, il reste "+ territoireDefenseur.troupe+" sur le territoire de " + defenseur.surname);
        sc.nextLine();
        return nbTroupeAttaquant;
    }

    public static ArrayList<Couleur> ChargerListeCouleurDepart(){



        ArrayList<Couleur> couleurs = new ArrayList<Couleur>();

        couleurs.add(new Couleur("Abricot","#E67E30"));
        couleurs.add(new Couleur("Absinthe","#7FDD4C"));
        couleurs.add(new Couleur("Acajou","#88421D"));
        couleurs.add(new Couleur("Aigue-marine","#79F8F8"));
        couleurs.add(new Couleur("Aile de corbeau","#000000"));
        couleurs.add(new Couleur("Albâtre","#FEFEFE"));




        return couleurs;

    }
    public static ArrayList<Joueur> chargerJoueurConsole(ArrayList<Couleur> couleurs)
    {
        Scanner sc = new Scanner(System.in);


        System.out.println("Selectionner le nombre de joueur entre 2 à 42");
        String str = sc.nextLine();

        int nombreJoueur = Integer.parseInt(str);
        ArrayList<Couleur> couleursDispo = new ArrayList<Couleur>();
        ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
        for (int i = 0; i < nombreJoueur; i++) {
            System.out.println("    Joueur" + i);
            System.out.println("    Surnom");
            String strSurnom = sc.nextLine();
            //System.out.println("Couleur");

            String strCouleur = "Blue";

            joueurs.add(new Joueur(i,strSurnom,strCouleur));
        }

        return joueurs;
    }


    public static int LancerDes(){
        Random rdm = new Random();

        return rdm.nextInt(6)+1;
    }
    public static ArrayList<Territoire> PlacementDesTroupesConsole(Plateau plateau, ArrayList<Joueur> joueurs)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Placer vos troupes");
        ArrayList<Territoire> territoiresDuJoueur = new ArrayList<Territoire>();
        for(int i = 0; i< joueurs.size(); i++)
        {
            System.out.println("Joueur" + i);
            territoiresDuJoueur = plateau.ListeTerritoirePourUnJoueur(plateau.ListeTerritoire,joueurs.get(i));

            for(int o = 0; o < territoiresDuJoueur.size(); o++){
                System.out.println("Territoire " + territoiresDuJoueur.get(o).IDTerritoire + " Troupe en stock: "+ joueurs.get(i).NbTroupeStock);
                //int nb = Integer.parseInt(sc.nextLine());
                int nb = 10;
                joueurs.get(i).RetirerNbTroupeStock(nb);
                territoiresDuJoueur.get(o).SetTroupe(nb);

            }


        }
        return plateau.ListeTerritoire;
    }
}
