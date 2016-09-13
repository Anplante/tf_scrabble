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

        JFrame fenetre = new JFrame();
        fenetre.pack();
        Insets insets = fenetre.getInsets();
        fenetre = null;
        panelMenu = new JPanel();
        panelMenu.setLayout(new BorderLayout());
        this.setSize(new Dimension(insets.left + insets.right + 500,
                insets.top + insets.bottom + 500));
        this.setVisible(true);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initializeComponents();

        add(panelMenu);
    }


    private void initializeComponents() {
        ajouterTextBox();
        ajouterLabelNom();
        ajouterBoutons();
    }

    private void ajouterBoutons() {
        btnConfirm = new JButton();
        btnConfirm.setVisible(true);
        btnConfirm.setSize(50,50);
        panelMenu.add(btnConfirm,BorderLayout.SOUTH);
    }

    private void ajouterLabelNom() {
        lblNom = new JLabel();
        lblNom.setText("Nom du Joueur : ");
        lblNom.setLocation(50,50);
        lblNom.setSize(lblNom.getPreferredSize());
        lblNom.setVisible(true);
        panelMenu.add(lblNom);
    }

    private void ajouterTextBox() {
        panelMenu.setLocation(0,0);
        panelMenu.setSize(new Dimension(500, 500));
        txtNom = new JTextField("Entrez votre nom", 30);
        txtNom.setBounds(150,50,180,30);
        txtNom.setVisible(true);
        txtNom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = txtNom.getText();
                txtNom.setText(input);
            }
        });
        panelMenu.add(txtNom);
    }
}