import javafx.scene.control.ListView;

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

    public JLabel InformationAttaquant = new JLabel();

    public JPanel PanelGaucheAction = new JPanel();

    public JButton ValiderTerritoireDepart = new JButton("Valider");
    public JButton ValiderTerritoireFin = new JButton("Valider");
    JPanel PanelGaucheButtonChoix = new JPanel();
    ImageIcon ImgPlateau = new ImageIcon("src\\risk_id.jpg");
    JLabel label = new JLabel(ImgPlateau);

    boolean EstUneAttaque = false ;




    public JFramePlateau(){
        super();


    }

    public void build(){

        attaquer.addActionListener(this);
        deplacer.addActionListener(this);
        ValiderTerritoireDepart.addActionListener(this);
        ValiderTerritoireFin.addActionListener(this);
        // Construction de l'interface gauche


        this.add(label,BorderLayout.CENTER);
        this.setVisible(true);
        setTitle("RiskRaclette"); //On donne un titre à l'application
        setSize(1900,1080); //On donne une taille à notre fenêtre
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
        this.add(PanelGauche, BorderLayout.WEST);









    }
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == attaquer){
            buildSelectionTerritoireDepart(true);
            EstUneAttaque = true;


        } else if(source == deplacer){
            Main.DeplacementTroupeConsole();
        }else if(source == ValiderTerritoireDepart && JListTerritoireDepart.isSelectionEmpty() == false){
                buildSelectionTerritoireArrive();
        }else if(source == ValiderTerritoireFin && JListTerritoireFin.isSelectionEmpty() == false){
            buildCombat();
        }

    }


    public static void setTitrePanelGauche( String gaucheTitre){
        GaucheTitreString = gaucheTitre;
    }



    public void buildSelectionTerritoireDepart(boolean attaque){


        PanelGauche.remove(PanelGaucheButtonChoix);


        JListTerritoireDepart.setBackground(Color.darkGray);
        JListTerritoireDepart = new JList<>(ConvertToListModel(Main.GetTerritoireAttaquant()));

        if(attaque) {
            InformationAttaquant.setText("Choisis ton territoire d'attaque");

        }
        PanelGaucheAction.add(InformationAttaquant);
        PanelGaucheAction.add(JListTerritoireDepart);
        PanelGaucheAction.add(ValiderTerritoireDepart);
        PanelGauche.add(PanelGaucheAction);
        this.validate();
    }

    public void buildSelectionTerritoireArrive(){
        PanelGaucheAction.remove(ValiderTerritoireDepart);
        Territoire test = JListTerritoireDepart.getSelectedValue();
        PanelGaucheAction.remove(JListTerritoireDepart);
        JListTerritoireFin = new JList<>(ConvertToListModel(Main.GetTerritoireVoisin(JListTerritoireDepart.getSelectedValue())));

        if(EstUneAttaque) {
            InformationAttaquant.setText("Choisis le territoire que tu attaques");

        }
        PanelGaucheAction.add(JListTerritoireFin);
        PanelGaucheAction.add(ValiderTerritoireFin);
        JListTerritoireFin.validate();
        this.validate();
        this.repaint();
    }


    public void buildCombat(){
        PanelGaucheAction.remove(JListTerritoireFin);
        PanelGaucheAction.remove(ValiderTerritoireFin);
    }


    public DefaultListModel<Territoire> ConvertToListModel(java.util.List<Territoire> listeTerritoire){
        DefaultListModel<Territoire> defaultListeTerritoire= new DefaultListModel<>();

        for(int i = 0; i < listeTerritoire.size();i++)
        {
            defaultListeTerritoire.add(i,listeTerritoire.get(i));
        }

        return defaultListeTerritoire;
    }

}
