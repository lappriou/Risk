/**
 * Created by Ludo on 15/01/2017.
 */
import java.util.*;
public class Plateau {


    int nombreTerritoire = 42;
    int[][] MatriceVoisin = new int[nombreTerritoire][nombreTerritoire];
    ArrayList<Territoire> ListeTerritoire = new ArrayList<Territoire>();


    public Plateau(){
        MatriceVoisin = GenererMatriceVoisin();
        ListeTerritoire = GenererTerritoire(ListeTerritoire);

    }

    public int[][] GenererMatriceVoisin(){

        int[][] territoire = new int [42][42];
        for(int i = 0; i< territoire.length; i++){
            for(int j = 0; j < territoire[i].length;j++){
                territoire[i][j] = 0;
            }
        }

        //Pays voisins de 0

        territoire = voisinGrille(territoire,0,new int[]{1,2,4,5,6});

        //Pays voisins de 1


        territoire = voisinGrille(territoire,1,new int[]{2,21});



        //Pays voisins de 2

        territoire = voisinGrille(territoire,2,new int[]{3,4});



        //Pays voisins de 3


        territoire = voisinGrille(territoire,3,new int[]{4,26,36,41});



        //Pays voisins de 4


        territoire = voisinGrille(territoire,4,new int[]{5,41});



        //Pays voisins de 5

        territoire = voisinGrille(territoire,5,new int[]{6,41});



        //Pays voisins de 6


        territoire = voisinGrille(territoire,6,new int[]{7,40,8});


        //Pays voisins de 7


        territoire = voisinGrille(territoire,7,new int[]{8,9,12});

        //Pays voisins de 8


        territoire = voisinGrille(territoire,8,new int[]{9,40,39});

        //Pays voisins de 9

        territoire = voisinGrille(territoire,9,new int[]{11,10});


        //Pays voisins de 10


        territoire = voisinGrille(territoire,10,new int[]{11});


        //Pays voisins de 12
        territoire = voisinGrille(territoire,12,new int[]{13,14});

        //Pays voisins de 14

        territoire = voisinGrille(territoire,14,new int[]{15,16});

        //Pays voisins de 15

        territoire = voisinGrille(territoire,15,new int[]{18,17});

        //Pays voisins de 16


        territoire = voisinGrille(territoire,16,new int[]{17});

        //Pays voisins de 17


        territoire = voisinGrille(territoire,12,new int[]{18,19,23});

        //Pays voisins de 18


        territoire = voisinGrille(territoire,18,new int[]{23});

        //Pays voisins de 19

        territoire = voisinGrille(territoire,19,new int[]{20,22});

        //Pays voisins de 20

        territoire = voisinGrille(territoire,20,new int[]{22,21});

        //Pays voisins de 21

        territoire = voisinGrille(territoire,21,new int[]{22});

        //Pays voisins de 22

        territoire = voisinGrille(territoire,22,new int[]{23});

        //Pays voisins de 23

        territoire = voisinGrille(territoire,23,new int[]{24});

        //Pays voisins de 24
        territoire = voisinGrille(territoire,24,new int[]{25});

        //Pays voisins de 25

        territoire = voisinGrille(territoire,25,new int[]{26,27,28});

        //Pays voisins de 26

        territoire = voisinGrille(territoire,26,new int[]{27,36});

        //Pays voisins de 27

        territoire = voisinGrille(territoire,27,new int[]{31,30,28,36,37,29});

        //Pays voisins de 28

        territoire = voisinGrille(territoire,28,new int[]{29});


        //Pays voisins de 29

        territoire = voisinGrille(territoire,29,new int[]{30,32,33});


        //Pays voisins de 30

        territoire = voisinGrille(territoire,30,new int[]{31,32});


        //Pays voisins de 31

        territoire = voisinGrille(territoire,31,new int[]{37});

        //Pays voisins de 32

        territoire = voisinGrille(territoire,32,new int[]{33,34});

        //Pays voisins de 33

        territoire = voisinGrille(territoire,33,new int[]{34});

        //Pays voisins de 34

        territoire = voisinGrille(territoire,34,new int[]{35});

        //Pays voisins de 36

        territoire = voisinGrille(territoire,36,new int[]{37});

        //Pays voisins de 37

        territoire = voisinGrille(territoire,37,new int[]{38,39});

        //Pays voisins de 38

        territoire = voisinGrille(territoire,38,new int[]{39,41});
        //Pays voisins de 39

        territoire = voisinGrille(territoire,39,new int[]{40});
        //Pays voisins de 40

        territoire = voisinGrille(territoire,40,new int[]{41});

        return  territoire;
    }

    public int[][] voisinGrille(int[][] territoire, int pays, int[] voisins){
        for(int i = 0; i < voisins.length;i++){
            territoire[pays][voisins[i]] = 1;
            territoire[voisins[i]][pays] = 1;
        }
        return territoire;
    }

    public ArrayList<Territoire> GenererTerritoire(ArrayList<Territoire> ListeTerritoire){

        ArrayList<Territoire> territoires = ListeTerritoire;
        for(int i = 0; i< nombreTerritoire; i++){
            territoires.add(new Territoire(i));
        }


        return territoires;

    }


    public ArrayList<Territoire> AttributionTerritoire(ArrayList<Territoire> Listterritoires,ArrayList<Joueur> joueurs){

        int nbJoueurs = joueurs.size();
        ArrayList<Territoire> territoires = Listterritoires;
        int IDJoueur = 0;
        int IDTerritoire;
        ArrayList<Integer> shuffle = new ArrayList<Integer>(territoires.size());

        for(int i = 0; i < territoires.size(); i++)
        {
            shuffle.add(i);
        }
        Collections.shuffle(shuffle);
        System.out.println("TestAttribution");
        for(int i = 0; i< territoires.size(); i++){
            IDTerritoire = shuffle.get(i);

            territoires.get(IDTerritoire).SetJoueur(joueurs.get(IDJoueur));
            if(IDJoueur < nbJoueurs - 1) {
                IDJoueur++;
            }
            else {
                IDJoueur = 0;
            }
            }


        return territoires;




    }


    public ArrayList<Territoire> ListeTerritoirePourUnJoueur( ArrayList<Territoire> territoires, Joueur joueur){

        ArrayList<Territoire> territoireJoueur = new ArrayList<Territoire>();

        for(int i = 0; i < territoires.size(); i++){
            if(territoires.get(i).Roi.IDJoueur == joueur.IDJoueur){
                territoireJoueur.add(territoires.get(i));
            }


        }
        return territoireJoueur;

    }

    public ArrayList<Territoire> ListeTerritoirePourUnJoueurAvecVoisins( ArrayList<Territoire> territoires, Joueur joueur){

        ArrayList<Territoire> territoireJoueur =  ListeTerritoirePourUnJoueur(territoires,joueur);
        ArrayList<Territoire> territoireJoueurAvecVoisins = new ArrayList<Territoire>();

        return territoireJoueurAvecVoisins;


    }


    public ArrayList<Territoire> GetTerritoireVoisin(Territoire territoire, Joueur joueur){

        ArrayList<Territoire> territoireVoisin = new ArrayList<Territoire>();

        for(int i = 0; i< MatriceVoisin.length; i++)
        {
            if(MatriceVoisin[territoire.IDTerritoire][i] == 1){
                territoireVoisin.add(ListeTerritoire.get(i));
            }
        }
        return territoireVoisin;
    }


    public Territoire GetTerritoireParID(int id){

        Territoire result = ListeTerritoire.get(id);
        for(int i = 0; i <ListeTerritoire.size();i++) {
            if(ListeTerritoire.get(i).IDTerritoire == id){
                result = ListeTerritoire.get(i);
            }
        }
        return result;
    }

    public void DeplacerTroupe(Territoire t1, Territoire t2, int nbTroupe){

        t1.SetTroupe(t1.troupe - nbTroupe);
        t2.SetTroupe(t2.troupe + nbTroupe);

    }




}
