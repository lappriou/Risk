import java.util.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;

public class Main {
    static Plateau plateau = Plateau.getInstance();
    static Territoire territoireAttaque;
    static Territoire territoiredefendu;
    static Joueur Attaquant;
    static Joueur Defenseur;
    static ArrayList<Territoire> territoiresDuJoueur;
    static ArrayList<Couleur> ListeCouleurs = ChargerListeCouleurDepart();
    static ArrayList<Joueur> ListeJoueur = chargerJoueurConsole(ListeCouleurs);
    static int nextJoueur = 0;

    static int TroupeAttaque;


    public static void main(String[] args) {



                plateau.AttributionTerritoire(ListeJoueur);
                PlacementDesTroupesConsole(plateau, ListeJoueur);
                InitialisationTour();


    }




    public static void DeplacementTroupeConsole()
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



    public static void InitialisationTour(){
        Attaquant = ListeJoueur.get(nextJoueur);



        JFramePlateau.setTitrePanelGauche(Attaquant.surname);
        JFramePlateau f = new JFramePlateau();
        f.build();
        System.out.println("C'est au tour de "+Attaquant.surname);

        List<Territoire> territoiresDuJoueursAvecVoisins = plateau.ListeTerritoirePourUnJoueur(plateau.ListeTerritoire, Attaquant);
        List<Territoire> territoireVoisin = new ArrayList<Territoire>();

        System.out.println("Tes territoires");

            /*for(int i = 0; i < territoiresDuJoueursAvecVoisins.size(); i++){
                System.out.println("    Le territoire: " + territoiresDuJoueursAvecVoisins.get(i).IDTerritoire + ". Il possède "+ territoiresDuJoueursAvecVoisins.get(i).troupe);
                territoireVoisin = plateau.GetTerritoireVoisin(territoiresDuJoueursAvecVoisins.get(i),Attaquant);
                System.out.println("        Ces voisins sont: ");
                for(int o = 0; o < territoireVoisin.size(); o++){
                    System.out.println("            Le territoire: " + territoireVoisin.get(o).IDTerritoire + ". Il possède " + territoireVoisin.get(o).troupe + " et appartient au joueur "+ territoireVoisin.get(o).Roi.surname);
                }


            }*/

        if(nextJoueur < ListeJoueur.size()-1) {
            nextJoueur++;
        }
        else{
            nextJoueur = 0;
        }


    }

    public static void CombatConsole(){

        Joueur attaquant = Attaquant;
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
        nbTroupeAttaquant = CombatDetailConsole();

        while(Terridefenseur.troupe > 0 && nbTroupeAttaquant > 0) {


            System.out.println("Que veux tu faire?");
            System.out.println("    0: Continuer?");
            System.out.println("    1: Repli ?");
            int choix = Integer.parseInt(sc.nextLine());
            switch (choix){
                case 0:
                    nbTroupeAttaquant = CombatDetailConsole();
                case 1:{
                    Terriattaquant.troupe += nbTroupeAttaquant;
                    nbTroupeAttaquant = 0;
                }


            }
        }

        if(Terridefenseur.troupe<= 0){
            Terridefenseur.SetJoueur(attaquant);
            Terridefenseur.troupe = nbTroupeAttaquant;
            System.out.println("Bravo a" + attaquant.surname +" pour avoir remporter " + Terridefenseur.IDTerritoire+". Il dispose maintenant d'une troupe de " + Terridefenseur.troupe);
        }
        else{
            System.out.println("Bravo a" + defenseur +" pour avoir defendu " + Terridefenseur.IDTerritoire+". Il dispose maintenant d'une troupe de " + Terridefenseur.troupe);
        }


    }

    public static int CombatDetailConsole(){
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> DesAttaquant = new ArrayList<Integer>();
        ArrayList<Integer> DesDefenseur = new ArrayList<Integer>();
        Joueur attaquant = territoireAttaque.Roi;
        Joueur defenseur = territoiredefendu.Roi;
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
                    territoiredefendu.troupe -= DesAttaquant.get(i) - DesDefenseur.get(i);

                    sc.nextLine();
                }
                else{
                    territoireAttaque.troupe -= DesDefenseur.get(i) - DesAttaquant.get(i);
                }

            }
        }

        else{
            for(int i = 0; i < DesAttaquant.size(); i++){
                if(DesAttaquant.get(i) > DesDefenseur.get(i)){
                    territoiredefendu.troupe -= DesAttaquant.get(i) - DesDefenseur.get(i);
                }
                else{
                    territoireAttaque.troupe -= DesDefenseur.get(i) - DesAttaquant.get(i);
                }
            }
        }
        System.out.println("Ce combat fut fatal, il reste "+ territoiredefendu.troupe+" sur le territoire de " + attaquant.surname);
        System.out.println("Ce combat fut fatal, il reste "+ territoiredefendu.troupe+" sur le territoire de " + defenseur.surname);
        sc.nextLine();
        return TroupeAttaque;
    }

    public static void CombatDetail(){
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> DesAttaquant = new ArrayList<Integer>();
        ArrayList<Integer> DesDefenseur = new ArrayList<Integer>();
        Joueur attaquant = territoireAttaque.Roi;
        Joueur defenseur = territoiredefendu.Roi;
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


        if(DesAttaquant.size() > DesDefenseur.size()){
            for(int i = 0; i < DesDefenseur.size(); i++){
                if(DesAttaquant.get(i) > DesDefenseur.get(i)){
                    territoiredefendu.troupe -= DesAttaquant.get(i) - DesDefenseur.get(i);

                }
                else{
                    TroupeAttaque -= DesDefenseur.get(i) - DesAttaquant.get(i);
                }

            }
        }

        else{
            for(int i = 0; i < DesAttaquant.size(); i++){
                if(DesAttaquant.get(i) > DesDefenseur.get(i)){
                    territoiredefendu.troupe -= DesAttaquant.get(i) - DesDefenseur.get(i);
                }
                else{
                    TroupeAttaque -= DesDefenseur.get(i) - DesAttaquant.get(i);
                }
            }
        }
        System.out.println("Ce combat fut fatal, il reste "+ TroupeAttaque+" sur le territoire de " + attaquant.surname);
        System.out.println("Ce combat fut fatal, il reste "+ territoiredefendu.troupe+" sur le territoire de " + defenseur.surname);
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
        List<Territoire> territoiresDuJoueur = new ArrayList<Territoire>();
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



    public static List<Territoire> GetTerritoireAttaquant(){
        return plateau.ListeTerritoirePourUnJoueur(plateau.ListeTerritoire,Attaquant);
    }

    public static List<Territoire> GetTerritoireJoueur(Joueur joueur){
        return plateau.ListeTerritoirePourUnJoueur(plateau.ListeTerritoire,joueur);
    }

    public static List<Territoire> GetTerritoireVoisin(Territoire territoire){
        return plateau.GetTerritoireVoisin(territoire);
    }
}
