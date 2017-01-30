import javafx.scene.control.*;

import javax.naming.ldap.InitialLdapContext;
import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.*;


public class JFramePlateau extends JFrame implements ActionListener {

    static String GaucheTitreString;

    public JButton attaquer = new JButton("Attaquer");


    public JButton deplacer = new JButton("Deplacer");
    public JPanel PanelGauche = new JPanel();

    public JList<Territoire> JListTerritoireDepart = new JList<>();
    public JList<Territoire> JListTerritoireFin = new JList<>();
    public JPanel PanelDroite = new JPanel();
    public JPanel PanelDroiteTitre = new JPanel();
    public JLabel LabelDroiteTitre = new JLabel();
    public JButton BoutonContinuerAttaque = new JButton("Continuer");
    public JButton BoutonRepliAttaque = new JButton("Repli");

    public JLabel InformationAttaquant = new JLabel();
    public JLabel InformationDefenseur = new JLabel();
    public JPanel PanelGaucheAction = new JPanel();
    public JTextField SelectionNbJoueurs = new JTextField(50);

    public JButton ValiderTerritoireDepart = new JButton("Valider");
    public JButton ValiderTerritoireFin = new JButton("Valider");
    public JButton ValiderNbTroupe = new JButton("Valider");
    JPanel PanelGaucheButtonChoix = new JPanel();
    ImageIcon ImgPlateau = new ImageIcon("src\\risk_id.jpg");
    JLabel label = new JLabel(ImgPlateau);
    JButton BoutonFinDeTour = new JButton("Fin de tour");
    boolean EstUneAttaque = false ;




    public JFramePlateau(){
        super();


    }

    public void build(){

        attaquer.addActionListener(this);
        deplacer.addActionListener(this);
        ValiderTerritoireDepart.addActionListener(this);
        ValiderTerritoireFin.addActionListener(this);
        ValiderNbTroupe.addActionListener(this);
        BoutonContinuerAttaque.addActionListener(this);
        BoutonRepliAttaque.addActionListener(this);
        BoutonFinDeTour.addActionListener(this);
        // Construction de l'interface gauche


        this.add(label,BorderLayout.CENTER);
        this.setVisible(true);
        setTitle("RiskRaclette"); //On donne un titre à l'application
        setSize(1900,960); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix


        JPanel PanelGaucheTitre = new JPanel();

        JLabel GaucheTitre= new JLabel(GaucheTitreString);

        PanelGaucheTitre.setSize(60,20);
        GaucheTitre.setVisible(true);
        PanelGaucheTitre.add(GaucheTitre);
        PanelGaucheTitre.setVisible(true);

        PanelGauche.add(PanelGaucheTitre, BorderLayout.NORTH);
        PanelGaucheButtonChoix.add(attaquer, BorderLayout.WEST);
        PanelGaucheButtonChoix.add(deplacer, BorderLayout.EAST);

        PanelGauche.add(PanelGaucheButtonChoix,BorderLayout.CENTER);

        PanelGauche.setVisible(true);
        this.add(PanelGauche, BorderLayout.NORTH);
    }
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == attaquer){
            buildSelectionTerritoireDepart();
            EstUneAttaque = true;


        }else if(source == deplacer){
            buildSelectionTerritoireDepart();
        }else if(source == ValiderTerritoireDepart && JListTerritoireDepart.isSelectionEmpty() == false){
                Main.territoireAttaque = JListTerritoireDepart.getSelectedValue();
                buildSelectionTerritoireArrive();

        }else if(source == ValiderTerritoireFin && JListTerritoireFin.isSelectionEmpty() == false){
            Main.territoiredefendu = JListTerritoireFin.getSelectedValue();
            buildNbTroupes();
        }
        else if(source == ValiderNbTroupe && tryParseInt(SelectionNbJoueurs.getText())){

            int test = Integer.parseInt(SelectionNbJoueurs.getText());

            if(test < Main.territoireAttaque.troupe - 1) {
                Main.TroupeAttaque = test;
                Main.territoireAttaque.troupe -= test;
                if(EstUneAttaque) {
                    buildCombat();
                }
                else{

                    Main.territoiredefendu.troupe += test;
                    Main.InitialisationTour();
                    this.dispose();

                }


            }



        }
        else if(source == BoutonContinuerAttaque){
            Main.CombatDetail();
            InformationAttaquant.setText("Il te reste "+Main.TroupeAttaque +" troupes");
            InformationDefenseur.setText("Il te reste "+Main.territoiredefendu.troupe +" troupes");


            this.validate();

            if(Main.TroupeAttaque <= 0 || Main.territoiredefendu.troupe <=0){
                BoutonContinuerAttaque.setVisible(false);
                BoutonRepliAttaque.setVisible(false);
                if(Main.TroupeAttaque <= 0) {
                    InformationAttaquant.setText("Vous avez perdu avec disgrace");
                    InformationDefenseur.setText("Vous avez gagnez avec bavoure mais il vous reste "+ Main.territoiredefendu.troupe);
                }
                else{
                    Main.territoiredefendu.Roi = Main.Attaquant;
                    Main.territoiredefendu.troupe = Main.TroupeAttaque;
                    InformationDefenseur.setText("Vous avez perdu avec disgrace");
                    InformationAttaquant.setText("Vous avez gagnez avec bavoure le territoire " + Main.territoiredefendu.IDTerritoire + " et vous disposez sur ce territoire de "+ Main.territoiredefendu.troupe + "troupes");

                    if(Main.GetTerritoireJoueur(Main.Defenseur).size() == 0)
                    {
                        Main.ListeJoueur.remove(Main.Defenseur);
                        InformationDefenseur.setText("Vous avez été exterminer");
                    }

                }

                PanelGaucheAction.add(BoutonFinDeTour);
            }
        }

        else if(source == BoutonFinDeTour){


            Main.InitialisationTour();
            this.dispose();
        }


    }


    public static void setTitrePanelGauche( String gaucheTitre){
        GaucheTitreString = gaucheTitre;
    }



    public void buildSelectionTerritoireDepart(){


        PanelGauche.remove(PanelGaucheButtonChoix);


        JListTerritoireDepart.setBackground(Color.darkGray);
        JListTerritoireDepart = new JList<>(ConvertToListModel(Main.GetTerritoireAttaquant()));

        if(EstUneAttaque) {
            InformationAttaquant.setText("Choisis ton territoire d'attaque");

        }
        else{
            InformationAttaquant.setText("Choisis ton territoire de depart");
        }
        PanelGaucheAction.add(InformationAttaquant);
        PanelGaucheAction.add(JListTerritoireDepart);
        PanelGaucheAction.add(ValiderTerritoireDepart);
        PanelGauche.add(PanelGaucheAction);

        this.validate();
    }

    public void buildSelectionTerritoireArrive(){
        ValiderTerritoireDepart.setVisible(false);
        JListTerritoireDepart.setVisible(false);


        if(EstUneAttaque) {
            InformationAttaquant.setText("Choisis le territoire que tu attaques");
            JListTerritoireFin = new JList<>(ConvertToListModel(Main.GetTerritoireVoisin(Main.territoireAttaque)));
        }
        else{
            JListTerritoireFin = new JList<>(ConvertToListModel(Main.GetTerritoireAttaquant()));
            //JListTerritoireFin.remove(JListTerritoireDepart.getSelectedIndex());
        }
        PanelGaucheAction.add(JListTerritoireFin);
        PanelGaucheAction.add(ValiderTerritoireFin);
        JListTerritoireFin.validate();
        this.validate();
        this.repaint();
    }


    public void buildCombat(){
        Main.Defenseur = Main.territoiredefendu.Roi;

        SelectionNbJoueurs.setVisible(false);
        ValiderNbTroupe.setVisible(false);

        PanelGaucheAction.add(BoutonContinuerAttaque);
        PanelGaucheAction.add(BoutonRepliAttaque);
        LabelDroiteTitre = new JLabel(Main.Defenseur.surname) ;
        LabelDroiteTitre.setVisible(true);
        PanelDroiteTitre.add(LabelDroiteTitre);
        PanelDroiteTitre.setVisible(true);
        InformationAttaquant.setText("Il te reste "+Main.TroupeAttaque +" troupes");
        InformationAttaquant.repaint();
        PanelDroite.add(PanelDroiteTitre,BorderLayout.NORTH);
        InformationDefenseur.setText("Il te reste "+Main.territoiredefendu.troupe +" troupes");
        PanelDroite.add(InformationDefenseur);
        this.add(PanelDroite, BorderLayout.SOUTH);
        PanelDroite.setVisible(true);
        this.validate();

    }

    public void buildNbTroupes(){

        InformationAttaquant.setText("Choisir le nombre de troupe a mobiliser, le max est " + Main.territoireAttaque.troupe);
        ValiderTerritoireFin.setVisible(false);
        JListTerritoireFin.setVisible(false);

        PanelGaucheAction.add(SelectionNbJoueurs);
        PanelGaucheAction.add(ValiderNbTroupe);
        this.validate();

    }


    public DefaultListModel<Territoire> ConvertToListModel(java.util.List<Territoire> listeTerritoire){
        DefaultListModel<Territoire> defaultListeTerritoire= new DefaultListModel<>();

        for(int i = 0; i < listeTerritoire.size();i++)
        {
            defaultListeTerritoire.add(i,listeTerritoire.get(i));
        }

        return defaultListeTerritoire;
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
