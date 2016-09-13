package ca.qc.bdeb.p56.scrabble.vue;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

/**
 * Created by Antoine on 9/12/2016.
 */
public class MenuCreation extends  JFrame {

    private JPanel panelMenu;
    private JTextField txtNom;
    private JButton btnConfirm;
    private JLabel lblNom;

    public MenuCreation() {

        super();

        setLayout(null);

        JFrame fenetre = new JFrame();
        fenetre.pack();
        Insets insets = fenetre.getInsets();
        fenetre = null;
        setSize(new Dimension(insets.left + insets.right + 500,
                insets.top + insets.bottom + 500));

       // panelMenu.setLayout(new BorderLayout());


        initializeComponents();



        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);




    }


    private void initializeComponents() {
        panelMenu = new JPanel();
        panelMenu.setLayout(null);
        ajouterTextBox();
        ajouterLabelNom();
        ajouterBoutons();

        add(panelMenu);
    }

    private void ajouterBoutons() {
        btnConfirm = new JButton();
        btnConfirm.setVisible(true);
        btnConfirm.setSize(100,50);
        btnConfirm.setLocation(225,350);
        panelMenu.add(btnConfirm);
    }

    private void ajouterLabelNom() {
        lblNom = new JLabel();
        lblNom.setText("Nom du Joueur : ");
        lblNom.setLocation(5,50);
        lblNom.setSize(lblNom.getPreferredSize());
        lblNom.setVisible(true);
        panelMenu.add(lblNom);
    }

    private void ajouterTextBox() {
        panelMenu.setLocation(0,0);
        panelMenu.setSize(new Dimension(500, 500));
        txtNom = new JTextField("", 30);
        txtNom.setBounds(50,50,180,30);

        txtNom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = txtNom.getText();
                txtNom.setText(input);
            }
        });
        panelMenu.add(txtNom);
    }
}